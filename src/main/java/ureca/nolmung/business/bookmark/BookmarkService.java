package ureca.nolmung.business.bookmark;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.implementation.bookmark.BookmarkManager;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class BookmarkService implements BookmarkUseCase {

	private final BookmarkManager bookmarkManager;

	@Transactional
	@Override
	public Long createBookmark(User user, BookmarkServiceRequest serviceRequest) {
		return bookmarkManager.save(user, serviceRequest);
	}

	@Transactional
	@Override
	public Long deleteBookmark(User user, Long bookmarkId) {
		bookmarkManager.delete(bookmarkId, user);
		return bookmarkId;
	}

	@Override
	public List<BookmarkResponse> findAllBookmarks(User user, Category category) {
		return bookmarkManager.findAllBookmarks(user, category);
	}
}
