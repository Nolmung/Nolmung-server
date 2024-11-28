package ureca.nolmung.business.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.exception.user.UserException;
import ureca.nolmung.exception.user.UserExceptionType;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.implementation.diary.dtomapper.DiaryDtoMapper;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DiaryService implements DiaryUseCase {
    private final DiaryManager diaryManager;
    private final UserRepository userRepository;
    private final DiaryDtoMapper diaryDtoMapper;

    @Override
    @Transactional
    public Long addDiary(Long userId, AddDiaryReq req) {
        // 임시
        User loginUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));

        return diaryManager.addDiary(loginUser, req);
    }

    @Override
    public DiaryListResp getAllDiaries(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
        List<Map<String, Object>> diaryList = diaryManager.getDiaryList(userId);

        return diaryDtoMapper.toAddDiaryResp(user, diaryList);
    }
}
