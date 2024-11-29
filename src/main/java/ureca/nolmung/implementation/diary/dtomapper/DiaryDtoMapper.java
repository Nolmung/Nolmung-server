package ureca.nolmung.implementation.diary.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.user.User;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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
}
