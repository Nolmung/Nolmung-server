package ureca.nolmung.jpa.label;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class LabelId implements Serializable {
	private Long labelId;
	private Long placeId;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LabelId)) return false;
		LabelId labelId = (LabelId) o;
		return Objects.equals(labelId, labelId.labelId) &&
			Objects.equals(placeId, labelId.placeId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(labelId, placeId);
	}
}
