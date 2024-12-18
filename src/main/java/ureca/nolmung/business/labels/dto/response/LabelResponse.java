package ureca.nolmung.business.labels.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.label.Label;

@Getter
@NoArgsConstructor
public class LabelResponse {

	private Long labelId;
	private Integer labelCount;

	@Builder
	public LabelResponse(Long labelId, Integer labelCount) {
		this.labelId = labelId;
		this.labelCount = labelCount;
	}

	public static LabelResponse of (Label label) {
		return LabelResponse.builder()
			.labelId(label.getId().getLabelId())
			.labelCount(label.getLabelCount())
			.build();
	}
}
