package ureca.nolmung.implementation.media;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum MediaExceptionType implements ExceptionType {

    MEDIA_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "미디어를 찾을 수 없습니다.");

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
