package ureca.nolmung.business.place.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import ureca.nolmung.business.diary.response.PlaceDiaryResponse;
import ureca.nolmung.business.labels.response.LabelResponse;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

@Getter
@Builder
public class PlaceDetailResponse {

	private Long placeId;
	private String placeName;
	private Category category;
	private String placeImgUrl;
	private String roadAddress;
	private String address;
	private String phone;
	private String homepageUrl;
	private String holiday;
	private String openHour;
	private boolean parkingYn;
	private String price;
	private String acceptSize;
	private String rule;
	private boolean inPossibleYn;
	private boolean outPossibleYn;
	private String extraPrice;
	private Double starRatingAvg;
	private Integer reviewCount;
	private List<LabelResponse> labels;
	private Integer totalDiaries;
	private List<PlaceDiaryResponse> diaries;
	private double latitude;
	private double longitude;
	private Boolean isBookmarked;

	public static PlaceDetailResponse of(Place place, List<LabelResponse> labels, List<PlaceDiaryResponse> diaries, Boolean isBookmarked) {
		return PlaceDetailResponse.builder()
			.placeId(place.getId())
			.placeName(place.getName())
			.category(place.getCategory())
			.placeImgUrl(place.getPlaceImageUrl())
			.roadAddress(place.getRoadAddress())
			.address(place.getAddress())
			.phone(place.getPhone())
			.homepageUrl(place.getHomepageUrl())
			.holiday(place.getHoliday())
			.openHour(place.getOpenHour())
			.parkingYn(place.isParkingYn())
			.price(place.getPrice())
			.acceptSize(place.getAcceptSize())
			.rule(place.getRule())
			.inPossibleYn(place.isInPossibleYn())
			.outPossibleYn(place.isOutPossibleYn())
			.extraPrice(place.getExtraPrice())
			.starRatingAvg(place.getRatingAvg())
			.reviewCount(place.getRatingCount())
			.isBookmarked(isBookmarked)
			.latitude(place.getLatitude())
			.longitude(place.getLongitude())
			.labels(labels)
			.diaries(diaries)
			.build();
	}

}
