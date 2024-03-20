package com.weather.app.config.filter;

import java.io.IOException;

import org.springframework.web.filter.GenericFilterBean;

import com.weather.app.exception.JWTTokenException;
import com.weather.app.exception.NoTokenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends GenericFilterBean {



	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;
		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final String authHeader = httpRequest.getHeader("Authorization");

		if ("OPTIONS".equals(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpServletResponse.SC_OK);
			chain.doFilter(request, response);
		} else {
			try {
				if (authHeader == null || (!authHeader.startsWith("Bearer "))) {
					throw new NoTokenException("Missing or invalid Authorization header");
				}
				String token = authHeader.substring(7);
				System.out.println(token);
				final Claims claims = Jwts.parser().setSigningKey("mysecretkeyjusttocreatejwttokenHnKagaintestingoKOK").build().parseSignedClaims(token).getBody();
				String username = claims.get("username", String.class);
				boolean result = Jwts.parser().setSigningKey("mysecretkeyjusttocreatejwttokenHnKagaintestingoKOK").build().isSigned(token);

				chain.doFilter(request, response);
			} catch (JwtException e) {
				throw new JWTTokenException("Invalid token");
			}

		}
	}

}
