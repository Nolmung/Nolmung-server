package ureca.nolmung.config.jwt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ureca.nolmung.exception.Status;
import ureca.nolmung.implementation.jwt.JwtTokenExceptionType;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Override
	public void commence(HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException authException) throws IOException {

		JwtTokenExceptionType exceptionType = (JwtTokenExceptionType) request.getAttribute("exception");

		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setCharacterEncoding("utf-8");
		response.setContentType("application/json");
		Map<String, Object> data = new HashMap<>();
		data.put("status", Status.UNAUTHORIZED);

		if(exceptionType != null)
		{
			data.put("message", exceptionType.message());
		}
		else {
			data.put("message", authException.getMessage());
		}

		ObjectMapper objectMapper = new ObjectMapper();
		String result = objectMapper.writeValueAsString(data);

		response.getWriter().write(result);
	}
}
