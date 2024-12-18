package ureca.nolmung.business.bookmark;

import java.util.List;

import ureca.nolmung.business.bookmark.dto.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.dto.response.BookmarkResponse;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.user.User;

public interface BookmarkUseCase {

	Long createBookmark(User user, BookmarkServiceRequest serviceRequest);

	Long deleteBookmark(User user, Long placeId);

	List<BookmarkResponse> findAllBookmarks(User user, Category category);
}
