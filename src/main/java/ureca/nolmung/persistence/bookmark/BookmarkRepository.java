package ureca.nolmung.persistence.bookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long>, BookmarkRepositoryCustom {

	Boolean existsByUserAndPlace(User user, Place place);

}
