package ureca.nolmung.presentation.controller.user;

import static ureca.nolmung.business.oauth.dto.response.LoginStatus.*;

import java.io.IOException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.oauth.OAuthUserServiceImplement;
import ureca.nolmung.business.oauth.dto.response.LoginStatus;
import ureca.nolmung.business.oauth.dto.response.OauthLoginRes;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.user.Enum.UserRole;
import ureca.nolmung.jpa.user.User;

@Tag(name = "소셜 로그인")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/oauth")
public class OauthController {

    private final OAuthUserServiceImplement oAuthUserService;
    private final JWTUtil jwtUtil;
    private final UserManager userManager;

    @Operation(summary = "카카오 소셜 로그인")
    @GetMapping("/kakao/callback")
    public void kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) throws IOException {

        OauthLoginRes res = oAuthUserService.kakaoOauthLogin(code);

        if(res.loginStatus() == LOGIN_SUCCESS)
        {
            jwtUtil.setAuthorizationHeader(response, res.id(), res.email(), res.role());
        }

        response.sendRedirect("http://localhost:3000/oauth/kakao/callback?id="+res.id());
    }

    @Operation(summary = "카카오 소셜 로그인 결과 반환")
    @GetMapping("/kakao/login")
    public ResponseDto<OauthLoginRes> getLoginStatus(@RequestParam("id") Long id)
    {
        User user = userManager.validateUserExistence(id);

        OauthLoginRes res;

        if(user.getRole() == UserRole.GUEST) {
            res = new OauthLoginRes(LoginStatus.SIGN_UP_REQUIRED, user.getId(), user.getEmail(), user.getRole(), user.getProvider());
        }
        else {
            res = new OauthLoginRes(LoginStatus.LOGIN_SUCCESS, user.getId(), user.getEmail(), user.getRole(), user.getProvider());
        }

        return ResponseUtil.SUCCESS("카카오 소셜 로그인에 성공하였습니다.", res);
    }


}