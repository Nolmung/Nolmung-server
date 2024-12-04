package ureca.nolmung.business.recommend;

import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.jpa.user.User;

import java.util.List;

public interface RecommendUseCase {
    List<RecommendResp> getMostBookmarkedPlaces();
    List<RecommendResp> getPlaceRecommendationsForDogs(User user);
    List<RecommendResp> getPlaceRecommendationsNearByUser(User user);
    List<RecommendResp> getPlaceRecommendationsFromPersonalize(User user);
    List<RecommendResp> getPlaceRecommendationsFromPersonalizeForBatch(Long userId);
}
