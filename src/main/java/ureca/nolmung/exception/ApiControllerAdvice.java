package ureca.nolmung.exception;

import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import ureca.nolmung.implementation.bookmark.BookmarkException;
import ureca.nolmung.implementation.diary.DiaryException;
import ureca.nolmung.implementation.dog.BadgeCodeException;
import ureca.nolmung.implementation.dog.DogException;
import ureca.nolmung.implementation.label.LabelException;
import ureca.nolmung.implementation.media.MediaException;
import ureca.nolmung.implementation.oauth.OauthException;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.review.ReviewException;
import ureca.nolmung.implementation.user.UserException;

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

	@ExceptionHandler(DiaryException.class)
	public ExceptionResponse diaryException(DiaryException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

	@ExceptionHandler(ReviewException.class)
	public ExceptionResponse reviewException(ReviewException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

	@ExceptionHandler(BadgeCodeException.class)
	public ExceptionResponse badgeCodeException(BadgeCodeException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

	@ExceptionHandler(DogException.class)
	public ExceptionResponse dogException(DogException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

	@ExceptionHandler(LabelException.class)
	public ExceptionResponse labelException(LabelException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

	@ExceptionHandler(MediaException.class)
	public ExceptionResponse mediaException(MediaException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

	@ExceptionHandler(OauthException.class)
	public ExceptionResponse oauthException(OauthException e) {
		return new ExceptionResponse(e.getExceptionType().status(), e.getMessage());
	}

}
