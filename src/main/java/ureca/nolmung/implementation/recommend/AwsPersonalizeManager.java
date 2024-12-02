package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AwsPersonalizeManager {

    private final String campaignArn;
    private final PersonalizeRuntimeClient personalizeRuntimeClient;
    private final PlaceRepository placeRepository;
    private final RedisTemplate<String, List<RecommendResp>> redisTemplate;
    private static final int DEFAULT_NUM_RESULTS = 25; // 기본값 설정

    //TODO List<String> 같은거로는 안되려나
    public List<PredictedItem> getRecs(Long userId) {

        List<PredictedItem> items = null;
        try {
            GetRecommendationsRequest recommendationsRequest = GetRecommendationsRequest.builder()
                    .campaignArn(campaignArn)
                    .numResults(DEFAULT_NUM_RESULTS)
                    .userId(String.valueOf(userId))
                    .build();

            GetRecommendationsResponse recommendationsResponse = personalizeRuntimeClient
                    .getRecommendations(recommendationsRequest);
            items = recommendationsResponse.itemList();

        } catch (AwsServiceException e) {
            //TODO 공통 예외처리 고민
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
        return items;
    }

    public List<Place> getPlaces(List<PredictedItem> recs) {
        List<Place> places = new ArrayList<>();

        for (PredictedItem item : recs) {
            Long id = Long.valueOf(item.itemId());
            places.add(placeRepository
                    .findById(id)
                    .orElseThrow(() -> new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION)));
        }

        return places;
    }

    public List<PredictedItem> getRandomRecs(List<PredictedItem> recs, int count) {
        Random random = new Random();
        if (recs.size() <= count) {
            return new ArrayList<>(recs);
        }
        List<PredictedItem> modifiableRecs = new ArrayList<>(recs);

        Collections.shuffle(modifiableRecs, random);

        return new ArrayList<>(modifiableRecs.subList(0, count));
    }

    public List<RecommendResp> saveRedis(List<Place> places, Long userId) {
        List<RecommendResp> recommendResponses = places.stream()
                .map(place -> new RecommendResp(
                        place.getId(),
                        place.getName(),
                        place.getCategory(),
                        place.getRoadAddress(),
                        place.getAddress(),
                        place.getPlaceImageUrl(),
                        place.getRatingAvg(),
                        place.getRatingCount()
                ))
                .collect(Collectors.toList());

        redisTemplate.opsForValue().set(String.valueOf(userId), recommendResponses);

        return recommendResponses;
    }

    public List<RecommendResp> getRandomRecommendResps(List<RecommendResp> recommendResps, int count) {
        if (count >= recommendResps.size()) {
            return recommendResps; // 요청 개수가 리스트 크기 이상이면 전체 반환
        }

        // 원본 리스트를 복사하고 셔플
        List<RecommendResp> shuffledList = new ArrayList<>(recommendResps); // 복사본 생성
        Collections.shuffle(shuffledList); // 셔플

        // 앞에서 count개 추출
        return shuffledList.subList(0, count);
    }

    public List<RecommendResp> getRedis(Long userId) {
        return redisTemplate.opsForValue().get(String.valueOf(userId));
    }
}
