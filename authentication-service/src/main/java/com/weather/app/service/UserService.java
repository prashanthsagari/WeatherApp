package com.weather.app.service;

import java.util.List;

import org.bson.Document;

public interface UserService {

	public Document registerUserUpsert(Document userDocument);
	
	public boolean deleteUser(String userName);
	
	public List<Document> getAllUsers();
	
	public Document getUser(String userName);
	
	public Document login(Document userDocument);
	
	public String actOrDeactUser(String userName, String status);
}
