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

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

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
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 만료되었습니다.");
                }
                else {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰이 유효하지 않습니다.");
                }
                return;
            }
            catch (IllegalArgumentException e) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "JWT 토큰의 형식이 잘못되었습니다.");
                return;
            }
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