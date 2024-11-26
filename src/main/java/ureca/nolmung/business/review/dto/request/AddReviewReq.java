package ureca.nolmung.business.review.dto.request;

import java.util.List;

public record AddReviewReq(int rating, Long placeId, String category, List<LabelDto> labels) {
    public record LabelDto(Long labelId, String labelName) {
    }
}
