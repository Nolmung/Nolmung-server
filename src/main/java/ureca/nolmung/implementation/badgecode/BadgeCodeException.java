package ureca.nolmung.implementation.dog;

import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class BadgeCodeException extends BaseException {

	public BadgeCodeException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}
