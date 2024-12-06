package ureca.nolmung.business.review;

import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.business.review.dto.response.DeleteReviewResp;
import ureca.nolmung.business.review.dto.response.ReviewResp;
import ureca.nolmung.jpa.user.User;

import java.util.List;

public interface ReviewUseCase {
    AddReviewResp addReview(User user, AddReviewReq req);
    DeleteReviewResp deleteReview(User user, Long reviewId);
    List<ReviewResp> getReviews(Long userId, int page, int size);
}
