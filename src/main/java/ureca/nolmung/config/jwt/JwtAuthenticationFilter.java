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

        String path = request.getRequestURI();

        // 헬스체크 경로 확인
        if (path.equals("/")) {
            return true;
        }

        // 제외할 경로들
        String[] excludePath = {
            "/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**",
            "/v1/oauth/**",
            "/v1/users/signup/**",
            "/v1/places/**",
            "/v1/recommend/bookmarks",
            "/ban-words/upload",
            "/v1/diary/public/**",
            "/actuator/prometheus"
        };


        return java.util.Arrays.stream(excludePath).anyMatch(path::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenFromHeader(request);

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
                    request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_EXPIRED_EXCEPTION);
                }
                else {
                    request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_INVALID_EXCEPTION);
                }
            }
            catch (IllegalArgumentException e) {
                request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_INVALID_EXCEPTION);
            }
        }
        else {
            request.setAttribute("exception", JwtTokenExceptionType.JWT_TOKEN_NOT_FOUND_EXCEPTION);
        }

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}