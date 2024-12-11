package ureca.nolmung.business.place;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ureca.nolmung.business.diary.response.PlaceDiaryResponse;
import ureca.nolmung.business.labels.response.LabelResponse;
import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.implementation.label.LabelManager;
import ureca.nolmung.implementation.place.PlaceManager;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlaceService implements PlaceUseCase {

	private final PlaceManager placeManager;
	private final LabelManager labelManager;
	private final DiaryManager diaryManager;

	@Override
	public List<SearchedPlaceResponse> searchByKeyword(CustomUserDetails userDetails, String keyword) {
		log.info("Searching for places with keyword: {}", keyword);
		List<Place> places = placeManager.searchByKeyword(keyword);
		return placeManager.createSearchedPlaceResponse(userDetails, places);
	}

	@Override
	public PlaceDetailResponse findPlaceDetailById(CustomUserDetails userDetails, long placeId) {
		Place place = placeManager.findPlaceById(placeId);
		List<LabelResponse> labelResponses = labelManager.findLabelsByPlaceId(placeId);
		List<PlaceDiaryResponse> placeDiaryResponses = diaryManager.findDiaryByPlace(place);
		Boolean isBookmarked = placeManager.isBookmarked(userDetails, place);
		return PlaceDetailResponse.of(place, labelResponses, placeDiaryResponses, isBookmarked);
	}

	@Transactional
	@Override
	public Integer makePointData() {
		List<Place> places = placeManager.findAllPlace();
		placeManager.makePointData(places);
		return places.size();
	}

	@Override
	public List<SearchedPlaceResponse> findBySearchOption(CustomUserDetails userDetails, Category category, Boolean isVisited, Boolean isBookmarked, double latitude, double longitude, double maxLatitude, double maxLongitude) {
		List<Place> places = placeManager.findBySearchOption(userDetails, category, isVisited, isBookmarked, latitude, longitude, maxLatitude, maxLongitude);
		return placeManager.createSearchedPlaceResponse(userDetails, places);
	}

	@Override
	public List<SearchedPlaceResponse> findPlaceOnMap(CustomUserDetails userDetails, double latitude, double longitude, double maxLatitude, double maxLongitude) {
		List<Place> places = placeManager.findPlaceMapOn(latitude, longitude, maxLatitude, maxLongitude);
		return placeManager.createSearchedPlaceResponse(userDetails, places);
	}
}
