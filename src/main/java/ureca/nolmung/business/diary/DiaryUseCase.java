package ureca.nolmung.business.diary;

import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.request.UpdateDiaryReq;
import ureca.nolmung.business.diary.dto.response.*;

public interface DiaryUseCase {
    AddDiaryResp addDiary(Long userId, AddDiaryReq req);
    DiaryListResp getAllDiaries(Long userId);
    DiaryDetailResp getDetailDiary(Long diaryId);
    DeleteDiaryResp deleteDiary(Long diaryId);
    UpdateDiaryResp updateDiary(Long diaryId, UpdateDiaryReq req);
}
