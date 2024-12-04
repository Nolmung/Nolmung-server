package ureca.nolmung.implementation.oauth;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum OauthExceptionType implements ExceptionType {

    CLIENT_REGISTRATION_NOT_FOUND(Status.BAD_REQUEST, "Kakao ClientRegistration을 찾을 수 없습니다."),
    ACCESS_TOKEN_RETRIEVAL_FAILED(Status.INTERNAL_SERVER_ERROR, "액세스 토큰을 가져오는 데 실패했습니다.");

    private final Status status;
    private final String message;

    @Override
    public Status status() {
        return status;
    }

    @Override
    public String message() {
        return message;
    }
}

