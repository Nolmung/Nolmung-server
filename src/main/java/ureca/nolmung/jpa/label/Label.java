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
import ureca.nolmung.jpa.place.Place;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Label {

    @EmbeddedId
    private LabelId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("placeId")
    @JoinColumn(name = "place_id", nullable = false)
    private Place place;

    @Column(nullable = false)
    private Integer labelCount;

    public void addLabelCount() {
        this.labelCount++;
    }

    public void removeLabelCount() {
        if (this.labelCount > 0) {
            this.labelCount--;
        }
    }

}
