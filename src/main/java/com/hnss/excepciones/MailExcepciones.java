package com.hnss.excepciones;

/**
 * The Class MailExcepciones. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class MailExcepciones extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3276137199199312949L;

	public MailExcepciones() {
	}

	public MailExcepciones(String message) {
		super(message);
	}

	public MailExcepciones(Throwable cause) {
		super(cause);
	}

	public MailExcepciones(String message, Throwable cause) {
		super(message, cause);
	}

	public MailExcepciones(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
