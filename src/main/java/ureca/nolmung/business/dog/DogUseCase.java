package ureca.nolmung.business.dog;

import java.util.List;

import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.jpa.user.User;

public interface DogUseCase {
    DogResp addDog(User user, DogReq req);
    DogResp updateDog(Long userId, Long dogId, DogReq req);
    DogResp deleteDog(Long userId, Long dogId);

    DogResp getDog(Long userId,Long dogId);
    List<DogResp> getDogList(Long userId);
}
