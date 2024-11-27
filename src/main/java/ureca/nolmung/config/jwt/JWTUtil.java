package ureca.nolmung.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.Cookie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.user.Enum.UserRole;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JWTUtil {

	private SecretKey secretKey;
	private Long expiration;


	public JWTUtil(@Value("${jwt.secretKey}")String secret, @Value("${jwt.access.expiration}")Long expiredMs) {
		// Create the SecretKey from the secret string
		this.secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
		this.expiration = expiredMs;
	}

	public String getEmail(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.get("email", String.class);
	}


	public UserRole getRole(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return UserRole.valueOf(claims.get("role", String.class));
	}

	public Long getUserId(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.get("userId", Long.class);  // "userId" claim을 Long 타입으로 반환
	}

	public Boolean isExpired(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(secretKey)
			.build()
			.parseClaimsJws(token)
			.getBody();
		return claims.getExpiration().before(new Date());
	}

	public String createJwt(String email, Long userId, UserRole role) {
		return Jwts.builder()
			.claim("email", email)
			.claim("userId", userId)
			.claim("role", role.name())
			//.claim("role", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + expiration))
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
}
