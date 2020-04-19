package com.hnss.entidades.lopd;

import java.time.LocalDate;

import com.hnss.entidades.Usuario;

public class LopdTipos {
	private Long id;
	private String descripcion;
	private LopdSujeto sujeto; // 1 paciente, 2 trabajador, 3 usuario, 4 otro
	private Boolean mailReponsable;
	private boolean estado;
	private Usuario usucambio;
	private LocalDate fechaCambio;

	public LopdTipos() {
		this.id = new Long(0);
	}

	public LopdTipos(Long id, String descripcion, LopdSujeto sujeto, boolean mail) {
		this.id = id;
		this.descripcion = descripcion;
		this.sujeto = sujeto;
		this.mailReponsable = mail;

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

	public LopdSujeto getSujeto() {
		return sujeto;
	}

	public String getSujetoDescripcion() {
		if (sujeto != null)
			return sujeto.getDescripcion();
		else
			return "";
	}

	public void setSujeto(LopdSujeto sujeto) {
		this.sujeto = sujeto;
	}

	public Boolean getMailReponsable() {
		return mailReponsable;
	}

	public void setMailReponsable(Boolean mailReponsable) {
		this.mailReponsable = mailReponsable;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Usuario getUsucambio() {
		return usucambio;
	}

	public void setUsucambio(Usuario usucambio) {
		this.usucambio = usucambio;
	}

	public LocalDate getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDate fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

}
