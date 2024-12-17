package ureca.nolmung.presentation.controller.bookmark;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.bookmark.BookmarkUseCase;
import ureca.nolmung.business.bookmark.dto.response.BookmarkResponse;
import ureca.nolmung.business.user.dto.response.CustomUserDetails;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;
import ureca.nolmung.jpa.place.Enum.Category;
import ureca.nolmung.presentation.controller.bookmark.dto.request.BookmarkRequest;

@Tag(name = "북마크")
@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/bookmarks")
public class BookmarkController {

	private final BookmarkUseCase bookmarkUseCase;

	@Operation(summary = "즐겨찾기 목록 조회")
	@GetMapping("")
	public ResponseDto<List<BookmarkResponse>> findAllBookmarks(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestParam Category category) {
		return ResponseUtil.SUCCESS("즐겨찾기 목록 조회에 성공하였습니다.", bookmarkUseCase.findAllBookmarks(userDetails.getUser(), category));
	}

	@Operation(summary = "즐겨찾기 등록")
	@PostMapping("")
	public ResponseDto<Long> createBookmark(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@Valid @RequestBody BookmarkRequest request) {
		return ResponseUtil.SUCCESS("즐겨찾기 등록에 성공하였습니다.", bookmarkUseCase.createBookmark(userDetails.getUser(), request.toServiceRequest()));
	}

	@Operation(summary = "즐겨찾기 삭제")
	@DeleteMapping("")
	public ResponseDto<Long> deleteBookmark(
		@AuthenticationPrincipal CustomUserDetails userDetails,
		@RequestParam Long placeId) {
		return ResponseUtil.SUCCESS("즐겨찾기 삭제에 성공하였습니다.", bookmarkUseCase.deleteBookmark(userDetails.getUser(), placeId));
	}

}
