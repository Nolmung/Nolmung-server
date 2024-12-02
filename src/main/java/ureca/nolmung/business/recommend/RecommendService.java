package ureca.nolmung.business.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.implementation.dog.DogManager;
import ureca.nolmung.implementation.recommend.AwsPersonalizeManager;
import ureca.nolmung.implementation.recommend.RecommendManager;
import ureca.nolmung.implementation.recommend.dtomapper.RecommendDtoMapper;
import ureca.nolmung.implementation.user.UserManager;
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
    private final UserManager userManager;
    private final RecommendDtoMapper recommendDtoMapper;
    private final RedisTemplate<String, List<Place>> redisTemplate;

    @Override
    public List<RecommendResp> getMostBookmarkedPlaces() {
        List<Place> places = recommendManager.getMostBookmarkedPlaces();
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsForDogs(Long userId) {
        List<Dog> dogs = dogManager.getDogList(userId);
        List<Place> places = recommendManager.getPlaceRecommendationsForDogs(dogs);
        List<Place> randomPlaces = recommendManager.getRandomPlaces(places, 5);
        return recommendDtoMapper.toGetPlaceRecommendations(randomPlaces);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsNearByUser(Long userId) {
        User user = userManager.validateUserExistence(userId);
        List<Place> places = recommendManager.getPlaceRecommendationsNearByUser(user.getAddressProvince());
        List<Place> randomPlaces = recommendManager.getRandomPlaces(places, 5);
        return recommendDtoMapper.toGetPlaceRecommendations(randomPlaces);
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalize(Long userId) {
        // 유저 검증, 없으면 에러
        userManager.validateUserExistence(userId);
        // 유저 아이디로 레디스에 데이터 조회
        Boolean isKeyExists = redisTemplate.hasKey(String.valueOf(userId));

        // 레디스에 데이터가 있으면 ( ture )
        if (isKeyExists) {
            log.info("레디스에 있는 데이터입니다.");
            // 레디스에서 유저 아이디로 조회해서 밸류 가져오기
            List<RecommendResp> recommendResps = awsPersonalizeManager.getRedis(userId);
            log.info("성공적으로 레디스에서 데이터를 가져왔습니다.");
            return awsPersonalizeManager.getRandomRecommendResps(recommendResps, 5);
        } else {
            log.info("레디스에 없는 데이터입니다.");
            // 데이터가 없으면
            // personalize 호출해서 가져오기
            List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(userId);
            // 가공
            List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
            // 레디스에 저장
            List<RecommendResp> recommendResps = awsPersonalizeManager.saveRedis(places, userId);
            log.info("레디스에 데이터를 새로 저장했습니다");

            return awsPersonalizeManager.getRandomRecommendResps(recommendResps, 5);
        }
        //TODO refactoring 필요
    }

    @Override
    public List<RecommendResp> getPlaceRecommendationsFromPersonalizeForBatch(Long userId) {
        List<PredictedItem> awsRecs = awsPersonalizeManager.getRecs(userId);
        List<Place> places = awsPersonalizeManager.getPlaces(awsRecs);
        return recommendDtoMapper.toGetPlaceRecommendations(places);
    }
}
