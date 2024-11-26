package ureca.nolmung.jpa.label;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.config.BaseEntity;
import ureca.nolmung.jpa.place.Place;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Label extends BaseEntity {

    @EmbeddedId
    private LabelId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("placeId")
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private Integer labelCount;

    /**
     * 리뷰 등록 시, 장소별 라벨 카운트 증가
     * */
    public void addLabelCount() {
        this.labelCount++;
    }

    /**
     * 리뷰 삭제 시, 장소별 라벨 카운트 감소
     * */
    public void removeLabelCount() {
        if (this.labelCount > 0) {
            this.labelCount--;
        }
    }
}