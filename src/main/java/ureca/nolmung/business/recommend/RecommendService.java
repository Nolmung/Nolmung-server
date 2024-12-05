package ureca.nolmung.business.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.implementation.dog.DogManager;
import ureca.nolmung.implementation.recommend.AwsPersonalizeManager;
import ureca.nolmung.implementation.recommend.RecommendManager;
import ureca.nolmung.implementation.recommend.dtomapper.RecommendDtoMapper;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService implements RecommendUseCase{
    private final AwsPersonalizeManager awsPersonalizeManager;
    private final RecommendManager recommendManager;
    private final DogManager dogManager;
    private final RecommendDtoMapper recommendDtoMapper;
    private final RedisTemplate<String, List<Place>> redisTemplate;

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getMostBookmarkedPlaces() {
        List<Place> places = recommendManager.getMostBookmarkedPlaces();
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsForDogs(User user) {
        List<Dog> dogs = dogManager.getDogList(user.getId());
        List<Place> places = recommendManager.getPlaceRecommendationsForDogs(dogs);
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsNearByUser(User user) {
        List<Place> nearbyPlaces = recommendManager.findNearbyPlaces(user.getUserLatitude(), user.getUserLongitude());
        return recommendDtoMapper.toGetPlaceRecommendations(nearbyPlaces);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalize(User user) {

        Boolean isKeyExists = redisTemplate.hasKey(String.valueOf(user.getId()));

        if (isKeyExists) {
            List<RecommendResp> recommendResps = awsPersonalizeManager.getRedis(user.getId());
            return awsPersonalizeManager.getRandomRecommendResps(recommendResps, 5);
        } else {
            List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(user.getId());
            List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
            List<RecommendResp> recommendResps = awsPersonalizeManager.saveRedis(places, user.getId());
            return awsPersonalizeManager.getRandomRecommendResps(recommendResps, 5);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsFromPersonalizeForBatch(Long userId) {
        List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(userId);
        List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }
}
