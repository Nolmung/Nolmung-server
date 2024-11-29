package ureca.nolmung.business.dog;

import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.implementation.dog.DogManager;
import ureca.nolmung.implementation.dog.dtomapper.DogDtoMapper;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class DogService implements DogUseCase{

    private final DogManager dogManager;
    private final UserManager userManager;
    private final DogDtoMapper dogDtoMapper;

    @Override
    @Transactional
    public DogResp addDog(Long userId, DogReq req) {

        User user = userManager.validateUserExistence(userId);
        Dog dog = dogManager.addDog(user,req);
        return dogDtoMapper.toDogResp(dog);
    }

    @Override
    @Transactional
    public DogResp updateDog(Long userId, Long dogId, DogReq req) {

        Dog currentDog = dogManager.validateDogExistence(userId, dogId);
        Dog updatedDog = dogManager.updateDog(currentDog, req);
        return dogDtoMapper.toDogResp(updatedDog);
    }

    @Override
    @Transactional
    public DogResp deleteDog(Long userId, Long dogId) {
        Dog currentDog = dogManager.validateDogExistence(userId, dogId);
        dogManager.deleteDog(dogId);
        return dogDtoMapper.toDogResp(currentDog);
    }

    @Override
    @Transactional(readOnly = true)
    public DogResp getDog(Long userId, Long dogId) {
        Dog dog = dogManager.validateDogExistence(userId, dogId);
        return dogDtoMapper.toDogResp(dog);
    }


}
