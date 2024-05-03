package com.weather.app.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.FeignClientService;
import com.weather.app.service.UserService;

import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/user-profile")
@CrossOrigin(origins = "*")
public class UserRegistrationController {

	@Autowired
	UserService userService;

	@Autowired 
	FeignClientService feignClientService; 
	
	@PostMapping("/upsert-user")
	public ResponseEntity<?> registerUser(@RequestBody  @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document userDetails) {
		return new ResponseEntity<Document>(userService.registerUserUpsert(userDetails), HttpStatus.CREATED);
	}
	
	@PutMapping("/update-email")
	public ResponseEntity<?> updateEmail(@RequestParam String username, @RequestParam String email) {
		return new ResponseEntity<String>(userService.updateEmail(username, email), HttpStatus.OK);
	}
	
	@DeleteMapping("/delete-user/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String userName) {
		return new ResponseEntity<Boolean>(userService.deleteUser(userName), HttpStatus.OK);
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String userName) {
		return new ResponseEntity<Document>(userService.getUser(userName), HttpStatus.OK);
	}
	
	@GetMapping("/users")
	public ResponseEntity<?> users() {
		return new ResponseEntity<List<Document>>(userService.getAllUsers(), HttpStatus.OK);
	}


	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document userDetails) {
		return new ResponseEntity<Document>(userService.login(userDetails), HttpStatus.OK);
	}

	@GetMapping("/activate-deactivate-user/{username}")
	public ResponseEntity<?> enableUser(@PathVariable("username") @Schema(example = "username") String username,
			@PathVariable("username") @Schema(example = "status") String status) {
		return new ResponseEntity<String>(userService.actOrDeactUser(username, status), HttpStatus.OK);
	}
	
	
	@GetMapping("/ping")
	public ResponseEntity<?> ping() {
		return new ResponseEntity<String>( " STATUS UP \n " + feignClientService.abcd(), HttpStatus.OK);
	}
	
	// TO BE IMPLEMENTED
	@PostMapping("/bookmark/{}")
	public ResponseEntity<?> bookmark(@RequestBody  @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document userDetails) {
		return new ResponseEntity<Document>(userService.registerUserUpsert(userDetails), HttpStatus.CREATED);
	}
}
