package com.weather.app;

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
				.route("service-discovery", r -> r.path("/discovery/**").filters(f -> f.filter(filter)).uri("lb://service-discovery"))
				.route("authentication-service", r -> r.path("/user-profile/**").filters(f -> f.filter(filter)).uri("lb://authentication-service"))
				.route("weather-service", r -> r.path("/weather/**").filters(f -> f.filter(filter)).uri("lb://weather-service"))
				.build();
	}
}


// for reference 

// builder.routes().route("authentication-service", r -> r.path("/api/**").filters(f -> f.filter(filter)).uri("lb://lb://authentication-service"))
//		.route("alert", r -> r.path("/alert/**").filters(f -> f.filter(filter)).uri("lb://alert"))
//		.route("echo", r -> r.path("/echo/**").filters(f -> f.filter(filter)).uri("lb://echo"))
//		.route("hello", r -> r.path("/hello/**").filters(f -> f.filter(filter)).uri("lb://hello")).build();