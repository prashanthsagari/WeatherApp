package com.weather.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException nfe) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value()).message(nfe.getMessage())
				.description("Global exception catch").build();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(JWTTokenException.class)
	public ResponseEntity<ErrorMessage> jwtToken(JWTTokenException jwt) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.UNAUTHORIZED.value())
				.message(jwt.getMessage()).description("Global exception catch").build();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
	}

	@ExceptionHandler(NoTokenException.class)
	public ResponseEntity<Object> handleGlobalException(NoTokenException tokenException) {
		// Handle the exception and return an appropriate response
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(tokenException.getMessage());
	}

}
