package com.weather.app.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
//@ControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(InactiveUserException.class)
	public ResponseEntity<ErrorMessage> userLocked(InactiveUserException iue) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.FORBIDDEN.value()).message(iue.getMessage())
				.description("iue Global exception catch").build();
		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(PasswordNotMatchException.class)
	public ResponseEntity<ErrorMessage> passwordChangeNotMatchException(PasswordNotMatchException pnme) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.BAD_REQUEST.value())
				.message(pnme.getMessage()).description("PNME Global exception catch").build();
		return ResponseEntity.badRequest().body(message);
	}

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ErrorMessage> resourceNotFoundException(ResourceNotFoundException nfe) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.NOT_FOUND.value()).message(nfe.getMessage())
				.description("Global exception catch").build();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(LoginUserAlreadyAvailable.class)
	public ResponseEntity<ErrorMessage> resourceAlreadyAvailableException(LoginUserAlreadyAvailable lua) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.CONFLICT.value()).message(lua.getMessage())
				.description("Global exception catch").build();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.CONFLICT);
	}
	
	@ExceptionHandler(JWTTokenException.class)
	public ResponseEntity<ErrorMessage> jwtToken(JWTTokenException jwt) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).message(jwt.getMessage())
				.description("Global exception catch").build();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
	}
	
	@ExceptionHandler(NoTokenException.class)
    public ResponseEntity<Object> handleGlobalException(NoTokenException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token is not available.");
    }
	
	@ExceptionHandler(UserAuthenticationException.class)
    public ResponseEntity<ErrorMessage> apiGateWayOnlyAllowed(UserAuthenticationException ex) {
		ErrorMessage message = ErrorMessage.builder().statusCode(HttpStatus.UNAUTHORIZED.value()).message(ex.getMessage())
				.description("Global exception catch").build();
		return new ResponseEntity<ErrorMessage>(message, HttpStatus.UNAUTHORIZED);
    }

}
