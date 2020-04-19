package com.hnss.entidades.lopd;

import java.util.ArrayList;

public class LopdSujeto {
	private int id;
	private String descripcion;

	public static int PACIENTE = 1;
	public static int TRABAJADOR = 2;
	public static int OTROS = 3;
	public static int USUARIO = 4;

	public static LopdSujeto SUJETO_PACIENTE = new LopdSujeto(PACIENTE, "Paciente");
	public static LopdSujeto SUJETO_TRABAJADOR = new LopdSujeto(TRABAJADOR, "Trabajador");
	public static LopdSujeto SUJETO_OTROS = new LopdSujeto(OTROS, "Otros");
	public static LopdSujeto SUJETO_USUARIO = new LopdSujeto(USUARIO, "Usuario");

	@SuppressWarnings("serial")
	public static ArrayList<LopdSujeto> LISTASUJETOS_COMPLETA = new ArrayList<LopdSujeto>() {
		{
			add(SUJETO_PACIENTE);
			add(SUJETO_TRABAJADOR);
			add(SUJETO_OTROS);
			add(SUJETO_USUARIO);
		}
	};
	@SuppressWarnings("serial")
	public static ArrayList<LopdSujeto> LISTASUJETOS_SINUSUARIOS = new ArrayList<LopdSujeto>() {
		{
			add(SUJETO_PACIENTE);
			add(SUJETO_TRABAJADOR);
			add(SUJETO_OTROS);
		}
	};

	public LopdSujeto(int i, String descripcion) {
		this.id = i;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public static String getDescripcionFromTipo(int tipo) {
		if (tipo == PACIENTE)
			return "Pacinte";
		else if (tipo == TRABAJADOR)
			return "Trabajador";
		else if (tipo == OTROS)
			return "Otros";
		else if (tipo == USUARIO)
			return "Usuario";
		else
			return "";
	}

	public static LopdSujeto getTipoSujeto(LopdTipos tipo) {
		switch (tipo.getSujeto().getId()) {
		case 1:
			return LopdSujeto.SUJETO_PACIENTE;
		case 2:
			return LopdSujeto.SUJETO_TRABAJADOR;
		case 3:
			return LopdSujeto.SUJETO_OTROS;
		case 4:
			return LopdSujeto.SUJETO_USUARIO;
		}
		return null;
	}

	@Override
	public String toString() {
		return "LopdSujeto [id=" + id + ", descripcion=" + descripcion + "]";
	}

}
