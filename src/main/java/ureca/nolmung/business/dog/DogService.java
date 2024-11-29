package ureca.nolmung.business.dog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.implementation.dog.DogManager;
import ureca.nolmung.implementation.user.UserManager;
import ureca.nolmung.jpa.user.User;

@Service
@RequiredArgsConstructor
public class DogService implements DogUseCase{

    private final DogManager dogManager;
    private final UserManager userManager;

    @Override
    @Transactional
    public DogResp addDog(Long userId, DogReq req) {

        User user = userManager.validateUserExistence(userId);

        return dogManager.addDog(user,req);
    }
}
