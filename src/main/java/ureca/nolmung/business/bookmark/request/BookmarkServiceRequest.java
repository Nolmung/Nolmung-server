package ureca.nolmung.business.bookmark.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.bookmark.Bookmark;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

@Getter
@NoArgsConstructor
public class BookmarkServiceRequest {

	private Long placeId;

	@Builder
	public BookmarkServiceRequest(Long placeId) {
		this.placeId = placeId;
	}

	public Bookmark toEntity(User user, Place place) {
		return Bookmark.builder()
			.user(user)
			.place(place)
			.build();
	}
}
