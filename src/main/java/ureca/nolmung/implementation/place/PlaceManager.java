package ureca.nolmung.implementation.place;

import java.util.List;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.place.request.PlaceOnMapServiceRequest;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.placeposition.PlacePosition;
import ureca.nolmung.persistence.place.PlaceRepository;
import ureca.nolmung.persistence.place.PlaceRepositoryImpl;
import ureca.nolmung.persistence.placeposition.PlacePositionRepository;

@Component
@RequiredArgsConstructor
public class PlaceManager {

	private final int SRID = 4326;

	private final PlaceRepository placeRepository;
	private final PlacePositionRepository placePositionRepository;
	private final PlaceRepositoryImpl placeRepositoryImpl;

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
			point.setSRID(SRID);
			PlacePosition placePosition = PlacePosition.builder()
				.place(place)
				.location(point)
				.build();
			placePositionRepository.save(placePosition);
		}
	}

	public List<Place> findBySearchOption(Category category, String acceptSize, Double ratingAvg) {
		return placeRepositoryImpl.findBySearchOption(category, acceptSize, ratingAvg);
	}

	public List<Place> findPlaceMapOn(PlaceOnMapServiceRequest serviceRequest) {
		Polygon polygon = generatePolygon(serviceRequest);
		return placePositionRepository.findPlaceByCoordinate(polygon);
	}

	private Polygon generatePolygon(PlaceOnMapServiceRequest serviceRequest) {
		Coordinate[] vertexes = createVertexes(serviceRequest);
		Polygon polygon = geometryFactory.createPolygon(vertexes);
		polygon.setSRID(SRID);
		return polygon;
	}

	private Coordinate[] createVertexes(PlaceOnMapServiceRequest serviceRequest) {
		double latitude = serviceRequest.getLatitude();
		double latitudeDelta = calcLatitudeDelta(serviceRequest.getMaxLatitude(), serviceRequest.getLatitude());
		double longitude = serviceRequest.getLongitude();
		double longitudeDelta = calcLongitudeDelta(serviceRequest.getMaxLongitude(), serviceRequest.getLongitude());

		double maxLatitude = latitude + latitudeDelta;
		double minLatitude = latitude - latitudeDelta;
		double maxLongitude = longitude + longitudeDelta;
		double minLongitude = longitude - longitudeDelta;

		return new Coordinate[] {
			new Coordinate(maxLongitude, minLatitude),
			new Coordinate(maxLongitude, maxLatitude),
			new Coordinate(minLongitude, maxLatitude),
			new Coordinate(minLongitude, minLatitude),
			new Coordinate(maxLongitude, minLatitude)
		};
	}

	private double calcLatitudeDelta(double maxLatitude, double latitude) {
		return maxLatitude - latitude;
	}

	private double calcLongitudeDelta(double maxLongitude, double longitude) {
		return maxLongitude - longitude;
	}

}
