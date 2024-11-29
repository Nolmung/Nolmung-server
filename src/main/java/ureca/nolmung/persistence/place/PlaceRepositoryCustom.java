package ureca.nolmung.persistence.place;

import java.util.List;

import org.locationtech.jts.geom.Polygon;

import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

public interface PlaceRepositoryCustom {
	List<Place> findBySearchOption(Long userId, Category category, String acceptSize, Double ratingAvg, Boolean isBookmarked, Polygon polygon);
}
