package ureca.nolmung.persistence.label;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.label.Label;

public interface LabelRepository extends JpaRepository<Label, Long> {

	List<Label> findAllByPlaceId(Long placeId);
	Optional<Label> findById_LabelIdAndId_PlaceId(Long labelId, Long placeId);

}
