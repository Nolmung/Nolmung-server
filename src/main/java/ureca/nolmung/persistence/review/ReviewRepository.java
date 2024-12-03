package ureca.nolmung.persistence.review;

import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.review.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    @Query("SELECT r FROM Review r JOIN FETCH r.place WHERE r.id = :reviewId ")
    Optional<Review> findByIdWithPlace(@Param("reviewId") Long reviewId);

    @Query("SELECT r FROM Review r LEFT JOIN FETCH r.place WHERE r.user.id = :userId ORDER BY r.createdAt DESC")
    Slice<Review> findByUserIdWithPlace(@Param("userId") Long userId, Pageable pageable);

}
