package ureca.nolmung.presentation.controller.review;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.review.ReviewUseCase;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.business.review.dto.response.DeleteReviewResp;
import ureca.nolmung.business.review.dto.response.ReviewResp;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@Tag(name = "후기")
@RestController
@RequestMapping("/v1/reviews")
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

    @Operation(summary = "내가 쓴 후기 목록 조회")
    @GetMapping("")
    public ResponseDto<List<ReviewResp>> getReviews(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                    @RequestParam(defaultValue = "0") int page,
                                                    @RequestParam(defaultValue = "5") int size)
    {
        return ResponseUtil.SUCCESS("후기 조회에 성공하였습니다.", reviewUseCase.getReviews(userDetails.getUser().getId(), page, size));
    }
}
