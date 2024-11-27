package ureca.nolmung.business.oauth;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import ureca.nolmung.business.oauth.dto.CustomOauth2User;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.jpa.user.Enum.UserRole;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SucessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JWTUtil jwtUtil;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        CustomOauth2User oauth2User = (CustomOauth2User) authentication.getPrincipal();

        if(oauth2User.getRole() == UserRole.GUEST) {

            // 회원가입 시, 추가로 정보 입력받아야 하는 페이지로 이동
            response.sendRedirect("http://localhost:3000/signUp?id=" + oauth2User.getUserId());
        }
        else
        {
            String token = jwtUtil.createJwt(oauth2User.getEmail(),oauth2User.getUserId(),oauth2User.getRole());
            response.addCookie(jwtUtil.createCookie("Authorization", token));
            System.out.println("로그인 성공 시, 토큰생성 : " + token);
            response.sendRedirect("http://localhost:3000/");
        }
    }
}
