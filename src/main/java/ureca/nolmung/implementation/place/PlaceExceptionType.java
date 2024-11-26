package ureca.nolmung.implementation.place;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum PlaceExceptionType implements ExceptionType {

    Place_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "장소를 찾을 수 없습니다.");

    private final Status status;
    private final String message;

    @Override
    public Status status() {
        return null;
    }

    @Override
    public String message() {
        return "";
    }
}
