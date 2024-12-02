package ureca.nolmung.implementation.user;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.user.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;

    // 사용자 유효성 검증 메서드
    public User validateUserExistence(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
    }

    public User findByEmail(String email)
    {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
    }

    public Optional<User> findByEmailAndProvider(String email, Provider provider)
    {
        return userRepository.findByEmailAndProvider(email, provider);
    }

    public User updateUser(User user, UserReq req) {
        user.updateUserInfo(req);
        return userRepository.save(user);
    }

    public User registerUserWithSocialLogin(String name, String profileImageUrl, String email, Provider provider)
    {
        User user = new User();

        user.setSocialLoginInfo(name, profileImageUrl, email, provider);

        return userRepository.save(user);
    }
}
