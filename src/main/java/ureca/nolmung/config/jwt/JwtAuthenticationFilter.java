package ureca.nolmung.config.jwt;

import java.io.IOException;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {

        String path = request.getRequestURI();

        String[] excludePaths = {
                "/oauth2",
                "/api/v1/users/signup",
                "/swagger-resources",
                "/swagger-ui",
                "/v3/api-docs"
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

        // 쿠키에서 Authorization 키에 담긴 토큰을 찾음
        String token = getTokenFromCookies(request);

        //Authorization 헤더 검증
        if (token != null) {
            try
            {
                if (jwtUtil.validateToken(token))
                {
                    Authentication authentication = jwtUtil.getAuthentication(token);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            catch (JwtException e)
            {
                if (e instanceof ExpiredJwtException) {
                    // 토큰 유효기간 만료
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 만료되었습니다.");
                }
                else
                {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 유효하지 않습니다.");
                }
                return;
            }
            catch (IllegalArgumentException e) { // 손상된 토큰
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰의 형식이 잘못되었습니다.");
                return;
            }
        }

        filterChain.doFilter(request, response);
    }

    // 쿠키에서 Authorization 토큰을 추출하는 메소드
    private String getTokenFromCookies(HttpServletRequest request) {
        String token = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("Authorization")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        return token;
    }
}