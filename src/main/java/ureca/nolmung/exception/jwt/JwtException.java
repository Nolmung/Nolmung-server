package ureca.nolmung.exception.jwt;


import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class JwtException extends BaseException {

    public JwtException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}

