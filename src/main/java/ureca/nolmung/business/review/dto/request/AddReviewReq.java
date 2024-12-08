package ureca.nolmung.business.review.dto.request;

import java.util.List;

public record AddReviewReq(
        List<ReviewDto> reviews
) {
    public record ReviewDto(
            Long placeId,
            int rating,
            String category,
            List<LabelDto> labels
    ) {
        public record LabelDto(
                Long labelId,
                String labelName
        ) {
        }
    }
}
