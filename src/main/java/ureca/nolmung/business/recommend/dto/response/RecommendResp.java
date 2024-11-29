package ureca.nolmung.business.recommend.dto.response;

import ureca.nolmung.jpa.place.Enum.Category;

public record RecommendResp(
        Long placeId, String placeName,
        Category category, String roadAddress,
        String placeImageUrl, Double ratingAvg,
        Integer ratingCount) {
}
