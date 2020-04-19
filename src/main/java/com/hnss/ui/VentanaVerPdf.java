package com.hnss.ui;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.dao.LopdDocumentoDAO;
import com.hnss.utilidades.Constantes;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

/**
 * The Class VentanaVerPdf.
 * 
 */
public class VentanaVerPdf extends Window {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7429777564790824145L;

	private static final Logger logger = LogManager.getLogger(VentanaVerPdf.class);

	private Window subWindow = null;

	private FormLayout formulario = null;

	private UI ui = null;
	private Long id;
	private int tipo;

	public static final int TIPO_VERPDF_INFORME = 1;

	public VentanaVerPdf(UI ui, Long id, int tipo) {
		this.ui = ui;
		this.id = id;
		this.tipo = tipo;
		subWindow = new Window();
		subWindow.setWidth("800");
		subWindow.setHeight("660");
		subWindow.center();
		subWindow.setModal(true);
		subWindow.isResizeLazy();

		formulario = new FormLayout();
		formulario.setMargin(true);
		formulario.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		formulario.setSizeFull();
		subWindow.setContent(formulario);

		Embedded pdf = new Embedded("", new FileResource(getFichero()));
		pdf.setMimeType("application/pdf");
		pdf.setType(Embedded.TYPE_BROWSER);
		pdf.setWidth("750px");
		pdf.setHeight("610px");
		formulario.addComponent(pdf);
		ui.addWindow(subWindow);
	}

	public VentanaVerPdf(StreamResource fichero, UI ui) {
		// this.ui = this.getUI();
		subWindow = new Window();
		subWindow.setWidth("800");
		subWindow.setHeight("660");
		subWindow.center();
		subWindow.setModal(true);
		subWindow.isResizeLazy();

		formulario = new FormLayout();
		formulario.setMargin(true);
		formulario.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		formulario.setSizeFull();
		subWindow.setContent(formulario);

		Embedded pdf = new Embedded("", fichero);
		pdf.setMimeType("application/pdf");
		pdf.setType(Embedded.TYPE_BROWSER);
		pdf.setWidth("750px");
		pdf.setHeight("610px");
		formulario.addComponent(pdf);
		ui.addWindow(subWindow);
	}

	public VentanaVerPdf(File fichero, UI ui) {
		// this.ui = this.getUI();
		subWindow = new Window();
		subWindow.setWidth("800");
		subWindow.setHeight("660");
		subWindow.center();
		subWindow.setModal(true);
		subWindow.isResizeLazy();

		formulario = new FormLayout();
		formulario.setMargin(true);
		formulario.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		formulario.setSizeFull();
		subWindow.setContent(formulario);

		Embedded pdf = new Embedded("", new FileResource(fichero));
		pdf.setMimeType("application/pdf");
		pdf.setType(Embedded.TYPE_BROWSER);
		pdf.setWidth("750px");
		pdf.setHeight("610px");
		formulario.addComponent(pdf);
		ui.addWindow(subWindow);
	}

	public VentanaVerPdf(UI ui, String nombreFichero) {
		this.ui = ui;
		inicia();

		Embedded pdf = new Embedded("", new FileResource(new File(nombreFichero)));
		pdf.setMimeType("application/pdf");
		pdf.setType(Embedded.TYPE_BROWSER);
		pdf.setWidth("750px");
		pdf.setHeight("610px");
		formulario.addComponent(pdf);
		ui.addWindow(subWindow);
	}

	public void inicia() {
		// this.ui = this.getUI();
		subWindow = new Window();
		subWindow.setWidth("800");
		subWindow.setHeight("660");
		subWindow.center();
		subWindow.setModal(true);
		subWindow.isResizeLazy();

		formulario = new FormLayout();
		formulario.setMargin(true);
		formulario.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		formulario.setSizeFull();
		subWindow.setContent(formulario);
	}

	public VentanaVerPdf(String caption, Component content) {
		super(caption, content);
	}

	/**
	 * Gets the fichero. 1 recupera un informe por el id
	 * 
	 * @return the fichero
	 */
	public File getFichero() {
		File file = null;
		switch (tipo) {
		case VentanaVerPdf.TIPO_VERPDF_INFORME:
			try {
				FileOutputStream outpu = null;
				String pathname = Constantes.DIRECTORIOREPORTS + System.getProperty("file.separator") + "inf_" + id
						+ ".pdf";
				file = new File(pathname);
				outpu = new FileOutputStream(file);
				Blob archivo = new LopdDocumentoDAO().getBlobPdfId(id);
				InputStream inStream = archivo.getBinaryStream();
				int size = (int) archivo.length();
				byte[] buffer = new byte[size];
				int length = -1;
				while ((length = inStream.read(buffer)) != -1) {
					outpu.write(buffer, 0, length);
				}
				outpu.close();
			} catch (Exception ioe) {
				logger.error(ioe);
			}

			break;
		}
		return file;
	}
}
