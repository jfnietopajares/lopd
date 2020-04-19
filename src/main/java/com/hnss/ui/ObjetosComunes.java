package com.hnss.ui;

import java.util.ArrayList;

import com.hnss.dao.ServicioDAO;
import com.hnss.entidades.Servicio;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.Resource;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The Class ObjetosComunes.
 * 
 * *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class ObjetosComunes {

	public static String NOMBRE_BOTON_MAMA = "MAMA";

	public static String NOMBRE_BOTON_COLON = "COLON";

	public static String NOMBRE_BOTON_PALIATIVOS = "PALIATIVOS";

	public static String NOMBRE_BOTON_OXIGENOTERAPIA = "OXIGENO";

	public static String NOMBRE_BOTON_PARTOS = "PARTOS";

	public static String NOMBRE_BOTON_HDIAMED = "H.D.MÉDICO";

	/**
	 * Instantiates a new objetos comunes.
	 */
	public ObjetosComunes() {

	}

	/**
	 * Gets the boton conectar.
	 *
	 * @return the boton conectar
	 */
	public Button getBoton(String captacion, String descripcion, String ancho, Resource icono) {
		Button boton = new Button();
		boton.setIcon(icono);
		boton.setWidthUndefined();
		boton.setHeight(ancho);
		boton.setDescription(descripcion);
		boton.setCaption(captacion);
		boton.setHeight("30px");
		return boton;
	}

	/**
	 * Gets the fecha.
	 *
	 * @param textocap   the textocap
	 * @param textoplace the textoplace
	 * @return the fecha
	 */
	// un campo tipo fecha
	public DateField getFecha(String textocap, String textoplace) {
		DateField campo = new DateField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setDateFormat("dd/MM/yyyy");
		campo.setIcon(VaadinIcons.CALENDAR_USER);
		campo.setWidth("150px");
		return campo;
	}

	public DateTimeField getFechaHora(String textocap, String textoplace) {
		DateTimeField campo = new DateTimeField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setDateFormat("dd/MM/yyyy HH:mm");
		campo.setIcon(VaadinIcons.CALENDAR_USER);
		campo.setWidth("250px");
		return campo;
	}

	public TextField getHora(String textocap, String textoplace) {
		TextField campo = new TextField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setIcon(VaadinIcons.CLOCK);
		campo.setWidth("65px");
		campo.setMaxLength(5);
		return campo;
	}

	/**
	 * Gets the userid.
	 *
	 * @return the userid
	 */
	// user id
	public TextField getUserid() {
		TextField userid = new TextField();
		userid.setCaption("Usuario");
		userid.setPlaceholder(" id usuario ");
		userid.setIcon(VaadinIcons.USER);
		userid.setWidth("100px");
		userid.setMaxLength(10);
		return userid;
	}

	/**
	 * Gets the ape nombre.
	 *
	 * @param textocap   the textocap
	 * @param textoplace the textoplace
	 * @return the ape nombre
	 */
	// nombre o apellidos
	public TextField getApeNombre(String textocap, String textoplace) {
		TextField campo = new TextField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		// campo.setIcon(VaadinIcons.USER);
		campo.setWidth("180px");
		campo.setMaxLength(30);
		return campo;
	}

	/**
	 * Gets the dni.
	 *
	 * @param textocap   the textocap
	 * @param textoplace the textoplace
	 * @return the dni
	 */
	// dni
	public TextField getDni(String textocap, String textoplace) {
		TextField campo = new TextField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setMaxLength(9);
		campo.setWidth("110px");
		campo.setIcon(VaadinIcons.USER_CARD);
		return campo;
	}

	/**
	 * Gets the numerohc.
	 *
	 * @param textocap   the textocap
	 * @param textoplace the textoplace
	 * @return the numerohc
	 */
	// numero historia
	public TextField getNumerohc(String textocap, String textoplace) {
		TextField campo = new TextField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setMaxLength(9);
		campo.setWidth("90px");
		campo.setIcon(VaadinIcons.HEALTH_CARD);
		return campo;
	}

	/**
	 * Gets the mail.
	 *
	 * @return the mail
	 */
	// mail
	public TextField getMail(String texto) {
		TextField campo = new TextField();
		campo.setCaption(texto);
		campo.setPlaceholder(" mail ");
		campo.setIcon(VaadinIcons.MAILBOX);
		campo.setWidth("300px");
		return campo;
	}

	/**
	 * Gets the telefono.
	 *
	 * @param textocap   the textocap
	 * @param textoplace the textoplace
	 * @return the telefono
	 */
	// teléfono
	public TextField getTelefono(String textocap, String textoplace) {
		TextField campo = new TextField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setMaxLength(12);
		campo.setWidth("95px");
		campo.setMaxLength(9);
		campo.setIcon(VaadinIcons.PHONE_LANDLINE);
		return campo;
	}

	/**
	 * Gets the movil.
	 *
	 * @param textocap   the textocap
	 * @param textoplace the textoplace
	 * @return the movil
	 */
	// movil
	public TextField getMovil(String textocap, String textoplace) {
		TextField campo = new TextField();
		campo.setCaption(textocap);
		campo.setPlaceholder(textoplace);
		campo.setMaxLength(12);
		campo.setWidth("95px");
		campo.setIcon(VaadinIcons.MOBILE);
		return campo;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	// passwd
	public PasswordField getPassword(String captacion, String placehol) {
		PasswordField campo = new PasswordField();
		campo.setCaption(captacion);
		campo.setPlaceholder(placehol);
		campo.setIcon(VaadinIcons.KEY);
		campo.setWidth("110px");
		campo.setMaxLength(60);
		return campo;
	}

	public ComboBox<Servicio> getComboServicio(Servicio servicio, Servicio defecto) {
		ComboBox<Servicio> combo = new ComboBox<Servicio>();
		combo.setCaption("Serv./Unidad");
		ArrayList<Servicio> lista = new ServicioDAO().getListaServicios();
		combo.setItems(lista);
		combo.setItemCaptionGenerator(Servicio::getDescripcion);
		// combo.setWidth("200px");
		// combo.setItemCaptionGenerator(Servicio::getDescripcion);
		if (servicio != null) {
			combo.setSelectedItem(servicio);
		} else if (lista.size() == 1) {
			combo.setSelectedItem(lista.get(0));
		} else if (defecto != null) {
			combo.setSelectedItem(defecto);
		} else {

		}

		combo.setEmptySelectionAllowed(true);
		return combo;
	}

	public ComboBox<String> getComoSiNo(String captacion, String valorDefecto, String valoractual) {
		@SuppressWarnings("serial")
		ArrayList<String> listaTipos = new ArrayList<String>() {
			{
				add("");
				add("Si");
				add("No");

			}
		};
		ComboBox<String> combo = new ComboBox<>();
		combo.setCaption(captacion);
		combo.setItems(listaTipos);
		combo.setWidth("80px");
		combo.setEmptySelectionAllowed(true);
		if (valoractual != null)
			combo.setSelectedItem(valoractual);
		else if (valorDefecto != null)
			combo.setSelectedItem(valorDefecto);
		else if (listaTipos.size() == 1)
			combo.setSelectedItem(listaTipos.get(0));

		combo.setEmptySelectionAllowed(false);
		combo.addStyleName(ValoTheme.CHECKBOX_SMALL);
		return combo;
	}

}
