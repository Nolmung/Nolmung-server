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

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecommendManager {

    private final PlaceRepository placeRepository;
    private final PlacePositionRepository placePositionRepository;

    private final int SRID = 4326;
    private static final double radius = 5.0;
    private final GeometryFactory geometryFactory = new GeometryFactory();
    public List<Place> getMostBookmarkedPlaces() {
        return placeRepository.findTopNPlaces(PageRequest.of(0, 5));
    }

    public List<Place> getPlaceRecommendationsForDogs(List<Dog> dogs) {
        Set<String> sizes = dogs.stream()
                .map(dog -> dog.getSize().name())
                .collect(Collectors.toSet());

        return placeRepository.findAllByDogSizes(sizes);
    }

    public List<Place> findNearbyPlaces(double userLatitude, double userLongitude) {
        Polygon polygon = generateCirclePolygon(userLatitude, userLongitude, radius);
        return placePositionRepository.findPlaceByCoordinate(polygon);
    }

    private Polygon generateCirclePolygon(double userLatitude, double userLongitude, double radius) {
        int numPoints = 100;
        Coordinate[] coordinates = new Coordinate[numPoints + 1];
        for (int i = 0; i <= numPoints; i++) {
            double angle = 2 * Math.PI * i / numPoints;
            double latOffset = radius / 111.0 * Math.sin(angle);
            double lonOffset = radius / (111.0 * Math.cos(Math.toRadians(userLatitude))) * Math.cos(angle);
            coordinates[i] = new Coordinate(userLongitude + lonOffset, userLatitude + latOffset);
        }
        coordinates[numPoints] = coordinates[0]; // 원을 닫기 위해 첫 점을 마지막에 추가
        Polygon polygon = geometryFactory.createPolygon(coordinates);
        polygon.setSRID(SRID);
        return polygon;
    }


}