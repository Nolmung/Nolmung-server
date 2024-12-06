package ureca.nolmung.implementation.jwt;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum JwtTokenExceptionType implements ExceptionType {

    JWT_TOKEN_CREATION_FAILED_EXCEPTION(Status.INTERNAL_SERVER_ERROR, "JWT 토큰 생성에 실패했습니다."),
    JWT_TOKEN_NOT_FOUND_EXCEPTION(Status.UNAUTHORIZED, "JWT 토큰이 없습니다."),
    JWT_TOKEN_EXPIRED_EXCEPTION(Status.UNAUTHORIZED, "JWT 토큰의 유효기간이 만료되었습니다."),
    JWT_TOKEN_INVALID_EXCEPTION(Status.UNAUTHORIZED, "JWT 토큰이 유효하지 않습니다.");

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
