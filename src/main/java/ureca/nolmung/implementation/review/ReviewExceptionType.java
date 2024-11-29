package ureca.nolmung.implementation.review;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum ReviewExceptionType implements ExceptionType {

    REVIEW_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "해당 리뷰가 존재하지 않습니다.");

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
