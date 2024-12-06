package ureca.nolmung.business.user.dto.request;

import java.time.LocalDate;

import ureca.nolmung.jpa.user.Enum.Gender;

public record UserReq (String userNickname, String userAddressProvince, double userLat, double userLong, LocalDate userBirth, Gender userGender) { }
