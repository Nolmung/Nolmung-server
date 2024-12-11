package ureca.nolmung.implementation.diary;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.request.UpdateDiaryReq;
import ureca.nolmung.business.diary.dto.response.AddDiaryResp;
import ureca.nolmung.business.diary.dto.response.DeleteDiaryResp;
import ureca.nolmung.business.diary.dto.response.UpdateDiaryResp;
import ureca.nolmung.business.diary.response.PlaceDiaryResponse;
import ureca.nolmung.implementation.dog.DogException;
import ureca.nolmung.implementation.dog.DogExceptionType;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.banword.trie.Trie;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.jpa.media.Enum.MediaType;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.diary.DiaryRepository;
import ureca.nolmung.persistence.diaryplace.DiaryPlaceRepository;
import ureca.nolmung.persistence.dog.DogRepository;
import ureca.nolmung.persistence.dogdiary.DogDiaryRepository;
import ureca.nolmung.persistence.media.MediaRepository;
import ureca.nolmung.persistence.place.PlaceRepository;

@Component
@RequiredArgsConstructor
public class DiaryManager {

	private final DiaryPlaceRepository diaryPlaceRepository;
	private final MediaRepository mediaRepository;
	private final DiaryRepository diaryRepository;
	private final PlaceRepository placeRepository;
	private final DogDiaryRepository dogDiaryRepository;
	private final DogRepository dogRepository;
	private final Trie trie;

	public List<PlaceDiaryResponse> findDiaryByPlace(Place place) {
		List<DiaryPlace> diaryPlaces = diaryPlaceRepository.findAllByPlaceOrderByCreatedAtDesc(place);
		List<PlaceDiaryResponse> placeDiaryResponses = new ArrayList<>();

		for (DiaryPlace diaryPlace : diaryPlaces) {
			Diary diary = findDiaryById(diaryPlace.getDiary().getId());

			if (!isDiaryPublic(diary)) {
				continue;
			}

			String mediaUrl = getMediaUrl(diary);
			placeDiaryResponses.add(new PlaceDiaryResponse().of(diary, mediaUrl));
		}
		return placeDiaryResponses;
	}

	public Diary findDiaryById(Long diaryId) {
		return diaryRepository.findById(diaryId)
			.orElseThrow(() -> new DiaryException(DiaryExceptionType.DIARY_NOT_FOUND_EXCEPTION));
	}

	private boolean isDiaryPublic(Diary diary) {
		return diary.isPublicYn();
	}

	private String getMediaUrl(Diary diary) {
		Media media = mediaRepository.findFirstByDiary(diary);
		return (media != null) ? media.getMediaUrl() : "이미지 없음";
	}

	public AddDiaryResp addDiary(User user, AddDiaryReq req) {

		Diary diary = createDiary(user, req);
		Diary savedDiary = diaryRepository.save(diary);

		req.places().forEach(placeId -> {
			Place place = placeRepository.findById(placeId)
				.orElseThrow(() -> new PlaceException(PlaceExceptionType.PLACE_NOT_FOUND_EXCEPTION));

			DiaryPlace diaryPlace = createDiaryPlace(savedDiary, place);
			diaryPlaceRepository.save(diaryPlace);
		});

		req.dogs().forEach(dogId -> {
			Dog dog = dogRepository.findById(dogId)
				.orElseThrow(() -> new DogException(DogExceptionType.DOG_NOT_FOUND_EXCEPTION));

			DogDiary dogDiary = createDogDiary(savedDiary, dog);
			dogDiaryRepository.save(dogDiary);
		});

		req.medias().forEach(mediaReq -> {
			Media media = createMedia(mediaReq);

			media.addDiary(savedDiary);
			mediaRepository.save(media);
		});

		return new AddDiaryResp(savedDiary.getId());
	}

	public List<Diary> getDiaryList(Long userId) {
		return diaryRepository.findDiariesWithFirstMediaByUser(userId);
	}

	public Diary getDetailDiary(Long diaryId) {
		return diaryRepository.findWithMediaById(diaryId);
	}

