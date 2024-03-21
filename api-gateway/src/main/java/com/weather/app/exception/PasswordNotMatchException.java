package com.weather.app.exception;
public class PasswordNotMatchException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PasswordNotMatchException(String message) {
		super(message);
	}
}
