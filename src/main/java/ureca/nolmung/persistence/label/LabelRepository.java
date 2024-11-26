package ureca.nolmung.persistence.label;

import org.springframework.data.jpa.repository.JpaRepository;
import ureca.nolmung.jpa.label.Label;
import ureca.nolmung.jpa.label.LabelId;

import java.util.Optional;

public interface LabelRepository extends JpaRepository<Label, Long> {
    Optional<Label> findById_LabelIdAndId_PlaceId(Long labelId, Long placeId);

}
