package ureca.nolmung.presentation.controller.diary;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.diary.DiaryUseCase;
import ureca.nolmung.business.diary.dto.request.AddDiaryReq;
import ureca.nolmung.business.diary.dto.request.UpdateDiaryReq;
import ureca.nolmung.business.diary.dto.response.*;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@Tag(name = "일기")
@RestController
@RequestMapping("/api/v1/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryUseCase diaryUseCase;

    @Operation(summary = "일기 등록")
    @PostMapping("")
    public ResponseDto<AddDiaryResp> addDiary(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody AddDiaryReq req) {
        return ResponseUtil.SUCCESS("일기 생성에 성공하였습니다.", diaryUseCase.addDiary(userDetails.getUser(), req));
    }

    @Operation(summary = "일기 목록 조회")
    @GetMapping("")
    public ResponseDto<DiaryListResp> getAllDiaries(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseUtil.SUCCESS("일기 조회에 성공하였습니다.", diaryUseCase.getAllDiaries(userDetails.getUser()));
    }

    @Operation(summary = "일기 상세 조회(모든 유저용)")
    @GetMapping("/public/{diaryId}")
    public ResponseDto<DiaryDetailResp> getDetailDiary(@PathVariable Long diaryId) {
        return ResponseUtil.SUCCESS("일기 상세조회에 성공하였습니다.", diaryUseCase.getDetailDiary(diaryId));
    }

    @Operation(summary = "일기 상세 조회(개인)")
    @GetMapping("/private/{diaryId}")
    public ResponseDto<DiaryDetailResp> getDetailMyDiary(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long diaryId) {
        return ResponseUtil.SUCCESS("일기 상세조회에 성공하였습니다.", diaryUseCase.getDetailMyDiary(userDetails.getUser(), diaryId));
    }

    @Operation(summary = "일기 삭제")
    @DeleteMapping("/{diaryId}")
    public ResponseDto<DeleteDiaryResp> deleteDiary(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long diaryId) {
        return ResponseUtil.SUCCESS("일기 삭제에 성공하였습니다.", diaryUseCase.deleteDiary(userDetails.getUser(), diaryId));
    }

    @Operation(summary = "일기 수정")
    @PatchMapping("/{diaryId}")
    public ResponseDto<UpdateDiaryResp> updateDiary(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long diaryId, @RequestBody UpdateDiaryReq req) {
        return ResponseUtil.SUCCESS("일기 수정에 성공하였습니다.", diaryUseCase.updateDiary(userDetails.getUser(), diaryId, req));
    }
}
