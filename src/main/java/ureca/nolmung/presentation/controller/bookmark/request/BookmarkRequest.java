package ureca.nolmung.presentation.controller.bookmark.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.business.bookmark.request.BookmarkServiceRequest;

@Getter
@NoArgsConstructor
public class BookmarkRequest {

	@NotNull(message = "장소id 값은 필수입니다.")
	private Long placeId;

	public BookmarkServiceRequest toServiceRequest() {
		return BookmarkServiceRequest.builder()
			.placeId(placeId)
			.build();
	}
}
