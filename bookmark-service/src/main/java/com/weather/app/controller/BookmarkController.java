package com.weather.app.controller;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weather.app.service.BookmarkService;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bookmark")
public class BookmarkController {

	@Autowired
	BookmarkService bookmarkService;

	@PostMapping("/store")
	public ResponseEntity<?> registerUser(HttpServletRequest request,
			@RequestParam(value = "username", required = true) String username,
			@RequestBody @Schema(example = "{\"username\": \"adsf\", \"email\": \"asdf\", \"password\" : \"dfs\"}") Document bookmarkDetails) {
		System.out.println(" *****  " + request.getHeader("username") + " **********");
		username = request.getHeader("username") != null ? request.getHeader("username") : username;
		return new ResponseEntity<Document>(bookmarkService.upsertBookmark(username, bookmarkDetails),
				HttpStatus.CREATED);
	}

	@GetMapping("/bookmarks/{username}")
	public ResponseEntity<?> getBookmarkByUser(@PathVariable("username") String userName) {
		return new ResponseEntity<Document>(bookmarkService.bookmarks(userName), HttpStatus.OK);
	}

	@GetMapping(value = "/all-bookmarks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookmarks() {
		return new ResponseEntity<List<Document>>(bookmarkService.getAllBookmarks(), HttpStatus.OK);
	}

	@GetMapping("/ping")
	public ResponseEntity<?> ping() {
		return new ResponseEntity<String>("Bookmark service is UP", HttpStatus.OK);
	}

}
