package ureca.nolmung.implementation.recommend.dtomapper;

import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.personalizeruntime.model.PredictedItem;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.jpa.place.Place;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecommendDtoMapper {


    public List<RecommendResp> toGetPlaceRecommendationsFromPersonalize(List<Place> places) {
        return places.stream()
                .map(place -> new RecommendResp(
                        place.getId(),
                        place.getName(),
                        place.getCategory(),
                        place.getRoadAddress(),
                        place.getPlaceImageUrl(),
                        place.getRatingAvg(),
                        place.getRatingCount()
                ))
                .collect(Collectors.toList());
    }
}
