package ureca.nolmung.config.jwt;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class HttpRequestEndpointChecker {

	private final DispatcherServlet servlet;

	public boolean isEndpointExist(HttpServletRequest request) {
		// 요청된 엔드포인트가 존재하면 true, 그렇지 않으면 false 반환
		for (HandlerMapping handlerMapping : servlet.getHandlerMappings()) {
			try {
				HandlerExecutionChain foundHandler = handlerMapping.getHandler(request);
				if (foundHandler != null) {
					return true;
				}
			} catch (Exception e) {
				return false;
			}
		}
		return false;
	}
}
