package ureca.nolmung.jpa.badgeCode;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BadgeCode extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "badge_code_id")
    private Long id;

    @Column(length = 20, nullable = false)
    private String badgeName;

    @Column(length = 100, nullable = false)
    private String content;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String badgeImageUrl;
}
