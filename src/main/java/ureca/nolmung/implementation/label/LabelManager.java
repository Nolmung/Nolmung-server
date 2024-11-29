package ureca.nolmung.implementation.label;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.labels.response.LabelResponse;
import ureca.nolmung.jpa.label.Label;
import ureca.nolmung.persistence.label.LabelRepository;

@Component
@RequiredArgsConstructor
public class LabelManager {

	private final LabelRepository labelRepository;

	public List<LabelResponse> findLabelsByPlaceId(long placeId) {
		List<Label> labels = labelRepository.findAllByPlaceId(placeId);
		return labels.stream()
			.map(LabelResponse::of)
			.collect(Collectors.toList());
	}

}
