package ureca.nolmung.business.review.dto.response;

import java.time.LocalDateTime;
import java.util.List;

import ureca.nolmung.jpa.place.Enum.Category;

public record ReviewResp (
        Long reviewId,
        Long placeId,
        Category category,
        String placeName,
        String address,
        int rating,
        List<LabelResp> Labels,
		LocalDateTime createdAt
        )
{ }
