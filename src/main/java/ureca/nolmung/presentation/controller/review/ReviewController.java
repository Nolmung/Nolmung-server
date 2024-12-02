package ureca.nolmung.presentation.controller.review;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.review.ReviewUseCase;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.business.review.dto.response.DeleteReviewResp;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@Tag(name = "후기")
@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewUseCase reviewUseCase;

    @Operation(summary = "후기 등록")
    @PostMapping("")
    public ResponseDto<AddReviewResp> addReview(@AuthenticationPrincipal CustomUserDetails userDetails, @RequestBody AddReviewReq req) {
        return ResponseUtil.SUCCESS("후기 생성에 성공하였습니다.",reviewUseCase.addReview(userDetails.getUser(), req));
    }

    @Operation(summary = "후기 삭제")
    @DeleteMapping("/{reviewId}")
    public ResponseDto<DeleteReviewResp> deleteReview(@AuthenticationPrincipal CustomUserDetails userDetails, @PathVariable Long reviewId) {
        return ResponseUtil.SUCCESS("후기 삭제에 성공하였습니다.", reviewUseCase.deleteReview(userDetails.getUser(), reviewId));
    }
}
