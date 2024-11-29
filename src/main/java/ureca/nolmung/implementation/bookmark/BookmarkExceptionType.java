package ureca.nolmung.implementation.bookmark;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum BookmarkExceptionType implements ExceptionType {

    BOOKMARK_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "해당 북마크가 존재하지 않습니다."),
    BOOKMARK_EXISTS_EXCEPTION(Status.BAD_REQUEST, "해당 북마크가 이미 존재합니다."),
    NOT_USERS_BOOKMARK_EXCEPTION(Status.UNAUTHORIZED, "해당 유저의 북마크가 아닙니다."),
    ;

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
