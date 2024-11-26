package ureca.nolmung.persistence.user;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.user.User;

public interface UserRepository extends JpaRepository<User, Long> {
}
