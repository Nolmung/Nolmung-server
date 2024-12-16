package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.place.PlaceRepository;
import ureca.nolmung.persistence.placeposition.PlacePositionRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecommendManager {

    private final PlaceRepository placeRepository;
    private final PlacePositionRepository placePositionRepository;

    private final int SRID = 4326;
    private static final double RADIUS = 5.0;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    public List<Place> getMostBookmarkedPlaces(int count) {
        return placeRepository.findTopNPlaces(PageRequest.of(0, count));
    }

    public List<Place> getPlaceRecommendationsForDogs(List<Dog> dogs) {
        Set<String> sizes = dogs.stream()
                .map(dog -> dog.getSize().name())
                .collect(Collectors.toSet());

        return placeRepository.findAllByDogSizes(sizes);
    }

    // Polygon 안의 장소 찾기
    public List<Place> findNearbyPlaces(double userLatitude, double userLongitude) {
        Polygon polygon = generateCirclePolygon(userLatitude, userLongitude);
        return placePositionRepository.findPlaceByCoordinate(polygon);
    }

    // 유저의 위도와 경도를 이용하여 원형 Polygon 만들기
    private Polygon generateCirclePolygon(double userLatitude, double userLongitude) {
        int numPoints = 100;
        Coordinate[] coordinates = new Coordinate[numPoints + 1];
        for (int i = 0; i <= numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            double latOffset = RecommendManager.RADIUS / 111.0 * Math.sin(angle);
            double lonOffset = RecommendManager.RADIUS / (111.0 * Math.cos(Math.toRadians(userLatitude))) * Math.cos(angle);
            coordinates[i] = new Coordinate(userLongitude + lonOffset, userLatitude + latOffset);
        }
        coordinates[numPoints] = coordinates[0];
        Polygon polygon = geometryFactory.createPolygon(coordinates);
        polygon.setSRID(SRID);
        return polygon;
    }


    public List<Place> randomSelectPlaces(List<Place> nearbyPlaces, int count) {
        if (nearbyPlaces.size() <= count) {
            return nearbyPlaces;
        }
        Collections.shuffle(nearbyPlaces);

        return nearbyPlaces.subList(0, count);
    }
}