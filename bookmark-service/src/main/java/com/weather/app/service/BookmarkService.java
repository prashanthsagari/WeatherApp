package com.weather.app.service;

import java.util.List;

import org.bson.Document;

public interface BookmarkService {

	public List<Document> bookmark(String username, Document bookmarkDetails);
}
