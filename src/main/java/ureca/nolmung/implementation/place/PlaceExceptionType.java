package ureca.nolmung.implementation.place;

import lombok.AllArgsConstructor;
import ureca.nolmung.exception.ExceptionType;
import ureca.nolmung.exception.Status;

@AllArgsConstructor
public enum PlaceExceptionType implements ExceptionType {

    PLACE_NOT_FOUND_EXCEPTION(Status.NOT_FOUND, "해당 장소가 존재하지 않습니다.");

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
