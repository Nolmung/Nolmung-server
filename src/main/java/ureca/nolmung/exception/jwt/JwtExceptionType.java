package ureca.nolmung.exception.jwt;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum JwtExceptionType implements ExceptionType {

    JWT_TOKEN_CREATION_FAILED_EXCEPTION(Status.BAD_REQUEST, "JWT 토큰 생성에 실패했습니다."),
    JWT_TOKEN_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "JWT 토큰 찾기에 실패했습니다."),
    JWT_USER_ID_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "JWT 토큰에서 사용자 ID를 찾을 수 없습니다.");

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
