package ureca.nolmung.jpa.place;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.jpa.placeposition.PlacePosition;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Column(columnDefinition = "TEXT")
    private String placeImageUrl;

    @Column
    private String roadAddress;

    @Column(nullable = false)
    private String address;

    @Column(length = 15)
    private String phone;

    @Column(columnDefinition = "TEXT")
    private String homepageUrl;

    @Column(length = 25)
    private String holiday;

    @Column(length = 20)
    private String openHour;

    @Column(nullable = false)
    private boolean parkingYn;

    @Column(length = 30, nullable = false)
    private String price;

    @Column(nullable = false)
    private String acceptSize;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rule;

    @Column(name = "inpossible_yn", nullable = false)
    private boolean inPossibleYn;

    @Column(name = "outpossible_yn", nullable = false)
    private boolean outPossibleYn;

    @Column(nullable = false)
    private String extraPrice;

    @Builder.Default
    @Column(nullable = false)
    private Double ratingTotal = 0.0;

    @Builder.Default
    @Column(nullable = false)
    private Integer ratingCount = 0;

    @Builder.Default
    @Column(nullable = false)
    private Double ratingAvg = 0.0;

    @Column(name = "mapx")
    private double latitude;

    @Column(name = "mapy")
    private double longitude;

    @Builder.Default
    @Column(nullable = false)
    private Integer bookmarkCount = 0;

    @OneToOne(mappedBy = "place", cascade = CascadeType.ALL)
    private PlacePosition placePosition;

    public void addRating(double newRating) {
        this.ratingTotal += newRating;
        this.ratingCount++;
        this.calculateRating();
    }

    public void removeRating(double oldRating) {
        this.ratingTotal -= oldRating;
        this.ratingCount = Math.max(0, this.ratingCount - 1);
        this.calculateRating();
    }

    public void calculateRating() {
        if (ratingCount == 0) {
            this.ratingAvg = 0.0;
        } else {
            this.ratingAvg = ratingTotal / ratingCount;
        }
    }

    public void addBookmarkCount() {
        this.bookmarkCount++;
    }

}
