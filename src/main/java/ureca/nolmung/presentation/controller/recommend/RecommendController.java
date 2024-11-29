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
}
