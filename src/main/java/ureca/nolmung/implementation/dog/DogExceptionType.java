package ureca.nolmung.implementation.dog;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum DogExceptionType implements ExceptionType {

    DOG_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "반려견을 찾을 수 없습니다."),
    DOG_REGISTRATION_LIMIT_EXCEEDED(Status.BAD_REQUEST, "등록 가능한 반려견 수를 초과하였습니다."),
    DOG_MIN_PROFILE_NOT_EMPTY(Status.BAD_REQUEST, "최소 한 마리의 반려견 프로필은 남겨두셔야 합니다.");


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
