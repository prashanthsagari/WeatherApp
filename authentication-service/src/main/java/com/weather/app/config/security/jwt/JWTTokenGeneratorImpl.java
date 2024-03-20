package com.weather.app.config.security.jwt;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import com.weather.app.model.ERole;
import com.weather.app.model.Role;
import com.weather.app.model.User;

import io.jsonwebtoken.Jwts;

@Service
public class JWTTokenGeneratorImpl implements JWTTokenGenerator {

	@Value("${app.jwtSecret}")
	private String secret;

	@Value("${app.jwtSecret.message}")
	private String message;

	@Value("${app.jwtSecret.jwtExpirationMs}")
	private long expirationTime;

	@SuppressWarnings("deprecation")
	@Override
	public Document generateJWTToken(Document user) {
		String jwtToken = "";

		jwtToken = Jwts.builder().setSubject(user.getString("username")).claim("username", user.getString("username"))
				.claim("roles", user.getString("roles"))
				.claim("email", user.getString("email"))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secret).compact();

		Document jwtTokenMap = new Document();
		jwtTokenMap.put("token", jwtToken);
		jwtTokenMap.put("message", message);
		return jwtTokenMap;
	}
	
	public void testJWTTokenGenerator() {
		Set<Role> roles = new HashSet<>();
		roles.add(new Role(ERole.ROLE_ADMIN));
		
		User user = User.builder()
			.username("Prashanth Sagari")
			.email("prashanthps7013@gmail.com")
			.roles(roles)
			.build();
			
//		System.out.println(generateJWTToken(user));
	}

	
}
