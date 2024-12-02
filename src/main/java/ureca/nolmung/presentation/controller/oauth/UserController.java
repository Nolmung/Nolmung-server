package ureca.nolmung.presentation.controller.oauth;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ureca.nolmung.business.user.UserService;
import ureca.nolmung.business.user.UserUseCase;
import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.business.user.dto.response.UserResp;
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
    private final UserUseCase userUseCase;
    private final JWTUtil jwtUtil;

    @Operation(summary = "최초 소셜로그인 후 추가 정보 입력")
    @PostMapping("/signup/{userId}")
    public ResponseDto<Long> signUp(@PathVariable("userId") Long userId, @RequestBody UserReq req, HttpServletResponse response) {

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

    @Operation(summary = "회원 정보 수정")
    @PutMapping("")
    public ResponseDto<UserResp> updateUser(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody UserReq req) {
        return ResponseUtil.SUCCESS("회원 정보 수정에 성공했습니다.", userUseCase.updateUser(userDetails.getUser(),req));
    }

    @Operation(summary = "회원 정보 조회")
    @GetMapping("")
    public ResponseDto<UserResp> getDog(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseUtil.SUCCESS("회원 정보 조회에 성공하였습니다.", userUseCase.getUser(userDetails.getUser()));
    }

}
