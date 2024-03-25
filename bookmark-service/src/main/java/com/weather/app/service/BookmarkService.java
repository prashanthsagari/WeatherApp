package com.weather.app.service;

import java.util.List;

import org.bson.Document;

public interface BookmarkService {

	public Document upsertBookmark(String username, Document bookmarkDetails);
	
	public Document bookmarks(String username);
	
	public List<Document> getAllBookmarks();
}
