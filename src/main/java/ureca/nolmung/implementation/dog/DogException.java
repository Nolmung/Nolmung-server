package ureca.nolmung.implementation.dog;

import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class DogException extends BaseException {

    public DogException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
