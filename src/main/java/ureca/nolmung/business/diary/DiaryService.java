package ureca.nolmung.business.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.implementation.diary.DiaryManager;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.diary.DiaryRepository;
import ureca.nolmung.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class DiaryService implements DiaryUseCase {
    private final DiaryManager diaryManager;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long addDiary(Long userId, AddDiaryReq req) {
        // 임시
        User loginUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); // 예외 처리

        return diaryManager.addDiary(loginUser, req);
    }
}
