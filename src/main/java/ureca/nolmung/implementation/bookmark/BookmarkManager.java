package ureca.nolmung.implementation.bookmark;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.dto.response.BookmarkResponse;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.bookmark.BookmarkRepository;

@Component
@RequiredArgsConstructor
public class BookmarkManager {

	private final BookmarkRepository bookmarkRepository;

	public Bookmark findBookmarkByUserAndPlace(User user, Place place) {
		return bookmarkRepository.findByUserAndPlace(user, place)
			.orElseThrow(() -> new BookmarkException(BookmarkExceptionType.BOOKMARK_NOT_FOUND_EXCEPTION));
	}

	public Long save(Bookmark bookmark, Place place) {
		checkIfBookmarkExists(bookmark);
		place.addBookmarkCount();
		return bookmarkRepository.save(bookmark).getId();
	}

	private void checkIfBookmarkExists(Bookmark bookmark) {
		if (bookmarkRepository.existsByUserAndPlace(bookmark.getUser(), bookmark.getPlace())) {
			throw new BookmarkException(BookmarkExceptionType.BOOKMARK_EXISTS_EXCEPTION);
		}
	}

	public long delete(Bookmark bookmark, User user) {
		validateUser(bookmark, user);
		bookmarkRepository.delete(bookmark);
		Place place = bookmark.getPlace();
		place.minusBookmarkCount();
		return bookmark.getId();
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

	public List<Bookmark> findAll() {
		return bookmarkRepository.findAll();
	}
}
