package ureca.nolmung.implementation.dog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ureca.nolmung.business.dog.dto.request.AddDogReq;
import ureca.nolmung.exception.user.UserException;
import ureca.nolmung.exception.user.UserExceptionType;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.dog.Enum.DogSize;
import ureca.nolmung.jpa.dog.Enum.Gender;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.dog.DogRepository;
import ureca.nolmung.persistence.user.UserRepository;

@Component
@RequiredArgsConstructor
public class DogManager {

    private final UserRepository userRepository;
    private final DogRepository dogRepository;
    public Long addDog(Long userId, AddDogReq req) {

        // 1. 사용자 유효성 검증
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserExceptionType.USER_NOT_FOUND_EXCEPTION)); // 예외 처리

        // 2. 반려견 정보 저장
        Dog newDog = createDog(user, req);
        dogRepository.save(newDog);

        return newDog.getId();
    }


    private Dog createDog(User user, AddDogReq req)
    {
        return Dog.builder()
                .user(user)
                .name(req.dogName())
                .gender(Gender.valueOf(req.gender()))
                .neuteredYn(req.neuterYn())
                .type(req.dogType())
                .size(DogSize.valueOf(req.size()))
                .birth(req.birth())
                .profileImageUrl(req.profileUrl()).build();
    }
}
