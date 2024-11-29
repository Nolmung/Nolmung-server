package ureca.nolmung.business.recommend;

import ureca.nolmung.business.recommend.dto.response.RecommendResp;

import java.util.List;

public interface RecommendUseCase {
    List<RecommendResp> getPlaceRecommendationsFromPersonalize(Long userId);
    List<RecommendResp> getMostBookmarkedPlaces();
    List<RecommendResp> getPlaceRecommendationsForDogs(Long userId);
}