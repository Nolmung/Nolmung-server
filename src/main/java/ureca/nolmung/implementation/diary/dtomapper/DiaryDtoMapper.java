package ureca.nolmung.implementation.diary.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.diary.dto.response.DiaryDetailResp;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryDtoMapper {

    public DiaryListResp toAddDiaryResp(User user, List<Diary> diaryList) {
        List<DiaryListResp.Diary> diaries = diaryList.stream()
                .map(this::mapDiaryToDiaryListResp)
                .collect(Collectors.toList());

        return new DiaryListResp(
                new DiaryListResp.User(user.getId(),user.getProfileImageUrl(),user.getNickname()),
                diaries
        );
    }

    private DiaryListResp.Diary mapDiaryToDiaryListResp(Diary diary) {
        List<DiaryListResp.Media> mediaList = diary.getMediaList().stream()
                .map(media -> new DiaryListResp.Media(
                        media.getId(),
                        media.getMediaType(),
                        media.getMediaUrl()
                ))
                .collect(Collectors.toList());
        return new DiaryListResp.Diary(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.isPublicYn(),
                diary.getCreatedAt(),
                mediaList
        );
    }

    public DiaryDetailResp toDiaryDetailResp(Diary diary, List<DogDiary> dogList, List<Place> placeList, List<Media> mediaList) {
        List<DiaryDetailResp.Place> placeDTO = placeList.stream()
                .map(place -> new DiaryDetailResp.Place(
                        place.getId(),
                        place.getName(),
                        place.getRoadAddress(),
                        place.getAddress(),
                        place.getRatingAvg()
                ))
                .collect(Collectors.toList());

        List<DiaryDetailResp.Media> mediaDTO = mediaList.stream()
                .map(media -> new DiaryDetailResp.Media(
                        media.getId(),
                        media.getMediaType(),
                        media.getMediaUrl()
                ))
                .collect(Collectors.toList());

        List<DiaryDetailResp.DogDiary> dogDTO = dogList.stream()
                .map(dog -> new DiaryDetailResp.DogDiary(
                        dog.getDog().getId(),
                        dog.getDog().getName(),
                        dog.getDog().getProfileImageUrl() == null ? "" : dog.getDog().getProfileImageUrl()
                ))
                .collect(Collectors.toList());

        return new DiaryDetailResp(
                diary.getUser().getId(),
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.isPublicYn(),
                diary.getCreatedAt(),
                dogDTO,
                placeDTO,
                mediaDTO
        );
    }
}
