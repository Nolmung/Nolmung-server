package ureca.nolmung.business.dog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.nolmung.business.dog.dto.request.AddDogReq;
import ureca.nolmung.implementation.dog.DogManager;

@Service
@RequiredArgsConstructor
public class DogService implements DogUseCase{

    private final DogManager dogManager;

    @Override
    @Transactional
    public Long addDog(Long userId, AddDogReq req) {
        return dogManager.addDog(userId,req);
    }
}
