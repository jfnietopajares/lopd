package com.hnss.ui;

import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;

/**
 * The Class VentanaHtml.
 */
public class VentanaHtml extends Window {

	private static final long serialVersionUID = 8296963606487374219L;

	private UI ui;
	private Label texto = new Label("<hr>");
	private String nombre = "";
	private VerticalLayout vtLayout = new VerticalLayout();

	/**
	 * Instantiates a new ventana html.
	 *
	 * @param ui     the ui
	 * @param texto  the texto
	 * @param nombre the nombre
	 */
	public VentanaHtml(UI ui, Label texto, String nombre) {
		this.ui = ui;
		this.texto = texto;
		this.nombre = nombre;
		dohazVentana();
	}

	/**
	 * Instantiates a new ventana html.
	 *
	 * @param ui    the ui
	 * @param texto the texto
	 */
	public VentanaHtml(UI ui, Label texto) {
		this.ui = ui;
		this.texto = texto;
		dohazVentana();
	}

	public VentanaHtml(UI ui, VerticalLayout vt) {
		this.ui = ui;
		vtLayout = vt;
		Window subWindow = new Window();
		subWindow.setWidth(ui.getWidth() * 2 / 3, vt.getWidthUnits());
		// subWindow.setWidth("440px");
		subWindow.center();
		subWindow.setModal(true);
		subWindow.setHeightUndefined();
		subWindow.setClosable(false);
		subWindow.setSizeUndefined();
		subWindow.setContent(vt);
		ui.addWindow(subWindow);
	}

	/**
	 * Dohaz ventana.
	 */
	public void dohazVentana() {
		// Crea una ventana modal
		Window subWindow = new Window(nombre);
		// subWindow.setWidth("640px");
		subWindow.setWidth(ui.getWidth() * 2 / 3, ui.getWidthUnits());
		subWindow.center();
		subWindow.setModal(true);
		subWindow.isResizeLazy();

		// Crea un layout y lo añade a la ventana
		FormLayout subContent = new FormLayout();
		subContent.setMargin(true);
		// subContent.setWidth("630px");
		subContent.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		subWindow.setContent(subContent);
		texto.setContentMode(ContentMode.HTML);
		texto.setSizeFull();
		subContent.addComponents(texto);
		// Abrir en el UI de la aplicación
		ui.addWindow(subWindow);
	}

}
