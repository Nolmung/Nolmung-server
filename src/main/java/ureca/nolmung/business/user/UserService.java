package ureca.nolmung.business.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.business.user.dto.response.UserResp;
import ureca.nolmung.config.jwt.JWTUtil;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.implementation.user.dtomapper.UserDtoMapper;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase{

	private final JWTUtil jwtUtil;
	private final UserManager userManager;
	private final UserDtoMapper userDtoMapper;

	@Transactional
	public UserResp completeSignUp(Long userId, UserReq req) {

		User user = userManager.validateUserExistence(userId);
		user.singUp(req);
		return userDtoMapper.toUserResp(user);
	}

	@Override
	@Transactional
	public UserResp updateUser(User user, UserReq req) {

		User updatedUser = userManager.updateUser(user, req);
		return userDtoMapper.toUserResp(updatedUser);
	}

	@Override
	public UserResp getUser(User user) {
		return userDtoMapper.toUserResp(user);
	}
}
