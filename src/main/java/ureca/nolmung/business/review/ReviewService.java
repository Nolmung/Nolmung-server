package ureca.nolmung.business.review;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.business.review.dto.response.DeleteReviewResp;
import ureca.nolmung.business.review.dto.response.LabelResp;
import ureca.nolmung.business.review.dto.response.ReviewResp;
import ureca.nolmung.implementation.review.ReviewManager;
import ureca.nolmung.implementation.review.dtomapper.ReviewDtoMapper;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.review.Review;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class ReviewService implements ReviewUseCase{
    private final ReviewManager reviewManager;
    private final UserManager userManager;
    private final ReviewDtoMapper reviewDtoMapper;

    @Override
    @Transactional
    public AddReviewResp addReview(User user, AddReviewReq req) {
        User loginUser = userManager.validateUserExistence(user.getId());
        return reviewManager.addReview(loginUser, req);
    }

    @Transactional
    public DeleteReviewResp deleteReview(User user, Long reviewId) {
        reviewManager.checkReviewWriter(user.getId(), reviewId);
        return reviewManager.deleteReview(reviewId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReviewResp> getReviews(Long userId, int page, int size) {

        List<Review> reviews = reviewManager.getReviews(userId, page, size).getContent();

        return reviews.stream()
                .map(review -> {
                    List<LabelResp> labelList = review.getReviewLabels().stream()
                            .map(label -> new LabelResp(label.getLabelId(), label.getLabelName())) // ReviewLabelResp 객체로 변환
                            .collect(Collectors.toList());
                    return reviewDtoMapper.toReviewResp(review, labelList);
                })
                .collect(Collectors.toList());
    }
}
