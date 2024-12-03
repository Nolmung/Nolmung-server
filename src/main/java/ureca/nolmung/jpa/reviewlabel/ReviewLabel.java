package ureca.nolmung.jpa.reviewlabel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.review.Review;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ReviewLabel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_label_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    @Column(nullable = false)
    private Long labelId;

    @Column(length = 30, nullable = false)
    private String labelName;
}
