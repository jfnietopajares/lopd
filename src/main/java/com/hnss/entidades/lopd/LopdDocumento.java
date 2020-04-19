package com.hnss.entidades.lopd;

import java.io.File;
import java.time.LocalDate;

import com.hnss.entidades.Usuario;
import com.hnss.utilidades.Constantes;

public class LopdDocumento {
	private Long id;
	private LopdIncidencia idIncidenica;
	private File ficheroAdjunto;
	private LocalDate fecha;
	private String hora;
	private Usuario usuCambio;
	private LocalDate fechaCambio;
	private int estado;

	public LopdDocumento() {
		this.id = new Long(0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LopdIncidencia getIdIncidenica() {
		return idIncidenica;
	}

	public void setIdIncidenica(LopdIncidencia idIncidenica) {
		this.idIncidenica = idIncidenica;
	}

	public File getFicheroAdjunto() {
		return ficheroAdjunto;
	}

	public void setFicheroAdjunto(File ficheroAdjunto) {
		this.ficheroAdjunto = ficheroAdjunto;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public Usuario getUsuCambio() {
		return usuCambio;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public void setUsuCambio(Usuario usuCambio) {
		this.usuCambio = usuCambio;
	}

	public LocalDate getFechaCambio() {
		return fechaCambio;
	}

	public void setFechaCambio(LocalDate fechaCambio) {
		this.fechaCambio = fechaCambio;
	}

	public String getPathFilePdf() {
		return Constantes.DIRECTORIOREPORTS + "documento_" + this.id + ".pdf";
	}
}
