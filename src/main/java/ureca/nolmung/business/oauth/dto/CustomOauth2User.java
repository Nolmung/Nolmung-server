package ureca.nolmung.business.oauth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;
import ureca.nolmung.jpa.user.Enum.UserRole;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CustomOauth2User implements OAuth2User {

    private Long userId;
    private String email;
    private UserRole role;

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("userId", userId);  // 회원 아이디 정보 추가
        attributes.put("email", email);  // 이메일 정보 추가
        attributes.put("role", role);    // 역할 정보 추가 (필요시 추가 정보)
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    @Override
    public String getName() {
        return this.email;
    }
}
