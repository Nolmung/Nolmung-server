package ureca.nolmung.business.review.dto.response;

import ureca.nolmung.jpa.place.Enum.Category;

import java.util.List;

public record ReviewResp (
        Long reviewId,
        Long placeId,
        Category category,
        String placeName,
        String address,
        int rating,
        List<LabelResp> Labels
        )
{ }
