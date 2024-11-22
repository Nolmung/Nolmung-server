package ureca.nolmung.jpa.badge;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.badgeCode.BadgeCode;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.user.User;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Badge extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "badge_code_id")
    private BadgeCode badgeCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
}
