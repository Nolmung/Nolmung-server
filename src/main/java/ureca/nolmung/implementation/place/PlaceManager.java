package ureca.nolmung.implementation.place;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.placeposition.PlacePosition;
import ureca.nolmung.persistence.place.PlaceRepository;
import ureca.nolmung.persistence.placeposition.PlacePositionRepository;

@Component
@RequiredArgsConstructor
public class PlaceManager {

	private final PlaceRepository placeRepository;
	private final PlacePositionRepository placePositionRepository;

	private final GeometryFactory geometryFactory = new GeometryFactory();

	public List<Place> searchByKeyword(String keyword) {
		return placeRepository.searchByKeyword(keyword);
	}

	public Place findPlaceById(long placeId) {
		return placeRepository.findById(placeId)
			.orElseThrow(() -> new PlaceException(PlaceExceptionType.Place_NOT_FOUND_EXCEPTION));
	}

	public List<Place> findAllPlace() {
		return placeRepository.findAll();
	}

	public void makePointData(List<Place> places) {
		for (Place place : places) {
			Point point = geometryFactory.createPoint(new Coordinate(place.getLongitude(), place.getLatitude()));
			point.setSRID(4326);
			PlacePosition placePosition = PlacePosition.builder()
				.place(place)
				.location(point)
				.build();
			placePositionRepository.save(placePosition);
		}
	}
}
