package ureca.nolmung.implementation.diary.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.jpa.user.User;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class DiaryDtoMapper {
    public DiaryListResp toAddDiaryResp(User user, List<Map<String, Object>> diaryList) {
        List<DiaryListResp.Diary> diaries = diaryList.stream()
                .map(this::mapRowToDiaryListResp)
                .collect(Collectors.toList());

        return new DiaryListResp(
                new DiaryListResp.User(user.getId(),user.getProfileImageUrl(),user.getNickname()),
                diaries
        );
    }

    private DiaryListResp.Diary mapRowToDiaryListResp(Map<String, Object> row) {
        return new DiaryListResp.Diary(
                (Long) row.get("diaryId"),
                (String) row.get("title"),
                (String) row.get("content"),
                (Boolean) row.get("publicYn"),
                (String) row.get("createdAt"),
                (String) row.get("mediaUrl")
        );
    }
}
