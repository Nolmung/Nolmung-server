package ureca.nolmung.implementation.label;


import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class LabelException extends BaseException {

    public LabelException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}

