package ureca.nolmung.business.place;

import java.util.List;

import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;

public interface PlaceUseCase {

	List<SearchedPlaceResponse> searchByKeyword(String keyword);

	PlaceDetailResponse findPlaceDetailById(long placeId);

	Integer makePointData();
}
