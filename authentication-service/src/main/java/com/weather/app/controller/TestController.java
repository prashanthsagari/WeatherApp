package com.weather.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-profile")
public class TestController {
	
	@GetMapping("/health")
	public ResponseEntity<?> ping() {
		return new ResponseEntity<String>("STATUS UP Authentication or user service", HttpStatus.OK);
	}
}
