package com.weather.app.config.security.crypto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderService {

	public PasswordEncoderService() {
	}

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public String encodePassword(String password) {
		return bCryptPasswordEncoder.encode(password);
	}

	public boolean matches(String rawPassword, String encodedPassword) {
		return bCryptPasswordEncoder.matches(rawPassword, encodedPassword);
	}
}
