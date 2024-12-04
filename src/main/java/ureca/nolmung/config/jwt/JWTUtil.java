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
import ureca.nolmung.exception.jwt.JwtException;
import ureca.nolmung.exception.jwt.JwtExceptionType;
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
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
		this.expiredMs = expiredMs;
		this.userManager = userManager;
	}

	private String getEmail(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload().get("email", String.class);
	}

	private String createJwt(Long id, String email, UserRole role) {
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
		String email = getEmail(accessToken);
		User user = userManager.findByEmail(email);
		CustomUserDetails customUserDetails = new CustomUserDetails(user);
		return new UsernamePasswordAuthenticationToken(customUserDetails, "", customUserDetails.getAuthorities());
	}

	public boolean validateToken(String accessToken) {
		Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(accessToken);
		return true;
	}

	public void setAuthorizationHeader(HttpServletResponse response, Long userId, String email, UserRole role)
	{
		String token = createJwt(userId,email,role);

		if (token == null) {
			throw new JwtException(JwtExceptionType.JWT_TOKEN_CREATION_FAILED_EXCEPTION);
		}

		response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

		System.out.println("setAuthorizationHeader : " + token);
	}
}
