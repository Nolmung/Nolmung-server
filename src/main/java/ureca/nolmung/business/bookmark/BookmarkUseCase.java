package ureca.nolmung.business.bookmark;

import java.util.List;

import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.jpa.place.Enum.Category;

public interface BookmarkUseCase {

	Long createBookmark(Long userId, BookmarkServiceRequest serviceRequest);

	Long deleteBookmark(Long userId, Long bookmarkId);

	List<BookmarkResponse> findAllBookmarks(Long userId, Category category);
}
