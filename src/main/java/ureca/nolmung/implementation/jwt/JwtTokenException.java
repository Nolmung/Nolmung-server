package ureca.nolmung.implementation.jwt;

import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class JwtTokenException extends BaseException {

	public JwtTokenException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}

