package ureca.nolmung.presentation.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "헬스 체크")
@RestController
public class HealthCheckController {

	@Operation(summary = "헬스 체크")
	@GetMapping("/")
	public String healthCheck() {
		return "Im Healthy";
	}

}
