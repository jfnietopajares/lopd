package com.hnss.entidades;

/**
 * The Class ParametroConfiguracion. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class ParametProperties {

	/** The nombre. */
	public String nombre;

	/** The valor. */
	public String valor;

	/**
	 * Instantiates a new parametro configuracion.
	 */
	public ParametProperties() {
	}

	/**
	 * Instantiates a new parametro configuracion.
	 *
	 * @param nombre the nombre
	 * @param valor  the valor
	 */
	public ParametProperties(String nombre, String valor) {
		this.setNombre(nombre);
		this.setValor(valor);
	}

	/**
	 * Gets the nombre.
	 *
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * Sets the nombre.
	 *
	 * @param nombre the new nombre
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * Gets the valor.
	 *
	 * @return the valor
	 */
	public String getValor() {
		return valor;
	}

	/**
	 * Sets the valor.
	 *
	 * @param valor the new valor
	 */
	public void setValor(String valor) {
		this.valor = valor;
	}

}