package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecommendManager {

    private final PlaceRepository placeRepository;

    public List<Place> getMostBookmarkedPlaces() {
        return placeRepository.findTopNPlaces(PageRequest.of(0, 5));
    }

    public List<Place> getPlaceRecommendationsForDogs(List<Dog> dogs) {
        Set<String> sizes = dogs.stream()
                .map(dog -> dog.getSize().name())
                .collect(Collectors.toSet());

        return placeRepository.findAllByDogSizes(sizes);
    }

    public List<Place> getPlaceRecommendationsNearByUser(String addressProvince) {
        return placeRepository.findByUserAddress(addressProvince);
    }
}