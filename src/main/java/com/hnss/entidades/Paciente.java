package com.hnss.entidades;

import java.time.LocalDate;

public class Paciente {

	private Long id;
	private String apellidosnombre;
	private Long idJimena;
	private String numerohc;
	private int estado;
	private Usuario usucambio;
	private LocalDate fechacambio;

	public Paciente() {
	}

	public Paciente(String numerohc) {
		this.numerohc = numerohc;
	}

	public Paciente(String numerohc, String apellidosnombre) {
		this.numerohc = numerohc;
		this.apellidosnombre = apellidosnombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getApellidosnombre() {
		if (this.apellidosnombre == null)
			return "";
		else if (this.apellidosnombre.isEmpty())
			return "";
		else
			return apellidosnombre;
	}

	public void setApellidosnombre(String apellidosnombre) {

		this.apellidosnombre = apellidosnombre;
	}

	public Long getIdJimena() {
		return idJimena;
	}

	public void setIdJimena(Long idJimena) {
		this.idJimena = idJimena;
	}

	public String getNumerohc() {
		return numerohc;
	}

	public void setNumerohc(String numerohc) {
		this.numerohc = numerohc;
	}

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public Usuario getUsucambio() {
		return usucambio;
	}

	public void setUsucambio(Usuario usucambio) {
		this.usucambio = usucambio;
	}

	public LocalDate getFechacambio() {
		return fechacambio;
	}

	public void setFechacambio(LocalDate fechacambio) {
		this.fechacambio = fechacambio;
	}

}
