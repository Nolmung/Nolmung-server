package ureca.nolmung.persistence.label;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ureca.nolmung.jpa.label.Label;

@Repository
public interface LabelRepository extends JpaRepository<Label, Long> {

	List<Label> findAllByPlaceId(Long placeId);

}
