package ureca.nolmung.implementation.review;


import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class ReviewException extends BaseException {

    public ReviewException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}

