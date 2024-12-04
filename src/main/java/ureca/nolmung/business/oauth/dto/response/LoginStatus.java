package ureca.nolmung.business.oauth.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LoginStatus {
    LOGIN_SUCCESS, SIGN_UP_REQUIRED;
}



