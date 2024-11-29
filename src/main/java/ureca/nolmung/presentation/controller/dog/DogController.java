package ureca.nolmung.presentation.controller.dog;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ureca.nolmung.business.dog.DogUseCase;
import ureca.nolmung.business.dog.dto.request.DogReq;
import ureca.nolmung.business.dog.dto.response.DogResp;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

import java.util.List;

@Tag(name = "반려견 프로필")
@RestController
@RequestMapping("/api/v1/dogs")
@RequiredArgsConstructor
public class DogController {

    private final DogUseCase dogUseCase;

    @Operation(summary = "반려견 프로필 등록")
    @PostMapping("")
    public ResponseDto<DogResp> addDog(@RequestParam("userId") Long userId, @RequestBody DogReq req) {

        DogResp resp = dogUseCase.addDog(userId, req);
        return ResponseUtil.SUCCESS("반려견 프로필 생성에 성공하였습니다.",resp);
    }

    @Operation(summary = "반려견 프로필 수정")
    @PutMapping("")
    public ResponseDto<DogResp> updateDog(@RequestParam("userId") Long userId,@RequestParam("dogId") Long dogId, @RequestBody DogReq req) {
        return ResponseUtil.SUCCESS("반려견 프로필 수정에 성공하였습니다.",dogUseCase.updateDog(userId, dogId, req));
    }

    @Operation(summary = "반려견 프로필 삭제")
    @DeleteMapping("")
    public ResponseDto<DogResp> deleteDog(@RequestParam("userId") Long userId, @RequestParam("dogId") Long dogId) {
        return ResponseUtil.SUCCESS("반려견 프로필 삭제에 성공하였습니다.", dogUseCase.deleteDog(userId, dogId));
    }

    @Operation(summary = "반려견 프로필 조회")
    @GetMapping("")
    public ResponseDto<DogResp> getDog(@RequestParam("userId") Long userId, @RequestParam("dogId") Long dogId) {
        DogResp dogResp = dogUseCase.getDog(userId, dogId);
        return ResponseUtil.SUCCESS("반려견 프로필 조회에 성공하였습니다.", dogResp);
    }

    @Operation(summary = "반려견 프로필 목록 조회")
    @GetMapping("/list")
    public ResponseDto<List<DogResp>> getDogList(@RequestParam("userId") Long userId) {
        List<DogResp> dogRespList = dogUseCase.getDogList(userId);
        return ResponseUtil.SUCCESS("반려견 프로필 목록 조회에 성공하였습니다.", dogRespList);
    }


}
