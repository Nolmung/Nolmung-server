package ureca.nolmung.business.place.response;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.label.Label;
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
	private List<Label> labels;
	private Integer totalDiaries;
	private List<Diary> diaries;
	private double latitude;
	private double longitude;

	public static PlaceDetailResponse of(Place place, List<Label> labels, List<Diary> diaries) {
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
			.latitude(place.getLatitude())
			.longitude(place.getLongitude())
			.labels(labels)
			.diaries(diaries)
			.build();
	}

}
