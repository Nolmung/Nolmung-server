package ureca.nolmung.presentation.controller.bookmark;

import org.springframework.web.bind.annotation.DeleteMapping;
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
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;
import ureca.nolmung.presentation.controller.bookmark.request.BookmarkRequest;

@Tag(name = "북마크")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/bookmarks")
public class BookmarkController {

	private final BookmarkUseCase bookmarkUseCase;

	@Operation(summary = "즐겨찾기 등록")
	@PostMapping("")
	public ResponseDto<Long> createBookmark(@RequestParam Long userId, @Valid @RequestBody BookmarkRequest request) {
		return ResponseUtil.SUCCESS("즐겨찾기 등록에 성공하였습니다.", bookmarkUseCase.createBookmark(userId, request.toServiceRequest()));
	}

	@Operation(summary = "즐겨찾기 삭제")
	@DeleteMapping("")
	public ResponseDto<Long> deleteBookmark(@RequestParam Long userId, @RequestParam Long bookmarkId) {
		return ResponseUtil.SUCCESS("즐겨찾기 삭제에 성공하였습니다.", bookmarkUseCase.deleteBookmark(userId, bookmarkId));
	}

}
