package ureca.nolmung.config.jwt;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.implementation.jwt.JwtTokenException;
import ureca.nolmung.implementation.jwt.JwtTokenExceptionType;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.user.Enum.UserRole;
import ureca.nolmung.jpa.user.User;

@Slf4j
@Component
public class JWTUtil {

	private final SecretKey secretKey;
	private final Long expiredMs;
	private final UserManager userManager;


	public JWTUtil(@Value("${jwt.secretKey}")String secret, @Value("${jwt.access.expiration}")Long expiredMs, UserManager userManager) {
		// 비밀 키, 토큰 만료 시간 및 userManager 초기화
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
		this.expiredMs = expiredMs;
		this.userManager = userManager;
	}

	private String getEmail(String token) {
		// 토큰에서 이메일 정보 추출
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload().get("email", String.class);
	}

	public String createJwt(Long id, String email, UserRole role) {
		// 토큰 생성
		return Jwts.builder()
			.claim("id", id)
			.claim("email", email)
			.claim("role", role.name())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {
		// 토큰을 사용하여 이메일을 추출하고, 해당 이메일로 사용자 정보를 조회하여 인증 정보를 생성
		String email = getEmail(accessToken);
		User user = userManager.findByEmail(email);
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
	}

	public boolean validateToken(String accessToken) {
		// 토큰 유효성 검사
		Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken);
		return true;
	}

	public void setAuthorizationHeader(HttpServletResponse response, Long userId, String email, UserRole role)
	{
		// 헤더에 토큰 설정
		String token = createJwt(userId,email,role);

		if (token == null) {
			throw new JwtTokenException(JwtTokenExceptionType.JWT_TOKEN_CREATION_FAILED_EXCEPTION);
		}

		response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		System.out.println("setAuthorizationHeader : " + token);
	}
}
