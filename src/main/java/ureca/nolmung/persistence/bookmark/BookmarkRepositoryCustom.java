package ureca.nolmung.persistence.bookmark;

import java.util.List;

import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.user.User;

public interface BookmarkRepositoryCustom {
	List<Bookmark> findByUserAndCategory(User user, Category category);
}
