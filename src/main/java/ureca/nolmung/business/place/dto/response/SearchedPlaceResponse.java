package ureca.nolmung.business.place.dto.response;

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
	private String address;
	private String roadAddress;
	private String placeImgUrl;
	private String acceptSize;
	private Double starRatingAvg;
	private int reviewCount;
	private double latitude;
	private double longitude;
	private Boolean isBookmarked;

	public static SearchedPlaceResponse of(Place place, Boolean isBookmarked) {
		return SearchedPlaceResponse.builder()
			.placeId(place.getId())
			.placeName(place.getName())
			.category(place.getCategory())
			.address(place.getAddress())
			.roadAddress(place.getRoadAddress())
			.placeImgUrl(place.getPlaceImageUrl())
			.starRatingAvg(place.getRatingAvg())
			.reviewCount(place.getRatingCount())
			.acceptSize(place.getAcceptSize())
			.latitude(place.getLatitude())
			.longitude(place.getLongitude())
			.isBookmarked(isBookmarked)
			.build();
	}

}

