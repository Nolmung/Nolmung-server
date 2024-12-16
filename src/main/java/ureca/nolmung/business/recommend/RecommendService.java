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

    private static final int RANDOM_SELECTION_COUNT = 10;
    private static final int SELECTION_COUNT = 10;
    // Redis TTL
    private static final int TIME_TO_LIVE = 48;
    private static final int MIN_TTL_THRESHOLD = 600;

    @Override
    @Transactional(readOnly = true)
    public List<RecommendResp> getMostBookmarkedPlaces() {
        log.info("좋아요 수 기반 추천");
        List<Place> places = recommendManager.getMostBookmarkedPlaces(SELECTION_COUNT);
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
        // 사용자 거주지 근처의 장소 리스트 가져오기, 공간 함수 사용
        List<Place> nearByPlaces = recommendManager.findNearbyPlaces(user.getUserLatitude(), user.getUserLongitude());
        List<Place> randomSelectNearByPlaces = recommendManager.randomSelectPlaces(nearByPlaces, RANDOM_SELECTION_COUNT);
        return recommendDtoMapper.toGetPlaceRecommendations(randomSelectNearByPlaces);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalize(User user) {
        log.info("개인 맞춤형 추천");
        String key = String.valueOf(user.getId());
        // Redis에서 Key로 조회한 Value의 TTL 조회
        Long ttl = redisManager.getExpire(key, TimeUnit.SECONDS);

        if (ttl != null && ttl > MIN_TTL_THRESHOLD) {
            // TTL 갱신
            redisManager.addExpire(key, TIME_TO_LIVE, TimeUnit.HOURS);
            List<RecommendResp> recommendResps = redisManager.getRedis(user.getId());
            return awsPersonalizeManager.getRandomRecommendResps(recommendResps, RANDOM_SELECTION_COUNT);
        }
        // AWS Personalize Campaign에 요청을 보내 추천 장소 정보 받아오기
        List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(user.getId());
        // 받아온 장소 정보들로 데이터베이스에 있는 장소 객체 가져오기
        List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
        List<RecommendResp> recommendResps = redisManager.saveRedis(places, key);
        return awsPersonalizeManager.getRandomRecommendResps(recommendResps, RANDOM_SELECTION_COUNT);
    }
}
