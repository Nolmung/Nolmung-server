package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class AwsPersonalizeManager {

    private final String campaignArn;
    private final PersonalizeRuntimeClient personalizeRuntimeClient;
    private final PlaceRepository placeRepository;
    private static final int DEFAULT_NUM_RESULTS = 50;

    public List<PredictedItem> getRecs(Long userId) {
        // Aws Personalize Api에 요청하여 추천 목록 받아오기
        List<PredictedItem> items = new ArrayList<>();
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
            log.error("Personalize에서 추천 목록을 가져오던 중 오류 발생: {}", e.awsErrorDetails().errorMessage());
            return items;
        }
        return items;
    }

    // PredictedItem List를 Place 객체 List로 바꾸기
    public List<Place> getPlaces(List<PredictedItem> recs) {
        List<Long> ids = recs.stream()
                .map(item -> Long.valueOf(item.itemId()))
                .collect(Collectors.toList());
        List<Place> places = placeRepository.findAllById(ids);
        if (places.size() != ids.size()) {
            throw new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION);
        }
        return places;
    }

    public List<RecommendResp> getRandomRecommendResps(List<RecommendResp> recommendResps, int count) {
        if (count >= recommendResps.size()) {
            return recommendResps;
        }
        Collections.shuffle(recommendResps);
        return recommendResps.subList(0, count);
    }
}
