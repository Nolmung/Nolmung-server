package ureca.nolmung.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Tag(name = "헬스 체크")
@RestController
public class HealthCheckController {

	@Operation(summary = "헬스 체크")
	@GetMapping("/")
	public String healthCheck() {
		return "Im Healthy";
	}

	@Operation(summary = "Error Log 발생")
	@GetMapping("/errors/logs")
	public String errorLogs() {
		log.error("에러 로그 발생!!(Test)");
		return "에러 로그 발생시켰습니다";
	}

}
