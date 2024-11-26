package ureca.nolmung.business.place;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.implementation.label.LabelManager;
import ureca.nolmung.implementation.place.PlaceManager;
import ureca.nolmung.implementation.place.dtomapper.PlaceDtoMapper;
import ureca.nolmung.jpa.label.Label;
import ureca.nolmung.jpa.place.Place;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class PlaceService implements PlaceUseCase {

	private final PlaceManager placeManager;
	private final LabelManager labelManager;
	private final PlaceDtoMapper placeDtoMapper;

	@Override
	public List<SearchedPlaceResponse> searchByKeyword(String keyword) {
		List<Place> places = placeManager.searchByKeyword(keyword);
		return placeDtoMapper.toSearchedPlaceReponseList(places);
	}

	@Override
	public PlaceDetailResponse findPlaceDetailById(long placeId) {
		Place place = placeManager.findPlaceById(placeId);
		List<Label> labels = labelManager.findLabelsByPlaceId(placeId);
		return null;
	}

	@Transactional
	@Override
	public Integer makePointData() {
		List<Place> places = placeManager.findAllPlace();
		placeManager.makePointData(places);
		return places.size();
	}
}
