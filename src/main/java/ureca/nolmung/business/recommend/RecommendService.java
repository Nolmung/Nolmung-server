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
import ureca.nolmung.implementation.recommend.RedisManager;
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
    private final RedisTemplate<String, List<RecommendResp>> redisTemplate;
    private final RedisManager redisManager;

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getMostBookmarkedPlaces() {
        List<Place> places = recommendManager.getMostBookmarkedPlaces();
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsForDogs(User user) {

        Boolean isKeyExists = redisTemplate.hasKey("dog" + user.getId());

        if (isKeyExists) {
            List<RecommendResp> recommendResps = redisManager.getRedis("dog" + user.getId());
            return recommendManager.getRandomRecommendResps(recommendResps, 5);

        } else {
            List<Dog> dogs = dogManager.getDogList(user.getId());
            List<Place> places = recommendManager.getPlaceRecommendationsForDogs(dogs);
            List<RecommendResp> recommendResps = redisManager.saveRedis(places, "dog" + user.getId());
            return recommendManager.getRandomRecommendResps(recommendResps, 5);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsNearByUser(User user) {

//        Boolean isKeyExists = redisTemplate.hasKey("near" + user.getId());

        List<Place> nearByPlaces = recommendManager.findNearbyPlaces(user.getUserLatitude(), user.getUserLongitude());
        List<Place> randomSelectNearByPlaces = recommendManager.randomSelectPlaces(nearByPlaces);
        return recommendDtoMapper.toGetPlaceRecommendations(randomSelectNearByPlaces);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalize(User user) {

//        Boolean isKeyExists = redisTemplate.hasKey(String.valueOf(user.getId()));
        Boolean isKeyExists = redisTemplate.hasKey("personalize" + user.getId());

        if (isKeyExists) {
            List<RecommendResp> recommendResps = redisManager.getRedis("personalize" + user.getId());
            return recommendManager.getRandomRecommendResps(recommendResps, 5);
        } else {
            List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(user.getId());
            List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
            List<RecommendResp> recommendResps = redisManager.saveRedis(places, "personalize" + user.getId());
            return recommendManager.getRandomRecommendResps(recommendResps, 5);
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
