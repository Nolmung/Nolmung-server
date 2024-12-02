package ureca.nolmung.business.user;

import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.business.user.dto.response.UserResp;
import ureca.nolmung.jpa.user.User;

public interface UserUseCase {
    UserResp updateUser(User user, UserReq req);

    UserResp getUser(User user);
}
