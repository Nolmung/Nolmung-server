package ureca.nolmung.jpa.reviewLabel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.badgeCode.BadgeCode;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.label.Label;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "label_id")
    private Label label;

}
