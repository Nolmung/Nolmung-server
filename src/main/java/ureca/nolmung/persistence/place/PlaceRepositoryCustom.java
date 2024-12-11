package ureca.nolmung.persistence.place;

import java.util.List;
import java.util.Set;

import org.locationtech.jts.geom.Polygon;

import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

public interface PlaceRepositoryCustom {
	List<Place> findBySearchOption(CustomUserDetails userDetails, Category category, Boolean isvisited, Boolean isBookmarked, Polygon polygon);
	List<Place> findAllByDogSizes(Set<String> sizes);
}
