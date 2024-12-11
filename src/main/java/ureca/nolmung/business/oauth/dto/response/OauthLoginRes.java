package ureca.nolmung.business.oauth.dto.response;

import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.Enum.UserRole;

public record OauthLoginRes(LoginStatus loginStatus, Long id, String email, UserRole role, Provider provider, String accessToken) {
}

