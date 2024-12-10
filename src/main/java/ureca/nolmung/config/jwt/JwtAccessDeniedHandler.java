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

		if (!endpointChecker.isEndpointExist(request)) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
		} else {
			response.sendError(HttpServletResponse.SC_FORBIDDEN, accessDeniedException.getMessage());
		}
	}
}
