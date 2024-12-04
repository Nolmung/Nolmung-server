package ureca.nolmung.implementation.user.dtomapper;

import org.springframework.stereotype.Component;
import ureca.nolmung.business.user.dto.response.UserResp;
import ureca.nolmung.jpa.user.User;

@Component
public class UserDtoMapper {

    public UserResp toUserResp(User user) {
        return new UserResp(
                user.getId(),
                user.getName(),
                user.getNickname(),
                user.getAddressProvince(),
                user.getAge(),
                user.getGender(),
                user.getProfileImageUrl(),
                user.getEmail(),
                user.getRole()
            );
    }
}
