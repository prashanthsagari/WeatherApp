package com.weather.app.config;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.server.ServerWebExchange;

import com.weather.app.exception.JWTTokenException;
import com.weather.app.exception.NoTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import reactor.core.publisher.Mono;

@Component
@CrossOrigin(origins = "*")
public class JwtAuthenticationFilter implements GatewayFilter {

	@Value("${app.jwtSecret}")
	private String secret;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = (ServerHttpRequest) exchange.getRequest();
		ServerHttpResponse response = (ServerHttpResponse) exchange.getResponse();

		// whitelist urls
		// Todo can be database driven
		final List<String> apiEndpoints = List.of("/user-profile/login", "/user-profile/ping",
				"/user-profile/upsert-user", "/weather/test", "/gateway-actuators/**", "/user-actuators/**",
				"/discovery-actuators/**", "/weather-actuators/**",
				"/bookmark/store", "/bookmark/ping"
				);

		Predicate<ServerHttpRequest> isApiSecured = r -> apiEndpoints.stream()
				.noneMatch(uri -> r.getURI().getPath().contains(uri));

		final String authHeader = request.getHeaders().getFirst("Authorization");

		if ("OPTIONS".equals(request.getMethod())) {
			response.setStatusCode(HttpStatus.OK);
			chain.filter(exchange);
		} else if (isApiSecured.test(request)) {
			try {
				if (authHeader == null || (!authHeader.startsWith("Bearer "))) {
					throw new NoTokenException("Missing or invalid Authorization header");
				}
				final String token = authHeader.substring(7);
				final Claims claims = Jwts.parser().setSigningKey(secret).build().parseSignedClaims(token).getBody();
				String username = claims.get("username", String.class);
				boolean result = Jwts.parser().setSigningKey(secret).build().isSigned(token);
				exchange.getRequest().mutate().header("username", username).build();
				chain.filter(exchange);
			} catch (JwtException e) {
				throw new JWTTokenException("Invalid token");
			}

		}
		//testing purpose
//		exchange.getRequest().mutate().header("username", "Prashanth Sagari").build();
		return chain.filter(exchange);
	}

}
