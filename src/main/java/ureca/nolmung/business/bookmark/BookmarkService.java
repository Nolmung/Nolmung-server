package ureca.nolmung.business.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.implementation.bookmark.BookmarkException;
import ureca.nolmung.implementation.bookmark.BookmarkExceptionType;
import ureca.nolmung.implementation.bookmark.BookmarkManager;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.bookmark.BookmarkRepository;
import ureca.nolmung.persistence.place.PlaceRepository;
import ureca.nolmung.persistence.user.UserRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkService implements BookmarkUseCase {

	private final BookmarkManager bookmarkManager;
	private final PlaceRepository placeRepository;
	private final BookmarkRepository bookmarkRepository;
	private final UserRepository userRepository;

	@Transactional
	@Override
	public Long createBookmark(User user, BookmarkServiceRequest serviceRequest) {
		Place place = placeRepository.findById(serviceRequest.getPlaceId())
			.orElseThrow(() -> new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION));
		//TODO place와 user의 북마크 카운트를 한 번에 조정하는 메서드를 매니저에 만들어두는게 어떨까
		place.addBookmarkCount();
		user.addBookmarkCount();
		userRepository.save(user);
		return bookmarkManager.save(serviceRequest.toEntity(user, place));
	}

	@Transactional
	@Override
	public Long deleteBookmark(User user, Long bookmarkId) {
		Bookmark bookmark = bookmarkRepository.findById(bookmarkId)
			.orElseThrow(() -> new BookmarkException(BookmarkExceptionType.BOOKMARK_NOT_FOUND_EXCEPTION));

		bookmarkManager.delete(bookmark, user);
		user.subtractBookmarkCount();
		userRepository.save(user);

		return bookmarkId;
	}

	@Override
	public List<BookmarkResponse> findAllBookmarks(User user, Category category) {
		List<Bookmark> bookmarks = bookmarkRepository.findByUserAndCategory(user, category);
		return bookmarkManager.findAllBookmarks(bookmarks);
	}
}
