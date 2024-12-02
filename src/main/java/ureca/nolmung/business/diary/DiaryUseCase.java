package ureca.nolmung.business.diary;

import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.request.UpdateDiaryReq;
import ureca.nolmung.business.diary.dto.response.*;
import ureca.nolmung.jpa.user.User;

public interface DiaryUseCase {
    AddDiaryResp addDiary(User user, AddDiaryReq req);
    DiaryListResp getAllDiaries(User user);
    DiaryDetailResp getDetailDiary(Long diaryId);
    DiaryDetailResp getDetailMyDiary(User user, Long diaryId);
    DeleteDiaryResp deleteDiary(User user, Long diaryId);
    UpdateDiaryResp updateDiary(User user, Long diaryId, UpdateDiaryReq req);
}
