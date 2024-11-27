package ureca.nolmung.presentation.controller.oauth;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ureca.nolmung.business.user.UserService;
import ureca.nolmung.business.user.dto.request.SignUpReq;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;
import jakarta.servlet.http.HttpServletResponse;

@Tag(name = "소셜로그인, 회원가입")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final JWTUtil jwtUtil;

    @Operation(summary = "최초 소셜로그인 후 추가 정보 입력")
    @PostMapping("/signup/{userId}")
    public ResponseDto<Long> signUp(@PathVariable("userId") Long userId, @RequestBody SignUpReq req, HttpServletResponse response) {

        String token = userService.completeSignUp(userId, req);
        response.addCookie(jwtUtil.createCookie("Authorization", token));
        return ResponseUtil.SUCCESS("회원가입에 성공했습니다.",userId);
    }

    // 로그인 처리 함수 만들기
    @Operation(summary = "소셜로그인")
    @GetMapping("/login")
    public ResponseDto<Long> login(@CookieValue(value = "Authorization") String token) {

        Long loginUserId = userService.login(token);
        return ResponseUtil.SUCCESS("로그인에 성공했습니다.", loginUserId);

    }
}
