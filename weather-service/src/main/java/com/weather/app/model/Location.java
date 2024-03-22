package com.weather.app.model;

import org.springframework.stereotype.Component;

@Component
public record Location(String name, float latitude, float longitude, String country, String state) {
	
	
	public Location() {		
		this(null, 0.0f, 0.0f, null, null);
	}
}
