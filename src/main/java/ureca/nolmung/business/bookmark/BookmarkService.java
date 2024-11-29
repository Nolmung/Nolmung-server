package ureca.nolmung.business.bookmark;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;
import ureca.nolmung.exception.user.UserException;
import ureca.nolmung.exception.user.UserExceptionType;
import ureca.nolmung.implementation.bookmark.BookmarkManager;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.place.PlaceRepository;
import ureca.nolmung.persistence.user.UserRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkService implements BookmarkUseCase {

	private final BookmarkManager bookmarkManager;
	private final UserRepository userRepository;
	private final PlaceRepository placeRepository;

	@Transactional
	@Override
	public Long createBookmark(Long userId, BookmarkServiceRequest serviceRequest) {
		User user = userRepository.findById(userId)
			.orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
		Place place = placeRepository.findById(serviceRequest.getPlaceId())
			.orElseThrow(() -> new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION));
		place.addBookmarkCount();

		return bookmarkManager.saveBookmark(serviceRequest.toEntity(user, place));
	}
}
