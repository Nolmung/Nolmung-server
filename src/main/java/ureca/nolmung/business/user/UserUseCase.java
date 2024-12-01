package ureca.nolmung.business.user;

import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.business.user.dto.response.UserResp;

public interface UserUseCase {
    UserResp updateUser(Long userId, UserReq req);

    UserResp getUser(Long userId);
}
