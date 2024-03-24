package com.weather.app.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://roytuts.com/spring-cloud-gateway-security-with-jwt-json-web-token/
@Configuration
public class GatewayConfig {

	@Autowired
	private JwtAuthenticationFilter filter;

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("service-discovery",
						r -> r.path("/discovery/**").filters(f -> f.filter(filter)).uri("lb://service-discovery"))
				.route("authentication-service",
						r -> r.path("/user-profile/**").filters(f -> f.filter(filter))
								.uri("lb://authentication-service"))
				.route("weather-service",
						r -> r.path("/weather/**").filters(f -> f.filter(filter)).uri("lb://weather-service"))
				
				.route("service-discovery_swagger_ui",
						r -> r.path("/service-discovery/swagger-ui/**")
								.uri("lb://service-discovery/service-discovery/swagger-ui"))
				.route("authentication-service_swagger_ui",
						r -> r.path("/authentication-service/swagger-ui/**")
								.uri("lb://authentication-service/authentication-service/swagger-ui"))
				.route("weather-service_swagger_ui",
						r -> r.path("/weather-service/swagger-ui/**")
								.uri("lb://weather-service/weather-service/swagger-ui"))

				.build();
	}
}
