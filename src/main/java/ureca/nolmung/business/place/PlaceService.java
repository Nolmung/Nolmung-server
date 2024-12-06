package ureca.nolmung.business.place;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.diary.response.PlaceDiaryResponse;
import ureca.nolmung.business.labels.response.LabelResponse;
import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.implementation.label.LabelManager;
import ureca.nolmung.implementation.place.PlaceManager;
import ureca.nolmung.implementation.place.dtomapper.PlaceDtoMapper;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlaceService implements PlaceUseCase {

	private final PlaceManager placeManager;
	private final LabelManager labelManager;
	private final DiaryManager diaryManager;
	private final PlaceDtoMapper placeDtoMapper;

	@Override
	public List<SearchedPlaceResponse> searchByKeyword(String keyword) {
		List<Place> places = placeManager.searchByKeyword(keyword);
		return placeDtoMapper.toSearchedPlaceReponseList(places);
	}

	@Override
	public PlaceDetailResponse findPlaceDetailById(long placeId) {
		Place place = placeManager.findPlaceById(placeId);
		List<LabelResponse> labelResponses = labelManager.findLabelsByPlaceId(placeId);
		List<PlaceDiaryResponse> placeDiaryResponses = diaryManager.findDiaryByPlace(place);
		return PlaceDetailResponse.of(place, labelResponses, placeDiaryResponses);
	}

	@Transactional
	@Override
	public Integer makePointData() {
		List<Place> places = placeManager.findAllPlace();
		placeManager.makePointData(places);
		return places.size();
	}

	@Override
	public List<SearchedPlaceResponse> findBySearchOption(User user, Category category, Boolean isVisited, Boolean isBookmarked, double latitude, double longitude, double maxLatitude, double maxLongitude) {
		List<Place> places = placeManager.findBySearchOption(user, category, isVisited, isBookmarked, latitude, longitude, maxLatitude, maxLongitude);
		return placeDtoMapper.toSearchedPlaceReponseList(places);
	}

	@Override
	public List<SearchedPlaceResponse> findPlaceOnMap(double latitude, double longitude, double maxLatitude, double maxLongitude) {
		List<Place> places = placeManager.findPlaceMapOn(latitude, longitude, maxLatitude, maxLongitude);
		return placeDtoMapper.toSearchedPlaceReponseList(places);
	}
}
