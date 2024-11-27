package ureca.nolmung.presentation.controller.place.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.business.place.request.PlaceOnMapServiceRequest;

@Getter
@NoArgsConstructor
public class PlaceOnMapRequest {

	@NotNull(message = "위도 값은 필수입니다.")
	private double latitude;

	@NotNull(message = "경도 값은 필수입니다.")
	private double longitude;

	@NotNull(message = "최대 위도 값은 필수입니다.")
	private double maxLatitude;

	@NotNull(message = "최대 경도 값은 필수입니다.")
	private double maxLongitude;

	@Builder
	public PlaceOnMapRequest(double latitude, double longitude, double maxLatitude, double maxLongitude) {
		this.latitude = latitude;
		this.longitude = longitude;
		this.maxLatitude = maxLatitude;
		this.maxLongitude = maxLongitude;
	}

	public PlaceOnMapServiceRequest toServiceRequest() {
		return PlaceOnMapServiceRequest.builder()
			.latitude(latitude)
			.longitude(longitude)
			.maxLatitude(maxLatitude)
			.maxLongitude(maxLongitude)
			.build();
	}

}
