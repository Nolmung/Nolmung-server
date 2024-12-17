package ureca.nolmung.config.jwt;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

	private final HttpRequestEndpointChecker endpointChecker;

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {

		// 요청된 엔드포인트가 존재하지 않는 경우 404 오류 반환
		if (!endpointChecker.isEndpointExist(request)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
		}
		// 접근이 거부된 경우 403 오류 반환
		else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
		}
	}
}
