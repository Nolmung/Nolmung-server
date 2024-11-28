package ureca.nolmung.business.diary;

import ureca.nolmung.business.diary.dto.request.AddDiaryReq;

public interface DiaryUseCase {
    Long addDiary(Long userId, AddDiaryReq req);
}
