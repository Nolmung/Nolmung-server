package ureca.nolmung.persistence.place;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.place.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
}
