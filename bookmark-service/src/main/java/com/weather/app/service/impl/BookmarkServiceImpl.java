package com.weather.app.service.impl;

import java.util.List;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.weather.app.db.service.MongoDbService;
import com.weather.app.service.BookmarkService;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	@Autowired
	private MongoDbService mongoDbService;

	@Value("${app.mongodb.collection}")
	private String mongoCollection;

	@Override
	public List<Document> bookmark(String username, Document bookmarkDetails) {

		return List.of(bookmarkDetails);
	}

}
