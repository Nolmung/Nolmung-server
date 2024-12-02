package ureca.nolmung.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.user.Enum.UserRole;
import ureca.nolmung.jpa.user.User;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Slf4j
@Component
public class JWTUtil {

	private final SecretKey secretKey;
	private final Long expiredMs;
	private final UserManager userManager;


	public JWTUtil(@Value("${jwt.secretKey}")String secret, @Value("${jwt.access.expiration}")Long expiredMs, UserManager userManager) {
		// Create the SecretKey from the secret string
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


	/*public UserRole getRole(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return UserRole.valueOf(claims.get("role", String.class));
	}*/

	public Long getUserId(String token) {
		Claims claims = Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload();
		return claims.get("id", Long.class);  // "userId" claim을 Long 타입으로 반환
	}

	/*public Boolean isExpired(String token) {
		return Jwts.parser()
			.verifyWith(secretKey)
			.build()
			.parseSignedClaims(token)
			.getPayload().getExpiration().before(new Date());
	}*/

	public String createJwt(Long id, String email, UserRole role) {
		return Jwts.builder()
			.claim("id", id)
			.claim("email", email)
			.claim("role", role.name())
			.issuedAt(new Date())
			.expiration(new Date(System.currentTimeMillis() + expiredMs))
			.signWith(secretKey)
			.compact();
	}

	public Cookie createCookie(String key, String value) {

		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(60*60*60);
		//cookie.setSecure(true);
		cookie.setPath("/");
		cookie.setHttpOnly(true);

		return cookie;
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
}
