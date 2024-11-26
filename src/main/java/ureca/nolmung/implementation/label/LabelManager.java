package ureca.nolmung.implementation.label;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.jpa.label.Label;
import ureca.nolmung.persistence.label.LabelRepository;

@Component
@RequiredArgsConstructor
public class LabelManager {

	private final LabelRepository labelRepository;

	public List<Label> findLabelsByPlaceId(long placeId) {
		return labelRepository.findAllByPlaceId(placeId);
	}

}
