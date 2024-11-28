package ureca.nolmung.presentation.controller.dog;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.dog.DogUseCase;
import ureca.nolmung.business.dog.dto.request.AddDogReq;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@RestController
@RequestMapping("/api/v1/dogs")
@RequiredArgsConstructor
public class DogController {

    private final DogUseCase dogUseCase;

    @PostMapping("")
    public ResponseDto<Long> addDog(@RequestParam("userId") Long userId, @RequestBody AddDogReq req) {
        Long createDogId = dogUseCase.addDog(userId, req);
        return ResponseUtil.SUCCESS("반려견 프로필 생성에 성공하였습니다.",createDogId);
    }
}
