package ureca.nolmung.implementation.diaryplace;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.diaryplace.DiaryPlaceRepository;
import ureca.nolmung.persistence.place.PlaceRepository;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DiaryPlaceManager {

    private final DiaryPlaceRepository diaryPlaceRepository;
    private final PlaceRepository placeRepository;

    public List<Place> getPlaceList(Long diaryId) {
        List<Long> placeIds = diaryPlaceRepository.findPlaceIdsByDiaryId(diaryId);
        List<Place> placeList = new ArrayList<>();
        for (Long placeId : placeIds) {
            placeList.add(placeRepository.findById(placeId)
                    .orElseThrow(() -> new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION)));
        }
        return placeList;
    }
}
