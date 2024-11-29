package ureca.nolmung.presentation.controller.recommend;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ureca.nolmung.business.recommend.RecommendUseCase;
import ureca.nolmung.business.recommend.dto.response.RecommendResp;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/recommend")
public class RecommendController {

    private final RecommendUseCase recommendUseCase;

    //TODO 나중에 토큰 어쩌구 JWT 어쩌구 인증된 사용자만 할 수 있게 처리 필요
    @GetMapping("/users/{userId}/similar/bookmarks")
    public ResponseDto<List<RecommendResp>> getPlaceRecommendations(@PathVariable Long userId) {
        return ResponseUtil.SUCCESS(
                "개인 맞춤형 장소 추천에 성공하였습니다.",
                recommendUseCase.getPlaceRecommendationsFromPersonalize(userId));
    }

    @GetMapping("/bookmarks")
    public ResponseDto<List<RecommendResp>> getBookmarkRecommendations() {
        return ResponseUtil.SUCCESS(
                "즐겨찾기가 많은 장소 추천에 성공하였습니다.",
                recommendUseCase.getMostBookmarkedPlaces()
        );
    }

    //TODO 나중에 토큰 어쩌구 JWT 어쩌구 인증된 사용자만 할 수 있게 처리 필요
    @GetMapping("/users/{userId}/weight")
    public ResponseDto<List<RecommendResp>> getWeightRecommendations(@PathVariable Long userId) {
        return ResponseUtil.SUCCESS(
                "반려견들 크기에 맞는 장소 추천에 성공하였습니다.",
                recommendUseCase.getPlaceRecommendationsForDogs(userId)
        );
    }
}
