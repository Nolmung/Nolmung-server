package ureca.nolmung.business.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.implementation.bookmark.BookmarkManager;
import ureca.nolmung.implementation.place.PlaceManager;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkService implements BookmarkUseCase {

	private final BookmarkManager bookmarkManager;
	private final PlaceManager placeManager;

	@Transactional
	@Override
	public Long createBookmark(User user, BookmarkServiceRequest serviceRequest) {
		Place place = placeManager.findPlaceById(serviceRequest.getPlaceId());
		Bookmark bookmark = serviceRequest.toEntity(user, place);
		return bookmarkManager.save(bookmark, place);
	}

	@Transactional
	@Override
	public Long deleteBookmark(User user, Long bookmarkId) {
		Bookmark bookmark = bookmarkManager.findBookmarkById(bookmarkId);
		bookmarkManager.delete(bookmark, user);
		return bookmarkId;
	}

	@Override
	public List<BookmarkResponse> findAllBookmarks(User user, Category category) {
		return bookmarkManager.findAllBookmarks(user, category);
	}
}
