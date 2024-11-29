package ureca.nolmung.jpa.placeposition;

import org.locationtech.jts.geom.Point;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class PlacePosition {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "place_position_id")
	private Long id;

	@Column(columnDefinition = "GEOMETRY", nullable = false)
	private Point location;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "place_id", nullable = false)
	private Place place;

}
