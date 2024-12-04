package ureca.nolmung.presentation.controller.recommend;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ureca.nolmung.business.recommend.RecommendUseCase;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

import java.util.List;

@Tag(name = "추천")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/recommend")
public class RecommendController {

    private final RecommendUseCase recommendUseCase;

    @Operation(summary = "개인 맞춤형 장소 추천")
    @GetMapping("/similar/bookmarks")
    public ResponseDto<List<RecommendResp>> getPlaceRecommendations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseUtil.SUCCESS(
                "개인 맞춤형 장소 추천에 성공하였습니다.",
                recommendUseCase.getPlaceRecommendationsFromPersonalize(userDetails.getUser()));
    }

    @Operation(summary = "즐겨찾기가 많은 장소 추천")
    @GetMapping("/bookmarks")
    public ResponseDto<List<RecommendResp>> getBookmarkRecommendations() {
        return ResponseUtil.SUCCESS(
                "즐겨찾기가 많은 장소 추천에 성공하였습니다.",
                recommendUseCase.getMostBookmarkedPlaces()
        );
    }

    //TODO 나중에 토큰 어쩌구 JWT 어쩌구 인증된 사용자만 할 수 있게 처리 필요
    @Operation(summary = "반려견들의 크기에 맞는 장소 추천")
    @GetMapping("/weight")
    public ResponseDto<List<RecommendResp>> getWeightRecommendations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseUtil.SUCCESS(
                "반려견들 크기에 맞는 장소 추천에 성공하였습니다.",
                recommendUseCase.getPlaceRecommendationsForDogs(userDetails.getUser())
        );
    }

    @Operation(summary = "사용자 거주지 근처의 장소 추천")
    @GetMapping("/users/nearby")
    public ResponseDto<List<RecommendResp>> getNearbyRecommendations(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseUtil.SUCCESS(
                "사용자 근처의 장소 추천에 성공하였습니다.",
                recommendUseCase.getPlaceRecommendationsNearByUser(userDetails.getUser())
        );
    }
}
