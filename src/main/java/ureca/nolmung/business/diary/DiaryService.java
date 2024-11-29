package ureca.nolmung.business.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.response.AddDiaryResp;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.exception.user.UserException;
import ureca.nolmung.exception.user.UserExceptionType;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.implementation.diary.dtomapper.DiaryDtoMapper;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.diary.Diary;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiaryService implements DiaryUseCase {
    private final DiaryManager diaryManager;
    private final UserManager userManager;
    private final DiaryDtoMapper diaryDtoMapper;

    @Override
    @Transactional
    public AddDiaryResp addDiary(Long userId, AddDiaryReq req) {
        User user = userManager.validateUserExistence(userId);

        return diaryManager.addDiary(user, req);
    }

    @Override
    public DiaryListResp getAllDiaries(Long userId) {
        User user = userManager.validateUserExistence(userId);
        List<Diary> diaryList = diaryManager.getDiaryList(userId);

        return diaryDtoMapper.toAddDiaryResp(user, diaryList);
    }
}
