package ureca.nolmung.business.bookmark.response;

import lombok.Builder;
import lombok.Getter;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.place.Place;

@Getter
@Builder
public class BookmarkResponse {

	private Long bookmarkId;
	private Long placeId;
	private Category category;
	private String placeImageUrl;
	private String roadAddress;
	private String name;
	private double ratingAvg;
	private Integer ratingCount;

	public static BookmarkResponse of (Bookmark bookmark, Place place) {
		return BookmarkResponse.builder()
			.bookmarkId(bookmark.getId())
			.placeId(place.getId())
			.category(place.getCategory())
			.placeImageUrl(place.getPlaceImageUrl())
			.roadAddress(place.getRoadAddress())
			.name(place.getName())
			.ratingAvg(place.getRatingAvg())
			.ratingCount(place.getRatingCount())
			.build();
	}

}
