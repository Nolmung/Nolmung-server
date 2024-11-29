package ureca.nolmung.implementation.bookmark;

import lombok.Getter;
import ureca.nolmung.exception.BaseException;
import ureca.nolmung.exception.ExceptionType;

@Getter
public class BookmarkException extends BaseException {

	public BookmarkException(ExceptionType exceptionType) {
		super(exceptionType);
	}
}