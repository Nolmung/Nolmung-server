package ureca.nolmung.implementation.dog;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.dog.Enum.DogSize;
import ureca.nolmung.jpa.dog.Enum.Gender;
import ureca.nolmung.jpa.user.User;
import ureca.nolmung.persistence.dog.DogRepository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DogManager {

    private final DogRepository dogRepository;

    public Dog validateDogExistence(Long userId, Long dogId) {
        return dogRepository.findByUserIdAndId(userId, dogId)
                .orElseThrow(() -> new DogException(DogExceptionType.DOG_NOT_FOUND_EXCEPTION));
    }

    public Dog addDog(User user, DogReq req) {

        Dog newDog = Dog.builder()
                .user(user)
                .name(req.dogName())
                .gender(Gender.valueOf(req.gender()))
                .neuteredYn(req.neuterYn())
                .type(req.dogType())
                .size(DogSize.valueOf(req.size()))
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
}
