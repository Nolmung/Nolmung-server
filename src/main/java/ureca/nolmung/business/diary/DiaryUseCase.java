package ureca.nolmung.business.diary;

import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;

public interface DiaryUseCase {
    Long addDiary(Long userId, AddDiaryReq req);
    DiaryListResp getAllDiaries(Long userId);
}
