package ureca.nolmung.presentation.controller.review;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.review.ReviewUseCase;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewUseCase reviewUseCase;

    /**
     * 24.11.22 작성자 : 배혜원
     * 후기 등록
     * */
    @PostMapping("")
    public ResponseDto<Long> addReview(Long userId, @RequestBody AddReviewReq req) {
        Long createReviewId = reviewUseCase.addReview(userId, req);
        return ResponseUtil.SUCCESS("리뷰 생성에 성공하였습니다.",createReviewId);
    }

    /**
     * 24.11.22 작성자 : 배혜원
     * 후기 삭제
     * */
    @DeleteMapping("/{reviewId}")
    public ResponseDto<Long> deleteReview(@PathVariable Long reviewId) {
        Long deleteReviewId = reviewUseCase.deleteReview(reviewId);
        return ResponseUtil.SUCCESS("리뷰 삭제에 성공하였습니다.", deleteReviewId);
    }
}
