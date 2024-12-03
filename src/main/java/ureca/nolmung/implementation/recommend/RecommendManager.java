package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RecommendManager {

    private final PlaceRepository placeRepository;

    public List<Place> getMostBookmarkedPlaces() {
        return placeRepository.findTopNPlaces(PageRequest.of(0, 5));
    }

    public List<Place> getPlaceRecommendationsForDogs(List<Dog> dogs) {
        List<Place> allPlaces = new ArrayList<>();
        for (Dog dog : dogs) {
            List<Place> places = placeRepository.findAllByDogSize(dog.getSize().name());
            allPlaces.addAll(places);
        }
        return allPlaces;
    }

    public List<Place> getPlaceRecommendationsNearByUser(String addressProvince) {
        return placeRepository.findByUserAddress(addressProvince);
    }

    public List<Place> getRandomPlaces(List<Place> places, int count) {
        Random random = new Random();
        if (places.size() <= count) {
            return new ArrayList<>(places);
        }

        Collections.shuffle(places, random);

        return new ArrayList<>(places.subList(0, count));
    }
}