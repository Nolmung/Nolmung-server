package ureca.nolmung.implementation.media;

import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class MediaException extends BaseException {

    public MediaException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
