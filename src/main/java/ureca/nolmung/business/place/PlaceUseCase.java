package ureca.nolmung.business.place;

import java.util.List;

import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.jpa.place.Enum.Category;

public interface PlaceUseCase {

	List<SearchedPlaceResponse> searchByKeyword(String keyword);

	PlaceDetailResponse findPlaceDetailById(long placeId);

	Integer makePointData();

	List<SearchedPlaceResponse> findBySearchOption(Long userId, Category category, String acceptSize, Double ratingAvg, Boolean isBookmarked, double latitude, double longitude, double maxLatitude, double maxLongitude);

	List<SearchedPlaceResponse> findPlaceOnMap(double latitude, double longitude, double maxLatitude, double maxLongitude);
}
