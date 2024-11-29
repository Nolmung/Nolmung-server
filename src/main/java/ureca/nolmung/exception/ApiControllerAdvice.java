package ureca.nolmung.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ureca.nolmung.exception.bookmark.BookmarkException;
import ureca.nolmung.exception.place.PlaceException;
import ureca.nolmung.exception.user.UserException;

@RestControllerAdvice
public class ApiControllerAdvice {

	@ExceptionHandler(BindException.class)
	public ExceptionResponse bindException(BindException e) {
		return new ExceptionResponse(Status.BAD_REQUEST, e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	@ExceptionHandler(BookmarkException.class)
	public ExceptionResponse bookmarkException(BookmarkException e) {
		return new ExceptionResponse(e.getExceptionType().status() , e.getMessage());
	}

	@ExceptionHandler(PlaceException.class)
	public ExceptionResponse bookmarkException(PlaceException e) {
		return new ExceptionResponse(e.getExceptionType().status() , e.getMessage());
	}

	@ExceptionHandler(UserException.class)
	public ExceptionResponse userException(UserException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

}
