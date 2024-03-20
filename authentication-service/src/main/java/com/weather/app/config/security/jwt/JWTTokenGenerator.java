package com.weather.app.config.security.jwt;

import org.bson.Document;

public interface JWTTokenGenerator {

	Document generateJWTToken(Document user);
}
