package ureca.nolmung.config.jwt;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.oauth.dto.CustomOauth2User;
import ureca.nolmung.jpa.user.Enum.UserRole;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //System.out.println("JwtAuthenticationFilter 호출됨");
        // 회원 가입 시에는 jwt 필터 적용 안되도록 수정
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/api/v1/users/signup")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 쿠키에서 Authorization 키에 담긴 토큰을 찾음
        String authorization = getTokenFromCookies(request);

        //Authorization 헤더 검증
        if (authorization == null) {
            //System.out.println("token null");
            return;
        }

        String token = authorization;

        //토큰 소멸 시간 검증 - 현재 시간이 토큰의 만료 시간보다 이전인 경우 false를 반환하고, 만료 시간이 지나면 true를 반환
        if (jwtUtil.isExpired(token)) {
            //System.out.println("토큰 유효기간 만료");
            filterChain.doFilter(request, response);
            return;
        }

        //토큰에서 email, role, userId 획득
        Long userId = jwtUtil.getUserId(token);
        String email = jwtUtil.getEmail(token);
        UserRole role = jwtUtil.getRole(token);

        CustomOauth2User customOAuth2User = new CustomOauth2User(userId,email,role);
        //스프링 시큐리티 인증 토큰 생성
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, customOAuth2User.getAuthorities());
        //세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authToken);

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
