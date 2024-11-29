package ureca.nolmung.exception;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ureca.nolmung.exception.bookmark.BookmarkException;
import ureca.nolmung.exception.user.UserException;
import ureca.nolmung.implementation.place.PlaceException;

@RestControllerAdvice
public class ApiControllerAdvice {

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BindException.class)
	public ExceptionResponse bindException(BindException e) {
		return new ExceptionResponse(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(BookmarkException.class)
	public ExceptionResponse bookmarkException(BookmarkException e) {
		return new ExceptionResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(PlaceException.class)
	public ExceptionResponse bookmarkException(PlaceException e) {
		return new ExceptionResponse(e.getMessage());
	}

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(UserException.class)
	public ExceptionResponse userException(UserException e) {
		return new ExceptionResponse(e.getMessage());
	}

}
