package ureca.nolmung.implementation.bookmark;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.response.BookmarkResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.bookmark.BookmarkRepository;

@Component
@RequiredArgsConstructor
public class BookmarkManager {

	private final BookmarkRepository bookmarkRepository;

	public Long save(Bookmark bookmark) {
		checkIfBookmarkExists(bookmark);
		return bookmarkRepository.save(bookmark).getId();
	}

	private void checkIfBookmarkExists(Bookmark bookmark) {
		if (bookmarkRepository.existsByUserAndPlace(bookmark.getUser(), bookmark.getPlace())) {
			throw new BookmarkException(BookmarkExceptionType.BOOKMARK_EXISTS_EXCEPTION);
		}
	}

	public void delete(Bookmark bookmark, User user) {
		validateUser(bookmark, user);
		bookmarkRepository.delete(bookmark);
	}

	public void validateUser(Bookmark bookmark, User user) {
		if (bookmark.getUser().getId() != user.getId()) {
			throw new BookmarkException(BookmarkExceptionType.NOT_USERS_BOOKMARK_EXCEPTION);
		}
	}

	public List<BookmarkResponse> findAllBookmarks(List<Bookmark> bookmarks) {
		return bookmarks.stream()
			.map(bookmark -> BookmarkResponse.of(bookmark, bookmark.getPlace()))
			.collect(Collectors.toList());
	}

}
