package ureca.nolmung.implementation.place;


import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class PlaceException extends BaseException {

    public PlaceException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}

