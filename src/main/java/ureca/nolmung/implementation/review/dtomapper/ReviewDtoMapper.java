package ureca.nolmung.implementation.review.dtomapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import ureca.nolmung.business.review.dto.response.LabelResp;
import ureca.nolmung.business.review.dto.response.ReviewResp;
import ureca.nolmung.jpa.review.Review;

@Component
public class ReviewDtoMapper {
    public ReviewResp toReviewResp(Review review, List<LabelResp> labels)
    {
        return new ReviewResp(review.getId(),
                review.getPlace().getId(),
                review.getPlace().getName(),
                review.getPlace().getAddress(),
                review.getRating(),
                labels);
    }

    public ReviewResp toReviewResp(Review review) {
        List<LabelResp> labelList = review.getReviewLabels().stream()
                .map(label -> new LabelResp(label.getLabelId(), label.getLabelName()))
                .collect(Collectors.toList());

        return toReviewResp(review, labelList);
    }

}