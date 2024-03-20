package com.weather.app.service;

import org.bson.Document;

public interface UserService {

	public Document registerUser(Document userDocument);
	
	public Document login(Document userDocument);
	
	public String activateUser(String userName);
}
