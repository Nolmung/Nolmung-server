package ureca.nolmung.business.place;

import java.util.List;

import ureca.nolmung.business.place.dto.response.PlaceDetailResponse;
import ureca.nolmung.business.place.dto.response.SearchedPlaceResponse;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.jpa.place.Enum.Category;

public interface PlaceUseCase {

	List<SearchedPlaceResponse> searchByKeyword(CustomUserDetails userDetails, String keyword);

	PlaceDetailResponse findPlaceDetailById(CustomUserDetails userDetails, long placeId);

	Integer makePointData();

	List<SearchedPlaceResponse> findBySearchOption(CustomUserDetails user, Category category, Boolean isVisited, Boolean isBookmarked, double latitude, double longitude, double maxLatitude, double maxLongitude);

	List<SearchedPlaceResponse> findPlaceOnMap(CustomUserDetails user, double latitude, double longitude, double maxLatitude, double maxLongitude);
}
