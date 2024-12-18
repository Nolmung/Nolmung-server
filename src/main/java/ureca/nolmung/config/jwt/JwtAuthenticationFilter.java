package ureca.nolmung.config.jwt;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

import ureca.nolmung.implementation.jwt.JwtTokenExceptionType;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        // 특정 요청에 대해 필터링을 적용하지 않도록 결정하는 데 사용 - 제외할 경로에 해당하는 경우 true, 그렇지 않은 경우 false 반환
        String path = request.getRequestURI();

        // 헬스체크 경로 제외
        if (path.equals("/")) {
            return true;
        }

        // JWT 필터에서 확인할 때, 제외할 경로
        String[] excludePaths = {
            "/swagger-resources", "/swagger-ui", "/v3/api-docs",
            "/v1/oauth",
            "/v1/users/signup",
            "/v1/places/point-data",
            "/v1/recommend/bookmarks",
            "/ban-words/upload",
            "/v1/diary/public",
            "/actuator/prometheus",
            "/favicon.ico"
        };

        for (String excludePath : excludePaths) {
            if (path.startsWith(excludePath)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 헤더에서 토큰 정보 가져오기
        String token = getTokenFromHeader(request);

        // 토큰이 존재하는 경우
        if (token != null) {
            try
            {
                // 토큰 유효성 확인
                if (jwtUtil.validateToken(token))
                {
                    // 유효한 토큰일 경우, 인증 정보를 가져와 SecurityContext에 설정
                    Authentication authentication = jwtUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            // JWT 관련 예외 처리
            catch (JwtException e)
            {
                // 토큰이 만료된 경우
                if (e instanceof ExpiredJwtException) {
                    request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_EXPIRED_EXCEPTION);
                }
                // 유효하지 않은 토큰인 경우
                else {
                    request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_INVALID_EXCEPTION);
                }
            }
            // 잘못된 인자 예외 처리
            catch (IllegalArgumentException e) {
                request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_INVALID_EXCEPTION);
            }
        }
        else
        {
            // 토큰이 없는 경우
            request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_NOT_FOUND_EXCEPTION);
        }


        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        // 헤더에서 인증 토큰 읽어오기
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}