package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecommendManager {

    private final PlaceRepository placeRepository;

    public List<Place> getMostBookmarkedPlaces() {
        List<Place> places = placeRepository.findTopNPlaces(PageRequest.of(0, 5));
        for (Place place : places) {
            System.out.println(place);
        }
        return places;
    }
}
