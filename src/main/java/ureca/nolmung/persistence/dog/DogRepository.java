package ureca.nolmung.persistence.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ureca.nolmung.jpa.dog.Dog;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
}
