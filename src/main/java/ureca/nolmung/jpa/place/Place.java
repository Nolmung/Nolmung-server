package ureca.nolmung.jpa.place;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.implementation.place.PlaceException;
import ureca.nolmung.implementation.place.PlaceExceptionType;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.place.Enum.Category;

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
            this.ratingAvg = Math.round((ratingTotal / ratingCount) * 10) / 10.0;
        }
    }

    public void addBookmarkCount() {
        this.bookmarkCount++;
    }

    public void minusBookmarkCount() {
        if (bookmarkCount <= 0) {
            throw new PlaceException(PlaceExceptionType.BOOKMARK_COUNT_CANNOT_BE_NEGATIVE);
        }
        this.bookmarkCount--;
    }

}
