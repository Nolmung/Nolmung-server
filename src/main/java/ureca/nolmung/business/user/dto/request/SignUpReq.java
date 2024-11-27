package ureca.nolmung.business.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ureca.nolmung.jpa.user.Enum.Gender;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class SignUpReq {
	private String nickname;
	private String addressProvince;
	private String addressDistrict;
	private Long age;
	private Gender gender;
}
