package ureca.nolmung.business.review;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.implementation.review.ReviewManager;
import ureca.nolmung.implementation.review.dtomapper.ReviewDtoMapper;
import ureca.nolmung.jpa.review.Review;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewUseCase{
    private final ReviewManager reviewManager;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public Long addReview(Long userId, AddReviewReq req) {
//        User loginUser = userManager.getLoginUser(userId);
        // 임시
        User loginUser = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found")); // 예외 처리

        return reviewManager.addReview(loginUser, req);
    }

    @Transactional
    public Long deleteReview(Long reviewId) {
        reviewManager.deleteReview(reviewId);
        return reviewId;
    }
}
