package ureca.nolmung.business.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecommendService implements RecommendUseCase {
    private final AwsPersonalizeManager awsPersonalizeManager;
    private final RecommendManager recommendManager;
    private final DogManager dogManager;
    private final RecommendDtoMapper recommendDtoMapper;
    private final RedisManager redisManager;

    private static final int RANDOM_SELECTION_COUNT = 5;
    private static final int TIME_TO_LIVE = 48;
    private static final int MIN_TTL_THRESHOLD = 600;

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getMostBookmarkedPlaces() {
        log.info("좋아요 수 기반 추천");
        List<Place> places = recommendManager.getMostBookmarkedPlaces();
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsForDogs(User user) {
        log.info("반려견 크기 기반 추천");
        List<Dog> dogs = dogManager.getDogList(user.getId());
        List<Place> places = recommendManager.getPlaceRecommendationsForDogs(dogs);
        List<Place> randomSelectNearByPlaces = recommendManager.randomSelectPlaces(places, RANDOM_SELECTION_COUNT);
        return recommendDtoMapper.toGetPlaceRecommendations(randomSelectNearByPlaces);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getPlaceRecommendationsNearByUser(User user) {
        log.info("근처 추천");
        List<Place> nearByPlaces = recommendManager.findNearbyPlaces(user.getUserLatitude(), user.getUserLongitude());
        List<Place> randomSelectNearByPlaces = recommendManager.randomSelectPlaces(nearByPlaces, RANDOM_SELECTION_COUNT);
        return recommendDtoMapper.toGetPlaceRecommendations(randomSelectNearByPlaces);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalize(User user) {
        String key = String.valueOf(user.getId());
        Long ttl = redisManager.getExpire(key, TimeUnit.SECONDS);

        if (ttl != null && ttl > MIN_TTL_THRESHOLD) {
            log.info("레디스에 있습니다");
            redisManager.addExpire(key, TIME_TO_LIVE, TimeUnit.HOURS);
            List<RecommendResp> recommendResps = redisManager.getRedis(user.getId());
            return awsPersonalizeManager.getRandomRecommendResps(recommendResps, RANDOM_SELECTION_COUNT);
        }
        log.info("레디스에 없습니다");
        List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(user.getId());
        List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
        List<RecommendResp> recommendResps = redisManager.saveRedis(places, key);
        return awsPersonalizeManager.getRandomRecommendResps(recommendResps, RANDOM_SELECTION_COUNT);
    }
}
