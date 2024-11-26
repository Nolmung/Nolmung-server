package ureca.nolmung.persistence.placeposition;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.placeposition.PlacePosition;

@Repository
public interface PlacePositionRepository extends JpaRepository<PlacePosition, Long> {

}
