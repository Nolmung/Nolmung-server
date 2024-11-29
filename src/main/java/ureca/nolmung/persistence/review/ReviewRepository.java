package ureca.nolmung.persistence.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ureca.nolmung.jpa.review.Review;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r JOIN FETCH r.place WHERE r.id = :reviewId ")
    Optional<Review> findByIdWithPlace(@Param("reviewId") Long reviewId);
}
