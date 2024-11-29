package ureca.nolmung.business.dog;

import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;

import java.util.List;

public interface DogUseCase {
    DogResp addDog(Long userId, DogReq req);
    DogResp updateDog(Long userId, Long dogId, DogReq req);
    DogResp deleteDog(Long userId, Long dogId);

    DogResp getDog(Long userId,Long dogId);
    List<DogResp> getDogList(Long userId);
}
