package com.weather.app.exception;

public class UserAuthenticationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserAuthenticationException(String msg) {
		super(msg);
	}
}
