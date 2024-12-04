package ureca.nolmung.implementation.oauth;

import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class OauthException extends BaseException {

    public OauthException(ExceptionType exceptionType) {
        super(exceptionType);
    }
}
