package ureca.nolmung.implementation.bookmark;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.exception.bookmark.BookmarkException;
import ureca.nolmung.exception.bookmark.BookmarkExceptionType;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.bookmark.BookmarkRepository;

@Component
@RequiredArgsConstructor
public class BookmarkManager {

	private final BookmarkRepository bookmarkRepository;

	public Long save(Bookmark bookmark) {
		if (bookmarkRepository.existsByUserAndPlace(bookmark.getUser(), bookmark.getPlace())) {
			throw new BookmarkException(BookmarkExceptionType.BOOKMARK_EXISTS_EXCEPTION);
		}
		return bookmarkRepository.save(bookmark).getId();
	}

	public void delete(Bookmark bookmark, User user) {
		if (notValidateUser(bookmark, user)){
			throw new BookmarkException(BookmarkExceptionType.NOT_USERS_BOOKMARK_EXCEPTION);
		}
		bookmarkRepository.delete(bookmark);
	}

	public boolean notValidateUser(Bookmark bookmark, User user) {
		if (bookmark.getUser().equals(user)) {
			return false;
		}
		return true;
	}

	public List<BookmarkResponse> findAllBookmarks(List<Bookmark> bookmarks) {
		return bookmarks.stream()
			.map(bookmark -> BookmarkResponse.of(bookmark, bookmark.getPlace()))
			.collect(Collectors.toList());
	}
}
