package com.hnss.ui;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

/**
 * The Class Frm_Master. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public abstract class FrmMaster extends HorizontalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8375371913895439348L;
	protected HorizontalLayout contenedorBotones = new HorizontalLayout();
	protected VerticalLayout contenedorCampos = new VerticalLayout();
	protected FormLayout contenedorCamposLayout = new FormLayout();
	protected VerticalLayout contenedorGrid = new VerticalLayout();
	protected HorizontalLayout fila1 = new HorizontalLayout();
	protected HorizontalLayout fila2 = new HorizontalLayout();
	protected HorizontalLayout fila3 = new HorizontalLayout();
	protected HorizontalLayout fila4 = new HorizontalLayout();
	protected HorizontalLayout fila5 = new HorizontalLayout();
	protected HorizontalLayout fila6 = new HorizontalLayout();
	protected HorizontalLayout fila7 = new HorizontalLayout();
	protected HorizontalLayout fila8 = new HorizontalLayout();
	protected HorizontalLayout fila9 = new HorizontalLayout();
	protected HorizontalLayout fila10 = new HorizontalLayout();
	protected HorizontalLayout fila11 = new HorizontalLayout();
	protected HorizontalLayout fila12 = new HorizontalLayout();
	protected HorizontalLayout fila13 = new HorizontalLayout();
	protected HorizontalLayout fila14 = new HorizontalLayout();
	protected HorizontalLayout fila15 = new HorizontalLayout();
	protected HorizontalLayout fila16 = new HorizontalLayout();
	protected HorizontalLayout fila17 = new HorizontalLayout();
	protected HorizontalLayout fila18 = new HorizontalLayout();

	protected Button grabar, borrar, cerrar, limpia, ayuda;

	/**
	 * Instantiates a new frm master.
	 */
	public FrmMaster() {
		this.setMargin(false);
		this.addStyleName(ValoTheme.LAYOUT_CARD);
		this.setDefaultComponentAlignment(Alignment.TOP_LEFT);
		this.fila1.setMargin(false);
		this.fila2.setMargin(false);
		this.fila3.setMargin(false);
		this.fila4.setMargin(false);
		this.fila5.setMargin(false);
		this.fila6.setMargin(false);
		this.fila7.setMargin(false);
		this.fila8.setMargin(false);
		this.fila9.setMargin(false);
		this.fila10.setMargin(false);
		this.fila11.setMargin(false);
		this.fila12.setMargin(false);
		this.fila13.setMargin(false);
		this.fila14.setMargin(false);
		this.fila15.setMargin(false);
		this.fila16.setMargin(false);
		this.fila17.setMargin(false);
		this.fila18.setMargin(false);

		contenedorBotones.setMargin(false);
		contenedorBotones.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

		contenedorCamposLayout.setSpacing(true);
		contenedorCamposLayout.setMargin(false);
		contenedorCampos.setMargin(false);
		contenedorCampos.addStyleName(ValoTheme.LAYOUT_CARD);
		contenedorCampos.setDefaultComponentAlignment(Alignment.TOP_LEFT);

		contenedorGrid.setMargin(false);
		contenedorGrid.setDefaultComponentAlignment(Alignment.MIDDLE_LEFT);

		grabar = new ObjetosComunes().getBoton("Grabar", "", "40px", VaadinIcons.CHECK);
		grabar.addClickListener(event -> grabarClick());
		borrar = new ObjetosComunes().getBoton("Borrar", "", "40px", VaadinIcons.MINUS);
		borrar.addClickListener(event -> borrarClick());
		borrar.setEnabled(false);
		cerrar = new ObjetosComunes().getBoton("Cerrar", "", "40px", VaadinIcons.CLOSE);
		cerrar.addClickListener(event -> cerrarClick());
		ayuda = new ObjetosComunes().getBoton("Ayuda", "", "40px", VaadinIcons.QUESTION);
		ayuda.addClickListener(event -> ayudaClick());
		limpia = new ObjetosComunes().getBoton("Limpia", "", "40px", VaadinIcons.COMPILE);
		limpia.addClickListener(event -> limpiarClick());

		contenedorBotones.addComponents(grabar, limpia, borrar, ayuda, cerrar);
		contenedorCampos.addComponents(contenedorBotones);
		this.addComponents(contenedorCampos, contenedorGrid);

	}

	/**
	 * Do control botones.
	 *
	 * @param id the id
	 */
	public void doActivaBotones(Long id) {
		if (id > 0) {
			borrar.setEnabled(true);
		} else {
			borrar.setEnabled(false);
		}
	}

	public void doActivaBotones(Object unaEntidad) {

		if (unaEntidad != null) {
			borrar.setEnabled(true);
		} else {
			borrar.setEnabled(false);
		}
	}

	/**
	 * Cerrar click.
	 */
	public abstract void cerrarClick();

	public abstract void limpiarClick();

	/**
	 * Ayuda click.
	 */
	public abstract void ayudaClick();

	/**
	 * Grabar click.
	 */
	public abstract void grabarClick();

	/**
	 * Borrar click.
	 */
	public abstract void borrarClick();

	/**
	 * Borra el registro.
	 */
	public abstract void borraElRegistro();

	/**
	 * Do valida formulario.
	 *
	 * @return true, if successful
	 */
	public abstract boolean doValidaFormulario();

	/**
	 * Gets the contenedor botones.
	 *
	 * @return the contenedor botones
	 */
	public HorizontalLayout getContenedorBotones() {
		return contenedorBotones;
	}

	/**
	 * Sets the contenedor botones.
	 *
	 * @param contenedorBotones the new contenedor botones
	 */
	public void setContenedorBotones(HorizontalLayout contenedorBotones) {
		this.contenedorBotones = contenedorBotones;
	}

	/**
	 * Gets the grabar.
	 *
	 * @return the grabar
	 */
	public Button getGrabar() {
		return grabar;
	}

	/**
	 * Sets the grabar.
	 *
	 * @param grabar the new grabar
	 */
	public void setGrabar(Button grabar) {
		this.grabar = grabar;
	}

	/**
	 * Gets the borrar.
	 *
	 * @return the borrar
	 */
	public Button getBorrar() {
		return borrar;
	}

	/**
	 * Sets the borrar.
	 *
	 * @param borrar the new borrar
	 */
	public void setBorrar(Button borrar) {
		this.borrar = borrar;
	}

	/**
	 * Gets the cerrar.
	 *
	 * @return the cerrar
	 */
	public Button getCerrar() {
		return cerrar;
	}

	/**
	 * Sets the cerrar.
	 *
	 * @param cerrar the new cerrar
	 */
	public void setCerrar(Button cerrar) {
		this.cerrar = cerrar;
	}

	/**
	 * Gets the ayuda.
	 *
	 * @return the ayuda
	 */
	public Button getAyuda() {
		return ayuda;
	}

	/**
	 * Sets the ayuda.
	 *
	 * @param ayuda the new ayuda
	 */
	public void setAyuda(Button ayuda) {
		this.ayuda = ayuda;
	}

	public String getAyudaHtml() {
		return "";
	}
}
