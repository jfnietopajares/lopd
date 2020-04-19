package com.hnss.entidades;

import java.time.LocalDate;

public class Funcionalidad {

	private Long id;
	private String descripcion;
	private String textomenu;
	private Boolean estado;
	private LocalDate fechacambio;
	private Usuario usucambio;
	private Boolean permitida;

	public static Funcionalidad PEDIRUSUARIO = new Funcionalidad(new Long(2));

	public Funcionalidad() {
		this.id = new Long(0);
	}

	public Funcionalidad(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTextomenu() {
		return textomenu;
	}

	public void setTextomenu(String textomenu) {
		this.textomenu = textomenu;
	}

	public Boolean getEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	public LocalDate getFechacambio() {
		return fechacambio;
	}

	public void setFechacambio(LocalDate fechacambio) {
		this.fechacambio = fechacambio;
	}

	public Usuario getUsucambio() {
		return usucambio;
	}

	public void setUsucambio(Usuario usucambio) {
		this.usucambio = usucambio;
	}

	public Boolean getPermitida() {
		return permitida;
	}

	public void setPermitida(Boolean permitida) {
		this.permitida = permitida;
	}

}
