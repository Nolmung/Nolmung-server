package ureca.nolmung.presentation.controller.place;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.place.PlaceUseCase;
import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;
import ureca.nolmung.jpa.place.Enum.Category;

@Tag(name = "장소")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/places")
public class PlaceController {

	private final PlaceUseCase placeUseCase;

	@Operation(summary = "키워드로 장소 검색")
	@GetMapping("/search")
	public ResponseDto<List<SearchedPlaceResponse>> searchByKeyword(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestParam String keyword) {
		return ResponseUtil.SUCCESS("장소 검색에 성공하였습니다.", placeUseCase.searchByKeyword(userDetails, keyword));
	}

	@Operation(summary = "지도에서 장소 검색")
	@GetMapping("/map")
	public ResponseDto<List<SearchedPlaceResponse>> findPlaceOnMap(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestParam double latitude,
		@RequestParam double longitude,
		@RequestParam double maxLatitude,
		@RequestParam double maxLongitude) {
		return ResponseUtil.SUCCESS("지도에서 장소 검색에 성공하였습니다.", placeUseCase.findPlaceOnMap(userDetails, latitude, longitude, maxLatitude, maxLongitude));
	}

	@Operation(summary = "장소 조회 필터링")
	@GetMapping("/filter")
	public ResponseDto<List<SearchedPlaceResponse>> findPlaceByFilter(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestParam(required = false) Category category,
		@RequestParam(required = false) Boolean isVisited,
		@RequestParam(required = false) Boolean isBookmarked,
		@RequestParam double latitude,
		@RequestParam double longitude,
		@RequestParam double maxLatitude,
		@RequestParam double maxLongitude) {
		return ResponseUtil.SUCCESS("장소 조회에 성공하였습니다.", placeUseCase.findBySearchOption(userDetails, category, isVisited, isBookmarked, latitude, longitude, maxLatitude, maxLongitude));
	}

	@Operation(summary = "장소 상세 정보 조회")
	@GetMapping("/details/{placeId}")
	public ResponseDto<PlaceDetailResponse> findPlaceDetailByPlaceId(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@PathVariable Long placeId) {
		return ResponseUtil.SUCCESS("장소 상세 정보 조회에 성공하였습니다.", placeUseCase.findPlaceDetailById(userDetails, placeId));
	}

	@Operation(summary = "공간 데이터 생성", description = "백엔드 정용 API")
	@PostMapping("/point-data")
	public ResponseDto<Integer> makePointData() {
		return ResponseUtil.SUCCESS("공간 데이터 생성에 성공하였습니다.", placeUseCase.makePointData());
	}

}
