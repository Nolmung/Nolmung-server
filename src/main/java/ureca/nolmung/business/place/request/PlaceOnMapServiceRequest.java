package ureca.nolmung.business.place.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PlaceOnMapServiceRequest {

	private double latitude;
	private double longitude;
	private double maxLatitude;
	private double maxLongitude;

	@Builder
	public PlaceOnMapServiceRequest(double latitude, double longitude, double maxLatitude, double maxLongitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.maxLatitude = maxLatitude;
		this.maxLongitude = maxLongitude;
	}
}
