package ureca.nolmung.implementation.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.exception.AwsServiceException;
import software.amazon.awssdk.services.personalizeruntime.PersonalizeRuntimeClient;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsRequest;
import software.amazon.awssdk.services.personalizeruntime.model.GetRecommendationsResponse;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AwsPersonalizeManager {

    private final String campaignArn;
    private final PersonalizeRuntimeClient personalizeRuntimeClient;
    private final PlaceRepository placeRepository;

    public List<PredictedItem> getRecs(Long userId) {

        List<PredictedItem> items = null;
        try {
            GetRecommendationsRequest recommendationsRequest = GetRecommendationsRequest.builder()
                    .campaignArn(campaignArn)
                    .numResults(25)
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
                    .orElseThrow(() -> new PlaceException(PlaceExceptionType.Place_NOT_FOUND_EXCEPTION)));
        }

        return places;
    }

    //TODO 파라미터로 가져갈 개수 정해주기 ( 지금은 5 )
    public List<PredictedItem> getRandomRecs(List<PredictedItem> recs) {
        Random random = new Random();
        if (recs.size() <= 5) {
            return new ArrayList<>(recs);
        }
        List<PredictedItem> modifiableRecs = new ArrayList<>(recs);

        Collections.shuffle(modifiableRecs, random);

        return new ArrayList<>(modifiableRecs.subList(0, 5));
    }
}
