package ureca.nolmung.business.review;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.business.review.dto.response.DeleteReviewResp;
import ureca.nolmung.implementation.review.ReviewManager;
import ureca.nolmung.implementation.review.dtomapper.ReviewDtoMapper;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.review.Review;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewUseCase{
    private final ReviewManager reviewManager;
    private final UserManager userManager;

    @Override
    @Transactional
    public AddReviewResp addReview(User user, AddReviewReq req) {
        User loginuser = userManager.validateUserExistence(user.getId());
        return reviewManager.addReview(loginuser, req);
    }

    @Transactional
    public DeleteReviewResp deleteReview(User user, Long reviewId) {
        reviewManager.checkReviewWriter(user.getId(), reviewId);
        return reviewManager.deleteReview(reviewId);
    }
}
