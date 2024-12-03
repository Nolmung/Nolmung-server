package ureca.nolmung.implementation.user;


import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class UserException extends BaseException {

    public UserException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}

