package ureca.nolmung.business.place.response;

import lombok.Builder;
import lombok.Getter;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

@Getter
@Builder
public class SearchedPlaceResponse {

	private Long placeId;
	private String placeName;
	private Category category;
	private String roadAddress;
	private String placeImgUrl;
	private Double starRatingAvg;
	private int reviewCount;
	private double latitude;
	private double longitude;

	public static SearchedPlaceResponse of(Place place) {
		return SearchedPlaceResponse.builder()
			.placeId(place.getId())
			.placeName(place.getName())
			.category(place.getCategory())
			.roadAddress(place.getRoadAddress())
			.placeImgUrl(place.getPlaceImageUrl())
			.starRatingAvg(place.getRatingAvg())
			.reviewCount(place.getRatingCount())
			.latitude(place.getLatitude())
			.longitude(place.getLongitude())
			.build();
	}

}

