package com.example.demo.Exception;

public class AccountException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AccountException(String message) {
		super(message);
	}

	public AccountException(String message, Exception e) {
		super(message, e);
	}

}
