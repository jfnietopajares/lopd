package com.hnss.excepciones;

/**
 * The Class PasswordException. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class PasswordException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6704407782785918421L;

	public PasswordException() {
	}

	public PasswordException(String message) {
		super(message);
	}

	public PasswordException(Throwable cause) {
		super(cause);
	}

	public PasswordException(String message, Throwable cause) {
		super(message, cause);
	}

	public PasswordException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
