package ureca.nolmung.presentation.controller.diary;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.diary.DiaryUseCase;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.response.AddDiaryResp;
import ureca.nolmung.business.diary.dto.response.DiaryDetailResp;
import ureca.nolmung.business.diary.dto.response.DiaryListResp;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryUseCase diaryUseCase;

    @PostMapping("")
    public ResponseDto<AddDiaryResp> addDiary(@RequestBody AddDiaryReq req) {
        return ResponseUtil.SUCCESS("일기 생성에 성공하였습니다.", diaryUseCase.addDiary(req.userId(), req));
    }

    @GetMapping("")
    public ResponseDto<DiaryListResp> getAllDiaries(@RequestParam Long userId) {
        return ResponseUtil.SUCCESS("일기 조회에 성공하였습니다.", diaryUseCase.getAllDiaries(userId));
    }

    @GetMapping("/{diaryId}")
    public ResponseDto<DiaryDetailResp> getDetailDiary(@PathVariable Long diaryId) {
        return ResponseUtil.SUCCESS("일기 상세조회에 성공하였습니다.", diaryUseCase.getDetailDiary(diaryId));
    }
}
