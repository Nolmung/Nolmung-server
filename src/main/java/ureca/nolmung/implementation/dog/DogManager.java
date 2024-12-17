package ureca.nolmung.implementation.dog;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.dog.DogRepository;

@Component
@RequiredArgsConstructor
public class DogManager {

    private final DogRepository dogRepository;
    private static final int MAX_DOG_REGISTRATION_LIMIT = 5;

    public Dog validateDogExistence(Long userId, Long dogId) {
        // 주어진 사용자 ID와 개 ID에 해당하는 반려견 프로필 정보가 유효한지 검증 후 반환
        return dogRepository.findByUserIdAndId(userId, dogId)
                .orElseThrow(() -> new DogException(DogExceptionType.DOG_NOT_FOUND_EXCEPTION));
    }

    public Dog addDog(User user, DogReq req) {
        // 반려견 프로필 정보 추가
        Dog newDog = Dog.builder()
                .user(user)
                .name(req.dogName())
                .gender(req.gender())
                .neuteredYn(req.neuterYn())
                .type(req.dogType())
                .size(req.size())
                .birth(req.birth())
                .profileImageUrl(req.profileUrl()).build();

        dogRepository.save(newDog);

        return newDog;
    }

    public Dog updateDog(Dog dog, DogReq req) {
        // 반려견 프로필 정보 수정
        dog.update(req);

        return dog;
    }


    public void deleteDog(Long dogId) {
        // 반려견 프로필 정보 삭제
        dogRepository.deleteById(dogId);
    }

    public List<Dog> getDogList(Long userId) {
        // 사용자 ID에 해당하는 반려견 프로필 정보 리스트로 반환
        return dogRepository.findByUserId(userId);
    }

    public void checkDogRegistrationLimit(Long userId)
    {
        // 현재 등록된 반려견 프로필 개수를 확인하여 반려견 프로필 추가 가능한 상태인지 판단 (등록된 프로필 개수가 5 미만일 경우 가능)
        if(dogRepository.countByUserId(userId) >= MAX_DOG_REGISTRATION_LIMIT)
        {
            throw new DogException(DogExceptionType.DOG_REGISTRATION_LIMIT_EXCEEDED);
        }
    }
}
