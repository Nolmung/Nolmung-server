package ureca.nolmung.implementation.review.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.review.dto.request.AddReviewReq;
import ureca.nolmung.business.review.dto.response.AddReviewResp;
import ureca.nolmung.jpa.review.Review;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ReviewDtoMapper {
    public AddReviewResp toAddReviewResp(Review newReview) {
        return new AddReviewResp( "성공적으로 " + newReview.getId() + "번 후기가 등록되었습니다.");
    }
}