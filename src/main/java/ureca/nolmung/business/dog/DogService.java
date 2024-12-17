package ureca.nolmung.business.dog;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.implementation.dog.DogManager;
import ureca.nolmung.implementation.dog.dtomapper.DogDtoMapper;
import ureca.nolmung.jpa.dog.Dog;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class DogService implements DogUseCase{

    private final DogManager dogManager;
    private final DogDtoMapper dogDtoMapper;

    @Override
    @Transactional
    public DogResp addDog(User user, DogReq req) {
        // 반려견 프로필 추가
        dogManager.checkDogRegistrationLimit(user.getId());
        Dog dog = dogManager.addDog(user,req);
        return dogDtoMapper.toDogResp(dog);
    }

    @Override
    @Transactional
    public DogResp updateDog(Long userId, Long dogId, DogReq req) {
        // 반려견 프로필 수정
        Dog currentDog = dogManager.validateDogExistence(userId, dogId);
        Dog updatedDog = dogManager.updateDog(currentDog, req);
        return dogDtoMapper.toDogResp(updatedDog);
    }

    @Override
    @Transactional
    public DogResp deleteDog(Long userId, Long dogId) {
        // 반려견 프로필 삭제
        Dog currentDog = dogManager.validateDogExistence(userId, dogId);
        dogManager.deleteDog(dogId);
        return dogDtoMapper.toDogResp(currentDog);
    }

    @Override
    @Transactional(readOnly = true)
    public DogResp getDog(Long userId, Long dogId) {
        // 특정 반려견 프로필 반환
        Dog dog = dogManager.validateDogExistence(userId, dogId);
        return dogDtoMapper.toDogResp(dog);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DogResp> getDogList(Long userId) {
        // 사용자의 반려견 프로필 목록 반환
        return dogManager.getDogList(userId).stream()
                .map(dog -> dogDtoMapper.toDogResp(dog))  // Dog 객체를 DogResp로 변환
                .collect(Collectors.toList());
    }


}
