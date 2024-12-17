package ureca.nolmung.business.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.dto.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.dto.response.BookmarkResponse;
import ureca.nolmung.implementation.bookmark.BookmarkManager;
import ureca.nolmung.implementation.place.PlaceManager;
import ureca.nolmung.implementation.user.UserManager;
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
	private final UserManager userManager;

	@Transactional
	@Override
	public Long createBookmark(User user, BookmarkServiceRequest serviceRequest) {
        userManager.addBookmarkCount(user);
		Place place = placeManager.findPlaceById(serviceRequest.getPlaceId());
		Bookmark bookmark = serviceRequest.toEntity(user, place);
		return bookmarkManager.save(bookmark, place);
	}

	@Transactional
	@Override
	public Long deleteBookmark(User user, Long placeId) {
        userManager.subtractBookmarkCount(user);
		Place place = placeManager.findPlaceById(placeId);
		Bookmark bookmark = bookmarkManager.findBookmarkByUserAndPlace(user, place);
		return bookmarkManager.delete(bookmark, user);
	}

	@Override
	public List<BookmarkResponse> findAllBookmarks(User user, Category category) {
		return bookmarkManager.findAllBookmarks(user, category);
	}
}
