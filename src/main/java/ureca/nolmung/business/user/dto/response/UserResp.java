package ureca.nolmung.business.user.dto.response;

import ureca.nolmung.jpa.user.Enum.Gender;

public record UserResp(Long userId, String userName, String userNickname, String userAddressProvince, Long userAge, Gender userGender) { }
