package com.hnss.excepciones;

/**
 * The Class UsuarioBajaException. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class UsuarioBajaException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6946564613668920119L;

	public UsuarioBajaException() {
	}

	public UsuarioBajaException(String message) {
		super(message);
	}

	public UsuarioBajaException(Throwable cause) {
		super(cause);
	}

	public UsuarioBajaException(String message, Throwable cause) {
		super(message, cause);
	}

	public UsuarioBajaException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
