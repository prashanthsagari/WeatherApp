package com.weather.app.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

	@Bean
	 public OpenAPI customOpenAPI() {
	   return new OpenAPI()
	        .addSecurityItem(new SecurityRequirement().addList("Bearer Authentication"))
	        .components(new Components().addSecuritySchemes("Bearer Authentication",  createAPI()))
	        .info(new Info().title("Authentication Server")
	        		.description("Securing API using JWT token.")
	        		.title("Prashanth Sagari"));
	}
	
	private SecurityScheme createAPI() {
		return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("Bearer");
	}
}
