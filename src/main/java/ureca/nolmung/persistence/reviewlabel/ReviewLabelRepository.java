package ureca.nolmung.persistence.reviewlabel;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.reviewlabel.ReviewLabel;

import java.util.List;

@Repository
public interface ReviewLabelRepository extends JpaRepository<ReviewLabel, Long> {
	List<ReviewLabel> findByReviewId(Long reviewId);
}