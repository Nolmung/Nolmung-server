package ureca.nolmung.exception.bookmark;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum BookmarkExceptionType implements ExceptionType {

    BOOKMARK_EXISTS_EXCEPTION(Status.BAD_REQUEST, "해당 북마크가 이미 존재합니다.");

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
