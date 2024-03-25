package com.weather.app.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.service.BookmarkService;

import io.jsonwebtoken.lang.Collections;
import io.swagger.v3.oas.annotations.media.Schema;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

	@Autowired 
	BookmarkService bookmarkService;
	
	@PostMapping("/store")
	public ResponseEntity<?> registerUser(@RequestHeader(value = "username", required = true, defaultValue = "Dummy USER") String username,
			@RequestBody @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document bookmarkDetails) {
		return new ResponseEntity<List<Document>>(bookmarkService.bookmark(username, bookmarkDetails), HttpStatus.CREATED);
	}

	@DeleteMapping("/delete-user/{username}")
	public ResponseEntity<?> deleteUser(@PathVariable("username") String userName) {
		return new ResponseEntity<Boolean>(true, HttpStatus.OK);
	}

	@GetMapping("/user/{username}")
	public ResponseEntity<?> getUser(@PathVariable("username") String userName) {
		return new ResponseEntity<Document>(new Document(), HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<?> users() {
		return new ResponseEntity<List<Document>>(Collections.emptyList(), HttpStatus.OK);
	}

	@GetMapping("/ping")
	public ResponseEntity<?> ping() {
		return new ResponseEntity<String>(" Bookmark service is UP", HttpStatus.OK);
	}

}
