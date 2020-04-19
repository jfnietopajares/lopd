package com.hnss.entidades;

import java.time.LocalDate;

public class Servicio {

	private Long id;
	private String codigo;
	private Long idjimena;
	private String descripcion;
	private Boolean asistencial;
	private int estado;
	private LocalDate fechacambio;
	private Long usucambio;

	public Servicio() {
		this.id = new Long(0);
		this.descripcion = "";
		this.codigo = "";
		this.idjimena = new Long(0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdjimena() {
		return idjimena;
	}

	public void setIdjimena(Long idjimena) {
		this.idjimena = idjimena;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Boolean getAsistencial() {
		return asistencial;
	}

	public void setAsistencial(Boolean asistencial) {
		this.asistencial = asistencial;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public LocalDate getFechacambio() {
		return fechacambio;
	}

	public void setFechacambio(LocalDate fechacambio) {
		this.fechacambio = fechacambio;
	}

	public Long getUsucambio() {
		return usucambio;
	}

	public void setUsucambio(Long usucambio) {
		this.usucambio = usucambio;
	}

}
