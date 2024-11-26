package ureca.nolmung.implementation.place.dtomapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.jpa.place.Place;

@Component
public class PlaceDtoMapper {

	public List<SearchedPlaceResponse> toSearchedPlaceReponseList(List<Place> places) {
		return places.stream()
			.map(SearchedPlaceResponse::of)
			.collect(Collectors.toList());
	}

}
