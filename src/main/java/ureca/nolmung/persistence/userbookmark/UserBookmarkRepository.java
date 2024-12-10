package ureca.nolmung.persistence.userbookmark;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.userbookmark.UserBookmark;

public interface UserBookmarkRepository extends JpaRepository<UserBookmark, Long> {

}
