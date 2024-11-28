package ureca.nolmung.persistence.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.dog.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {
}
