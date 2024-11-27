package ureca.nolmung.implementation.diary;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.diary.response.PlaceDiaryResponse;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.persistence.diary.DiaryRepository;
import ureca.nolmung.persistence.diaryplace.DiaryPlaceRepository;
import ureca.nolmung.persistence.media.MediaRepository;

@Component
@RequiredArgsConstructor
public class DiaryManager {

	private final DiaryPlaceRepository diaryPlaceRepository;
	private final MediaRepository mediaRepository;
	private final DiaryRepository diaryRepository;

	public List<PlaceDiaryResponse> findDiaryByPlace(Place place) {
		List<DiaryPlace> diaryPlaces = diaryPlaceRepository.findAllByPlaceOOrderByCreatedAtDesc(place);
		List<PlaceDiaryResponse> placeDiaryResponses = new ArrayList<>();
		for (DiaryPlace diaryPlace : diaryPlaces) {
			Diary diary = diaryRepository.findById(diaryPlace.getId())
				.orElseThrow(() -> new DiaryException(DiaryExceptionType.DIARY_NOT_FOUND_EXCEPTION));
			Media media = mediaRepository.findFirstByDiary(diary);
			placeDiaryResponses.add(new PlaceDiaryResponse().of(diary, media.getMediaUrl()));
		}
		return placeDiaryResponses;
	}

}
