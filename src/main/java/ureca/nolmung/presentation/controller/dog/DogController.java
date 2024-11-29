package ureca.nolmung.presentation.controller.dog;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.dog.DogUseCase;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@RestController
@RequestMapping("/api/v1/dogs")
@RequiredArgsConstructor
public class DogController {

    private final DogUseCase dogUseCase;

    @PostMapping("")
    public ResponseDto<DogResp> addDog(@RequestParam("userId") Long userId, @RequestBody DogReq req) {

        DogResp resp = dogUseCase.addDog(userId, req);
        return ResponseUtil.SUCCESS("반려견 프로필 생성에 성공하였습니다.",resp);
    }

    @PutMapping("")
    public ResponseDto<DogResp> updateDog(@RequestParam("userId") Long userId,@RequestParam("dogId") Long dogId, @RequestBody DogReq req) {
        return ResponseUtil.SUCCESS("반려견 프로필 수정에 성공하였습니다.",dogUseCase.updateDog(userId, dogId, req));
    }

    @DeleteMapping("")
    public ResponseDto<DogResp> deleteDog(@RequestParam("userId") Long userId, @RequestParam("dogId") Long dogId) {
        return ResponseUtil.SUCCESS("반려견 프로필 삭제에 성공하였습니다.", dogUseCase.deleteDog(userId, dogId));
    }

    @GetMapping("")
    public ResponseDto<DogResp> getDog(@RequestParam("userId") Long userId, @RequestParam("dogId") Long dogId) {
        DogResp dogResp = dogUseCase.getDog(userId, dogId);
        return ResponseUtil.SUCCESS("반려견 프로필 조회에 성공하였습니다.", dogResp);
    }


}
