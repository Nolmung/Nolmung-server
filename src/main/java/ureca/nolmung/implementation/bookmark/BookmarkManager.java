package ureca.nolmung.implementation.bookmark;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.bookmark.BookmarkRepository;
import ureca.nolmung.persistence.place.PlaceRepository;

@Component
@RequiredArgsConstructor
public class BookmarkManager {

	private final BookmarkRepository bookmarkRepository;
	private final PlaceRepository placeRepository;

	public Long save(User user, BookmarkServiceRequest serviceRequest) {
		Place place = placeRepository.findById(serviceRequest.getPlaceId())
			.orElseThrow(() -> new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION));
		Bookmark bookmark = serviceRequest.toEntity(user, place);

		checkIfBookmarkExists(bookmark);

		place.addBookmarkCount();
		return bookmarkRepository.save(bookmark).getId();
	}

	private void checkIfBookmarkExists(Bookmark bookmark) {
		if (bookmarkRepository.existsByUserAndPlace(bookmark.getUser(), bookmark.getPlace())) {
			throw new BookmarkException(BookmarkExceptionType.BOOKMARK_EXISTS_EXCEPTION);
		}
	}

	public void delete(Long bookmarkId, User user) {
		Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(() -> new BookmarkException(BookmarkExceptionType.BOOKMARK_NOT_FOUND_EXCEPTION));
		validateUser(bookmark, user);

		bookmarkRepository.delete(bookmark);

		Place place = bookmark.getPlace();
		place.minusBookmarkCount();
	}

	public void validateUser(Bookmark bookmark, User user) {
		if (bookmark.getUser().getId() != user.getId()) {
			throw new BookmarkException(BookmarkExceptionType.NOT_USERS_BOOKMARK_EXCEPTION);
		}
	}

	public List<BookmarkResponse> findAllBookmarks(User user, Category category) {
		List<Bookmark> bookmarks = bookmarkRepository.findByUserAndCategory(user, category);
		return bookmarks.stream()
			.map(bookmark -> BookmarkResponse.of(bookmark, bookmark.getPlace()))
			.collect(Collectors.toList());
	}

}
