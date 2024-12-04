package ureca.nolmung.business.user.dto.response;

import ureca.nolmung.jpa.user.Enum.Gender;
import ureca.nolmung.jpa.user.Enum.UserRole;

public record UserResp(Long userId, String userName, String userNickname, String userAddressProvince, Long userAge, Gender userGender, String userProfileImage, String userEmail, UserRole userRole) { }
