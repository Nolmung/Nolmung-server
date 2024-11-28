package ureca.nolmung.business.dog;

import ureca.nolmung.business.dog.dto.request.AddDogReq;

public interface DogUseCase {
    Long addDog(Long userId, AddDogReq req);
}
