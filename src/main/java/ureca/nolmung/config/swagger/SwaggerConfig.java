package ureca.nolmung.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@OpenAPIDefinition(
	servers = {
		@Server(url = "https://api.nolmung.org", description = "운영 서버"),
		@Server(url = "https://dev.nolmung.org", description = "개발 서버"),
		@Server(url = "http://localhost:8080", description = "로컬 서버")
	}
)
@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI openAPI() {
		SecurityScheme apiKey = new SecurityScheme()
			.type(SecurityScheme.Type.APIKEY)
			.in(SecurityScheme.In.HEADER)
			.name("Authorization");

		SecurityRequirement securityRequirement = new SecurityRequirement()
			.addList("Bearer Token");

		return new OpenAPI()
			.components(new Components().addSecuritySchemes("Bearer Token", apiKey))
			.addSecurityItem(securityRequirement)
			.info(apiInfo());
	}

	private Info apiInfo() {
		return new Info()
			.title("Nolmung API Document")
			.description("놀멍 API 문서")
			.version("1.0.0");
	}

}
