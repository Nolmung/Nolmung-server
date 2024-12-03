package ureca.nolmung.persistence.placeposition;

import java.util.List;

import org.locationtech.jts.geom.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.placeposition.PlacePosition;

@Repository
public interface PlacePositionRepository extends JpaRepository<PlacePosition, Long> {

	@Query("SELECT p FROM PlacePosition pp JOIN pp.place p WHERE ST_Contains(:polygon, pp.location)")
	List<Place> findPlaceByCoordinate(@Param("polygon") Polygon polygon);

}
