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
        return dogRepository.findByUserIdAndId(userId, dogId)
                .orElseThrow(() -> new DogException(DogExceptionType.DOG_NOT_FOUND_EXCEPTION));
    }

    public Dog addDog(User user, DogReq req) {

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

        dog.update(req);

        return dog;
    }


    public void deleteDog(Long dogId) {
        dogRepository.deleteById(dogId);
    }

    public List<Dog> getDogList(Long userId) {
        return dogRepository.findByUserId(userId);
    }

    public void checkDogRegistrationLimit(Long userId)
    {
        if(dogRepository.countByUserId(userId) >= MAX_DOG_REGISTRATION_LIMIT)
        {
            throw new DogException(DogExceptionType.DOG_REGISTRATION_LIMIT_EXCEEDED);
        }
    }
}
