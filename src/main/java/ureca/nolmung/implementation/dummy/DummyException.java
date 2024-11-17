package ureca.nolmung.implementation.dummy;

import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

public class DummyException extends BaseException {

    public DummyException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
