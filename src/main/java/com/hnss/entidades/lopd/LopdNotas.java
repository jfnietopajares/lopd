package com.hnss.entidades.lopd;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.hnss.entidades.Usuario;
import com.hnss.utilidades.Utilidades;

public class LopdNotas {
	private Long id;
	private Long idIncidenciaLong;
	private LocalDate fecha;
	private Integer hora;
	private String descripcion;
	private Usuario usucambio;
	private int estado;

	public LopdNotas() {
		this.id = new Long(0);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setId(String id) {
		if (Utilidades.isNumeric(id)) {
			this.id = Long.parseLong(id);
		}
	}

	public String getIdString() {
		return Long.toString(this.id);
	}

	public Long getIdIncidenciaLong() {
		return idIncidenciaLong;
	}

	public void setIdIncidenciaLong(Long idIncidenciaLong) {
		this.idIncidenciaLong = idIncidenciaLong;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public Integer getHora() {
		return hora;
	}

	public void setHora(Integer hora) {
		this.hora = hora;
	}

	public String getFechaHoraFormato() {
		DateTimeFormatter fechaformato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		if (this.fecha != null)
			return fechaformato.format(this.fecha);
		else
			return "";
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Usuario getUsucambio() {
		return usucambio;
	}

	public void setUsucambio(Usuario usucambio) {
		this.usucambio = usucambio;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

}
