package ureca.nolmung.implementation.review.dtomapper;

import java.util.List;

import org.springframework.stereotype.Component;

import ureca.nolmung.business.review.dto.response.ReviewLabelResp;
import ureca.nolmung.business.review.dto.response.ReviewResp;
import ureca.nolmung.jpa.review.Review;

@Component
public class ReviewDtoMapper {
    public ReviewResp toReviewResp(Review review, List<ReviewLabelResp> reviewLabel)
    {
        return new ReviewResp(review.getId(),
                review.getPlace().getName(),
                review.getPlace().getAddress(),
                review.getRating(),
                reviewLabel);
    }
}