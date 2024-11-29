package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.dog.DogRepository;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class RecommendManager {

    private final PlaceRepository placeRepository;
    private final DogRepository dogRepository;

    public List<Place> getMostBookmarkedPlaces() {
        List<Place> places = placeRepository.findTopNPlaces(PageRequest.of(0, 5));
        for (Place place : places) {
            System.out.println(place);
        }
        return places;
    }

    public List<Place> getPlaceRecommendationsForDogs(List<Dog> dogs) {
        List<Place> allPlaces = new ArrayList<>();
        for (Dog dog : dogs) {
            List<Place> places = placeRepository.findAllByDogSize(dog.getSize().name());
            allPlaces.addAll(places);
        }
        return allPlaces;
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