package com.hnss.excepciones;

/**
 * The Class LoginException. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class LoginException extends Exception {

	/**
	 * Instantiates a new login exception.
	 */
	public LoginException() {
	}

	/**
	 * Instantiates a new login exception.
	 *
	 * @param message the message
	 */
	public LoginException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new login exception.
	 *
	 * @param cause the cause
	 */
	public LoginException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new login exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public LoginException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new login exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public LoginException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
