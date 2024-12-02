package ureca.nolmung.presentation.controller.banword;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import ureca.nolmung.business.banword.BanWordUseCase;
import ureca.nolmung.config.response.ResponseDto;
import ureca.nolmung.config.response.ResponseUtil;

@Tag(name = "금칙어")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ban-words")
public class BanWordController {

	private final BanWordUseCase banWordUseCase;

	@Operation(summary = "금칙어 업로드", description = "사용 X")
	@PostMapping("/upload")
	public ResponseDto<String> uploadWords() {
		banWordUseCase.saveBanWordsFromFile();
		return ResponseUtil.SUCCESS("금칙어 업로드에 성공하였습니다.", "2024-12-01 업데이트");
	}

}
