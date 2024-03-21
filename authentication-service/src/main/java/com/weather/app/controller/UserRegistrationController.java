package com.weather.app.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.FeignClientService;
import com.weather.app.service.UserService;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
public class UserRegistrationController {

	@Autowired
	UserService userService;

	@Autowired 
	FeignClientService feignClientService; 
	
	@PostMapping("/create-user")
	public ResponseEntity<?> registerUser(@RequestBody  @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document userDetails) {
		return new ResponseEntity<Document>(userService.registerUser(userDetails), HttpStatus.CREATED);
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document userDetails) {
		return new ResponseEntity<Document>(userService.login(userDetails), HttpStatus.OK);
	}

	@GetMapping("/api/activate-user/{username}")
	public ResponseEntity<?> enableUser(@PathVariable("username") @Schema(example = "username") String username) {
		return new ResponseEntity<String>(userService.activateUser(username), HttpStatus.OK);
	}
	
	@GetMapping("/ping")
	public ResponseEntity<?> ping() {
		return new ResponseEntity<String>( " STATUS UP \n " + feignClientService.abcd(), HttpStatus.OK);
	}
}
