package ureca.nolmung.presentation.controller.user;

import static ureca.nolmung.business.oauth.dto.response.LoginStatus.*;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.oauth.OAuthUserServiceImplement;
import ureca.nolmung.business.oauth.dto.response.OauthLoginRes;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;
@Tag(name = "소셜 로그인")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/oauth")
public class OauthController {

    private final OAuthUserServiceImplement oAuthUserService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "카카오 소셜 로그인")
    @GetMapping("/kakao/callback")
    public ResponseDto<OauthLoginRes> kakaoLogin(@RequestParam("code") String code, HttpServletResponse response) {

        OauthLoginRes res = oAuthUserService.kakaoOauthLogin(code);

        if(res.loginStatus() == LOGIN_SUCCESS)
        {
            jwtUtil.setAuthorizationHeader(response, res.id(), res.email(), res.role());
        }

        return ResponseUtil.SUCCESS("카카오 소셜 로그인에 성공하였습니다.", res);
    }


}