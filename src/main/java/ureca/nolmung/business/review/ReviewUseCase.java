package ureca.nolmung.business.review;

import ureca.nolmung.business.dummy.dto.request.AddDummyReq;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.jpa.review.Review;

public interface ReviewUseCase {
    Long addReview(Long userId, AddReviewReq req);
    Long deleteReview(Long reviewId);
}
