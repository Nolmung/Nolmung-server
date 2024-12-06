package ureca.nolmung.business.user.dto.response;

import java.time.LocalDate;

import ureca.nolmung.jpa.user.Enum.Gender;
import ureca.nolmung.jpa.user.Enum.UserRole;

public record UserResp(Long userId, String userName, String userNickname, String userAddressProvince, LocalDate userBirth, Gender userGender, String userProfileImage, String userEmail, UserRole userRole) { }
