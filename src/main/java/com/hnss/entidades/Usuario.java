package com.hnss.entidades;

import com.hnss.ui.Notificaciones;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import com.vaadin.ui.DateField;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class Usuario. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class Usuario implements Serializable {

	private static final long serialVersionUID = -5231586971597227517L;
        
         private static final Logger logger = LogManager.getLogger(Usuario.class);

	private Long id;

	private String dni;

	private String clave;

	private String apellido1;

	private String apellido2;

	private String nombre;

	private String mail;

	private String telefono;

//	private String password;

	private int estado;

	private DateField fechabaja;

	private Usuario usucambio;

	private boolean llamadaExterna;

	private Set<String> funcionalidadStrings = new HashSet<String>();

	private ArrayList<Funcionalidad> fucionalidadesArrayList = new ArrayList<Funcionalidad>();
	public static String PASSWORD_DEFECTO = "murallas";
	public static int USUARIO_DEBAJA = 0;
	public static int USUARIO_ACTIVO = 1;
	public static int USUARIO_ADMINISTRADOR = 2;

	/**
	 * Instantiates a new usuario.
	 */
	public Usuario() {
		this.id = new Long(0);
		this.estado = 0;
		this.llamadaExterna = false;
	}

	public Usuario(Long id) {
		this.id = id;
		this.llamadaExterna = false;
	}

	public Usuario(String dni) {
		this.dni = dni;
		this.llamadaExterna = false;
	}

	public Usuario(String dni, String clave) {
		this.dni = dni;
		this.clave = clave;
	}

	public Usuario(Long id, String dni, String clave) {
		this.id = id;
		this.dni = dni;
		this.clave = clave;
	}

	public Usuario(String dni, String apellidos, String mail) {
		this.dni = dni;
		this.setApellidosNombre(apellidos);
		this.mail = mail;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getApellido1() {
		return apellido1;
	}

	public void setApellido1(String apellido1) {
		this.apellido1 = apellido1;
	}

	public String getApellido2() {
		return apellido2;
	}

	public void setApellido2(String apellido2) {
		this.apellido2 = apellido2;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidosNombre() {
		String cadenaString = "";
		if (this.apellido1 != null)
			cadenaString = this.getApellido1();

		if (this.apellido2 != null)
			cadenaString = cadenaString.concat(" " + this.getApellido2());

		if (this.getNombre() != null)
			cadenaString = cadenaString.concat("," + this.getNombre());

		return cadenaString;
	}

	public void setApellidosNombre(String apellidosNombre) {
		String[] ape = apellidosNombre.split(" ");
		if (ape.length == 0) {
		} else if (ape.length == 1)
			this.setApellido1(ape[0]);
		else if (ape.length == 2) {
			this.setApellido1(ape[0]);
			this.setApellido2(ape[1]);
		} else if (ape.length == 3) {
			this.setApellido1(ape[0]);
			this.setApellido2(ape[1]);
			this.setNombre(ape[2]);
		} else {
			this.setApellido1(ape[0]);
			this.setApellido2(ape[1]);
			this.setNombre(ape[2]);
		}
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/*
	 * public String getPassword() { return password; }
	 * 
	 * public void setPassword(String password) { this.password = password; }
	 */

	public int getEstado() {
		return estado;
	}

	public void setEstado(int estado) {
		this.estado = estado;
	}

	public DateField getFechabaja() {
		return fechabaja;
	}

	public void setFechabaja(DateField fechabaja) {
		this.fechabaja = fechabaja;
	}

	public boolean isLlamadaExterna() {
		return llamadaExterna;
	}

	public void setLlamadaExterna(boolean llamadaExterna) {
		this.llamadaExterna = llamadaExterna;
	}

	public String getHtmlDatos() {
		String texto;
		String mail = getMail() != null ? getMail() : " ";
		texto = "<hr><b>Usuario:</b>" + getDni() + "<br>";
		texto = texto.concat("<b>Nombre:</b>" + getApellidosNombre() + "<br>");
		texto = texto.concat("<b>Mail:</b>" + getMail() + "<br>");
		texto = texto.concat("<b>Tef:</b>" + getTelefono() + "<br>");
		texto = texto.concat("<b>Estado:</b>" + getEstado() + "<br>");

		return texto;
	}

	public Usuario getUsucambio() {
		return usucambio;
	}

	public void setUsucambio(Usuario usucambio) {
		this.usucambio = usucambio;
	}

	public Set<String> getFuncionalidadStrings() {
		return funcionalidadStrings;
	}

	public void setFuncionalidadStrings(Set<String> funcionalidadStrings) {
		this.funcionalidadStrings = funcionalidadStrings;
	}

	public ArrayList<Funcionalidad> getFucionalidadesArrayList() {
		return fucionalidadesArrayList;
	}

	public void setFucionalidadesArrayList(ArrayList<Funcionalidad> fucionalidadesArrayList) {
		this.fucionalidadesArrayList = fucionalidadesArrayList;
	}

	public Boolean tieneLaFuncionalidad(Funcionalidad fun) {
		Boolean laTiene = false;
		for (Funcionalidad funcionaliad : this.getFucionalidadesArrayList()) {
			if (funcionaliad.getId().equals(fun.getId()))
				laTiene = true;
		}
		return laTiene;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", dni=" + dni + ", clave=" + clave + ", apellido1=" + apellido1 + ", apellido2="
				+ apellido2 + ", nombre=" + nombre + ", mail=" + mail + ", telefono=" + telefono + ", estado=" + estado
				+ ", fechabaja=" + fechabaja + ", usucambio=" + usucambio + ", llamadaExterna=" + llamadaExterna + "]";
	}

        
         public static Usuario getUsuairoResulSetJimena(ResultSet resulSet, Usuario usuarioParam) {
        Usuario usuario = null;
        if (usuarioParam != null) {
            usuario = usuarioParam;
        } else {
            try {
                usuario = new Usuario();
                usuario.setDni(resulSet.getString("usuuserid"));
                usuario.setApellido1(resulSet.getString("usuapellido1"));
                usuario.setApellido2(resulSet.getString("usuapellido2"));
                usuario.setNombre(resulSet.getString("usunombre"));
                usuario.setEstado(resulSet.getInt("usuestado"));
            } catch (SQLException e) {
                logger.error(Notificaciones.SQLERRORRESULSET, e);
            }
        }
        return usuario;
    }
}
