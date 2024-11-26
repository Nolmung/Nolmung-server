package ureca.nolmung.presentation.controller.place;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.place.PlaceService;
import ureca.nolmung.business.place.response.PlaceDetailResponse;
import ureca.nolmung.business.place.response.SearchedPlaceResponse;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/places")
public class PlaceController {

	private final PlaceService placeService;

	@GetMapping("/search")
	public ResponseDto<List<SearchedPlaceResponse>> searchByKeyword(@RequestParam String keyword) {
		return ResponseUtil.SUCCESS("장소 검색에 성공하였습니다.", placeService.searchByKeyword(keyword));
	}

	@GetMapping("-details/{placeId}")
	public ResponseDto<PlaceDetailResponse> findPlaceDetailByPlaceId(@PathVariable Long placeId) {
		return ResponseUtil.SUCCESS("장소 상세 정보 조회에 성공하였습니다.", placeService.findPlaceDetailById(placeId));
	}

	@PostMapping("/point-data")
	public ResponseDto<Integer> makePointData() {
		return ResponseUtil.SUCCESS("공간 데이터 생성에 성공하였습니다.", placeService.makePointData());
	}

}
