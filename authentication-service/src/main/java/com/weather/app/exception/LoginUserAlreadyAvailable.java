package com.weather.app.exception;

public class LoginUserAlreadyAvailable extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginUserAlreadyAvailable(String msg) {
		super(msg);
	}
}
