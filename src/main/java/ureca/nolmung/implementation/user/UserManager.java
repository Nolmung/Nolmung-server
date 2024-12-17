package ureca.nolmung.implementation.user;

import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.user.dto.request.UserReq;
import ureca.nolmung.jpa.user.Enum.Provider;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.jpa.userbookmark.UserBookmark;
import ureca.nolmung.persistence.user.UserRepository;
import ureca.nolmung.persistence.userbookmark.UserBookmarkRepository;

@Component
@RequiredArgsConstructor
public class UserManager {

    private final UserRepository userRepository;
    private final UserBookmarkRepository userBookmarkRepository;


    public User validateUserExistence(Long userId) {
        // 사용자 유효성 검증
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
    }

    public User findByEmail(String email)
    {
        // 이메일에 해당하는 사용자를 반환
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));
    }

    public Optional<User> findByEmailAndProvider(String email, Provider provider)
    {
        // 소셜 로그인 유형(카카오...)과 이메일을 사용하여 사용자 정보를 조회하고 반환
        return userRepository.findByEmailAndProvider(email, provider);
    }

    public User updateUser(User user, UserReq req) {
        // 사용자 정보 수정
        user.updateUserInfo(req);
        return userRepository.save(user);
    }

    public User registerUserWithSocialLogin(String name, String profileImageUrl, String email, Provider provider)
    {
        // 소셜 로그인을 통해 가져온 정보(프로필 이미지, 이메일...)로 사용자 정보 등록
        User user = new User();

        user.setSocialLoginInfo(name, profileImageUrl, email, provider);

        return userRepository.save(user);
    }

    public void updateBookmarkCount(Long userId) {
        // 해당 사용자의 북마크 수를 업데이트
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION));

        int userBookmarkCount = user.getBookmarkCount();

        UserBookmark userBookmark = userBookmarkRepository.findById(userId)
                .orElseGet(() -> UserBookmark.createUserBookmark(userId, userBookmarkCount));

        userBookmark.updateBookmarkCount(userBookmarkCount);

        userBookmarkRepository.save(userBookmark);
    }

    public void addBookmarkCount(User user) {
        // 해당 사용자의 북마크 개수 증가
        user.addBookmarkCount();
        userRepository.save(user);
    }

    public void subtractBookmarkCount(User user) {
        // 해당 사용자의 북마크 개수 감소
        user.subtractBookmarkCount();
        userRepository.save(user);
    }
}
