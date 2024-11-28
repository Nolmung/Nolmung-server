package ureca.nolmung.implementation.dog;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum DogExceptionType implements ExceptionType {

    DOG_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "반려견을 찾을 수 없습니다.");

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
