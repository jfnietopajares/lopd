package com.hnss.excepciones;

/**
 * The Class SistemaException. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class SistemaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8290422052061279746L;

	/**
	 * Instantiates a new sistema exception.
	 */
	public SistemaException() {
	}

	/**
	 * Instantiates a new sistema exception.
	 *
	 * @param message the message
	 */
	public SistemaException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new sistema exception.
	 *
	 * @param cause the cause
	 */
	public SistemaException(Throwable cause) {
		super(cause);
	}

	/**
	 * Instantiates a new sistema exception.
	 *
	 * @param message the message
	 * @param cause   the cause
	 */
	public SistemaException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Instantiates a new sistema exception.
	 *
	 * @param message            the message
	 * @param cause              the cause
	 * @param enableSuppression  the enable suppression
	 * @param writableStackTrace the writable stack trace
	 */
	public SistemaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