	public DeleteDiaryResp deleteDiary(Diary diary) {
		diaryRepository.delete(diary);
		return new DeleteDiaryResp(diary.getId());
	}

	public UpdateDiaryResp updateDiary(Diary diary, UpdateDiaryReq req) {
		List<Long> reqDogIds = req.dogs();
		List<Long> existingDogIds = diary.getDogDiaries().stream()
			.map(dogDiary -> dogDiary.getDog().getId())
			.toList();

		for (Long reqDogId : reqDogIds) {
			if (!existingDogIds.contains(reqDogId)) {
				Dog dog = dogRepository.findById(reqDogId)
					.orElseThrow(() -> new DogException(DogExceptionType.DOG_NOT_FOUND_EXCEPTION));
				DogDiary newDogDiary = createDogDiary(diary, dog);
				diary.addDogDiary(newDogDiary);
			}
		}
		diary.getDogDiaries().removeIf(dogDiary -> {
			if (!reqDogIds.contains(dogDiary.getDog().getId())) {
				return true;
			}
			return false;
		});

		mediaRepository.deleteByDiaryId(diary.getId());
		req.medias().forEach(mediaReq -> {
			Media newMedia = updateMedia(mediaReq);
			newMedia.addDiary(diary);
			mediaRepository.save(newMedia);
		});

		diary.updateDiary(req.title(), req.content(), req.publicYn());

		return new UpdateDiaryResp(diary.getId());
	}

	private static Media updateMedia(UpdateDiaryReq.MediaDto mediaReq) {
		return Media.builder()
			.mediaType(MediaType.valueOf(mediaReq.mediaType()))
			.mediaUrl(mediaReq.mediaUrl())
			.build();
	}

	private static Media createMedia(AddDiaryReq.MediaDto mediaReq) {
		return Media.builder()
			.mediaType(MediaType.valueOf(mediaReq.mediaType()))
			.mediaUrl(mediaReq.mediaUrl())
			.build();
	}

	private static DogDiary createDogDiary(Diary savedDiary, Dog dog) {
		return DogDiary.builder()
			.diary(savedDiary)
			.dog(dog)
			.build();
	}

	private static DiaryPlace createDiaryPlace(Diary savedDiary, Place place) {
		return DiaryPlace.builder()
			.diary(savedDiary)
			.place(place)
			.build();
	}

	private Diary createDiary(User loginUser, AddDiaryReq req) {
		if (trie.search(req.title())) {
			throw new DiaryException(DiaryExceptionType.DIARY_TITLE_CONTAINS_BAN_WORD);
		}
		if (trie.search(req.content())) {
			throw new DiaryException(DiaryExceptionType.DIARY_CONTENT_CONTAINS_BAN_WORD);
		}

		return ureca.nolmung.jpa.diary.Diary.builder()
			.user(loginUser)
			.title(req.title())
			.content(req.content())
			.publicYn(req.publicYn())
			.build();
	}

	public Diary checkExistDiary(Long diaryId) {
		return diaryRepository.findById(diaryId)
			.orElseThrow(() -> new DiaryException(DiaryExceptionType.DIARY_NOT_FOUND_EXCEPTION));
	}

	public Diary checkDiaryWriter(Long userId, Long diaryId) {
		Diary diary = diaryRepository.findById(diaryId)
			.orElseThrow(() -> new DiaryException(DiaryExceptionType.DIARY_NOT_FOUND_EXCEPTION));
		if (!diary.getUser().getId().equals(userId)) {
			throw new DiaryException(DiaryExceptionType.DIARY_UNAUTHORIZED_EXCEPTION);
		}
		return diary;
	}

	public void checkTodayDiary(Long userId, LocalDate date) {
		boolean hasTodayDiary = diaryRepository.existsByUserIdAndCreatedAt(userId, date);

		if(hasTodayDiary) {
			throw new DiaryException(DiaryExceptionType.DIARY_EXITST_TODAY_EXCEPTION);
		}
	}
}
