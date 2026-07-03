package com.cesar.claims.shared.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
	info = @Info(
		title = "Claims Platform API",
		version = "v1",
		description = "Backend API for insurance claims management."
	)
)
class OpenApiConfig {
}

