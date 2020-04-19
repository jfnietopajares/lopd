package com.hnss.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Page;
import com.vaadin.shared.Position;
import com.vaadin.ui.Notification;

/**
 * The Class NotificacionInfo.
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class Notificaciones extends Notification {

	public static final String LONGIN_USUARIO_NO_ENCONTRADO = "Usuario no encontrado.";

	public static final String LONGIN_USUARIO_NO_ACTIVO = "Usuario dasactivado.";

	public static final String CAMBIO_CLAVE_NO_COINCIDEN = "Las claves son distintas";

	public static final String SQLREGISTROBORRADO = " Se ha borrado el dato   ";

	public static final String SQLREGISTROSERRORBORRADO = " No se puede borrar el  dato tiene registros asociados   ";

	public static final String SQLREFERENCIASEXTERNAS = "Registro con referencias foráneas.  ";

	public static final String SQLERRORRESULSET = "Error resulset to registros  ";

	public static final String FORMULARIOCAMPOREQUERIDO = "En necesario registrar valor en este campo ";

	public static final String FORMULARIOFECHAMENOR = "El valor no puede ser menor de  ";

	public final static String ERROR_UI = "Error UI ";

	public final static String EXCEPTION_ERROR = "Excepción general.";
	public final static String EXCEPTION_IO = "IO Excepción.";
	public final static String EXCEPTION_FILENOTFOUND = "Fichero no encontrado.";

	public static final String PDFERROR = "Error pdf  ";

	public static final String BINDER_DATOS_NOVALIDOS = "Datos no válidos";

	public static final String BINDER_DATOS_ERRORVALIDACION = "Error de validación.";

	public static final String FORMULARIO_DATOS_GUARDADOS = "Datos almacenados.";

	public static final String FORMULARIO_DATOS_ERROR_GUARDADOS = "Erro almacenado datos.";

	public static final String FORMULARIO_DATO_BORRADO = "Dato borrado.";

	public static final String FORMULARIO_ERROR_DATO_BORRADO = "Error borrando dato.";

	public static final String FORMULARIO_DATO_DE_BUSQUEDA_NECESARIO = "Es necesario algún dato de búsqueda.";

	public static final String FORMULARIO_TF_EXISTENTE = "Ese teléfono ya está registrado en al base de datos.";

	public static final String GRID_DATO_NO_RECUPERADO = "Error en la recuperacion del dato.";

	public static final String GRID_PACIENTE_SIN_PROCESO = "El paciente no tiene proceso.";

	public static final String GRID_SIN_ELEGIR_DATO = "Dato no seleccionado.";

	public static final String GRID_DATOS_NO_ENCONTRADOS_FILTRO = "No hay datos para esos criterios.";

	public static final String FORMULARIONOHAYDATOSPARACRITERIO = "No se encuentran datos para esecriterio de búsqueda.  ";

	public static final String FIND_PACIENTE_NHC_NO_ENCONTRADO = "El paciente no encontrado. NHC: ";

	public static final String FIND_DATO_DE_BUSQUEDA_NO_VALIDO = "Dato de búsqueda no válido. ";

	public static final String FIND_PACIENTE_DNI_NO_ENCONTRADO = "El paciente no encontrado. DNI: ";

	public static final String FIND_PACIENTE_APELLIDOS_NO_ENCONTRADO = "El paciente no encontrado. APELLIDOS: ";

	public static final String PROCESO_ERROR_SUBAMBITO = "Sin formulario destino. Subambito != ";

	public static final String PROCESO_PACIENTE_PROCESO_ACTIVO = "Paciente con proceso activo.";

	public static final String PROCESO_PACIENTE_SIN_PROCESO_ACTIVO = "Paciente sin proceso activo.";

	public static final String PROCESO_PACIENTE_SIN_ELEIGR_PROCESO = "Sin proceso seleccionado.";

	public static final String BBDD_REGISTRO_PADRE_NULL = "Registro padre es nulo.";

	public static final String BBDD_REGISTRO_TIPO_NOVALIDO = "Tipo de registro no válido.";

	public static final String PACIENTE_NO_ENCONTRADO = "Paciente no encontrado.";

	public static final String PACIENTE_REPETIDO_NHC = "Ya existe un paciente ese número historia   ";

	public static final String PACIENTE_REPETIDO_DNI = "Ya existe un paciente con  es DNI ";

	public static final String ERROR_PACIENTE_ES_NUlO = "El paciente es nulo o no válido (0).";

	public static final String ERROR_PROCESO_ES_NULO = "El proceso es nulo o no válido (0).";

	public static final String ERROR_REGISTRO_ES_NULO = "El registro  es nulo.";

	public static final String ERRORTIPOFICHERONOVALIDO = "El tipo de fichero no es válido, debe ser pdf.";

	public final static String AVISO_DATO_NO_VALIDO = "Este dato no es válido ";

	public final static String MSG_EXCEPTION = "Error excepcion:";

	public final static String FORMULARIO_DATO_REPETIDO = "Ya existe un dato con ese valor";

	private static final long serialVersionUID = -646978632729992305L;

	public static final String DATO_BORRADO = " Dato borrado ";

	public static final String DATO_GRABADO = " Dato grabado ";

	public final static String DATO_ERROR = " Hay un error al registrar los datos en la base de datos";

	/**
	 * Instantiates a new notificacion info.
	 *
	 * @param caption the caption
	 */
	public Notificaciones(String caption) {
		super(caption);
		this.setPosition(Position.BOTTOM_CENTER);
		this.show(Page.getCurrent());
		this.setDelayMsec(80);
	}

	/**
	 * Instantiates a new notificacion info.
	 *
	 * @param caption the caption
	 * @param error   the error
	 */
	public Notificaciones(String caption, boolean error) {
		super(caption, Notification.TYPE_ERROR_MESSAGE);
		this.show(Page.getCurrent());
		this.setIcon(VaadinIcons.THUMBS_DOWN);
		this.setDelayMsec(-1);
		this.setPosition(Position.MIDDLE_CENTER);

	}

}
