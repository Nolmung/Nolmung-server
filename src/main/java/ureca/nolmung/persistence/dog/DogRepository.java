package ureca.nolmung.persistence.dog;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ureca.nolmung.jpa.dog.Dog;

import java.util.List;
import java.util.Optional;

@Repository
public interface DogRepository extends JpaRepository<Dog, Long> {
    Optional<Dog> findByUserIdAndId(Long userId, Long dogId);
    List<Dog> findByUserId(Long userId);
}
