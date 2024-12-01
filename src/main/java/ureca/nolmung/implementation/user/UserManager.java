package ureca.nolmung.implementation.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    // 사용자 유효성 검증 메서드
    public User validateUserExistence(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
    }

    public User updateUser(User user, UserReq req) {
        user.update(req);
        return user;
    }
}
