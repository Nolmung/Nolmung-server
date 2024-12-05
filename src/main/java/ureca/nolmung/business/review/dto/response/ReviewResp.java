package ureca.nolmung.business.review.dto.response;

import java.util.List;

public record ReviewResp (
        Long id,
        Long placeId,
        String placeName,
        String address,
        int rating,
        List<LabelResp> Labels
        )
{ }
