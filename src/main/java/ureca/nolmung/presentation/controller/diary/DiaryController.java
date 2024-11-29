package ureca.nolmung.presentation.controller.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.diary.DiaryUseCase;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryUseCase diaryUseCase;

    @PostMapping("")
    public ResponseDto<Long> addDiary(@RequestBody AddDiaryReq req) {
        Long createDiaryId = diaryUseCase.addDiary(req.userId(), req);
        return ResponseUtil.SUCCESS("일기 생성에 성공하였습니다.", createDiaryId);
    }
}
