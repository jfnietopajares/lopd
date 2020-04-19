package com.hnss.excepciones;

/**
 * The Class MensajesExcepciones. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class MensajesExcepciones extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5109746124590509860L;

	public MensajesExcepciones() {
	}

	public MensajesExcepciones(String message) {
		super(message);
	}

	public MensajesExcepciones(Throwable cause) {
		super(cause);
	}

	public MensajesExcepciones(String message, Throwable cause) {
		super(message, cause);
	}

	public MensajesExcepciones(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
