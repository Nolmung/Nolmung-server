package ureca.nolmung.business.user.dto.request;

import ureca.nolmung.jpa.user.Enum.Gender;

public record UserReq (String userNickname, String userAddressProvince, Long userAge, Gender userGender) { }
