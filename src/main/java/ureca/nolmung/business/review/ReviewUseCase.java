package ureca.nolmung.business.review;

import ureca.nolmung.business.dummy.dto.request.AddDummyReq;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.business.review.dto.response.DeleteReviewResp;
import ureca.nolmung.jpa.review.Review;
import ureca.nolmung.jpa.user.User;

public interface ReviewUseCase {
    AddReviewResp addReview(User user, AddReviewReq req);
    DeleteReviewResp deleteReview(User user, Long reviewId);
}
