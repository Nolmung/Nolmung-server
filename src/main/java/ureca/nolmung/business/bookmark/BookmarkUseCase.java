package ureca.nolmung.business.bookmark;

import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;

public interface BookmarkUseCase {

	Long createBookmark(Long userId, BookmarkServiceRequest serviceRequest);

	Long deleteBookmark(Long userId, Long bookmarkId);
}
