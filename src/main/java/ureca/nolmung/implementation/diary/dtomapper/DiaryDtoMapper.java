package ureca.nolmung.implementation.diary.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.diary.dto.response.DiaryDetailResp;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.diaryplace.DiaryPlace;
import ureca.nolmung.jpa.dogdiary.DogDiary;
import ureca.nolmung.jpa.media.Media;
import ureca.nolmung.jpa.place.Place;
import ureca.nolmung.jpa.user.User;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DiaryDtoMapper {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy.MM.dd");

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
        String formattedDate = diary.getCreatedAt().format(DATE_FORMATTER);
        String mediaUrl = diary.getMediaList().isEmpty() ? "" : diary.getMediaList().get(0).getMediaUrl();

        return new DiaryListResp.Diary(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.isPublicYn(),
                formattedDate,
                mediaUrl
        );
    }

    public DiaryDetailResp toDiaryDetailResp(Diary diary, List<DogDiary> dogList, List<Place> placeList, List<Media> mediaList) {
        String formattedDate = diary.getCreatedAt().format(DATE_FORMATTER);
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
                        dog.getDog().getProfileImageUrl() == null ? "" : dog.getDog().getProfileImageUrl()
                ))
                .collect(Collectors.toList());

        return new DiaryDetailResp(
                diary.getId(),
                diary.getTitle(),
                diary.getContent(),
                diary.isPublicYn(),
                formattedDate,
                dogDTO,
                placeDTO,
                mediaDTO
        );
    }
}
