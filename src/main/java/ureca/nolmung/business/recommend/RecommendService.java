package ureca.nolmung.business.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.implementation.recommend.AwsPersonalizeManager;
import ureca.nolmung.implementation.recommend.RecommendManager;
import ureca.nolmung.implementation.recommend.dtomapper.RecommendDtoMapper;
import ureca.nolmung.jpa.place.Place;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecommendService implements RecommendUseCase{
    private final AwsPersonalizeManager awsPersonalizeManager;
    private final RecommendManager recommendManager;
    private final RecommendDtoMapper recommendDtoMapper;


    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalize(Long userId) {
        List<PredictedItem> recs = awsPersonalizeManager.getRecs(userId);
        List<PredictedItem> randomRecs = awsPersonalizeManager.getRandomRecs(recs);
        List<Place> places = awsPersonalizeManager.getPlaces(randomRecs);
        return recommendDtoMapper.toGetPlaceRecommendationsFromPersonalize(places);
    }
}
