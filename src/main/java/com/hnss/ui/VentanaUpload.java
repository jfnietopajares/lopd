package com.hnss.ui;

import java.io.File;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.controlador.Uploaderr;
import com.hnss.dao.LopdDocumentoDAO;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdDocumento;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Upload.FailedEvent;
import com.vaadin.ui.Upload.FailedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;
import com.vaadin.ui.Window;

/**
 * The Class VentanaUpload.
 */
public class VentanaUpload extends Window implements SucceededListener, FailedListener, ProgressListener {

	private static final long serialVersionUID = 6992582432390213459L;

	private static final Logger logger = LogManager.getLogger(VentanaUpload.class);

	public static final String TIPOPDF = "application/pdf";

	private UI ui;

	private Window subWindow;

	private FormLayout contenedorVentada = new FormLayout();
	private Label texto = new Label();

	private String nombre = "Adjuntar ficheros";

	private LopdIncidencia incidencia;

	Upload upload1;
	Uploaderr receiver;
	Upload upload;
	String filenameString;

	public VentanaUpload(UI ui, LopdIncidencia incidencia) {
		this.incidencia = incidencia;
		this.ui = ui;

		// Crea una ventana modal
		subWindow = new Window(nombre);
		subWindow.setWidth("800px");
		subWindow.setHeight("600px");
		subWindow.center();
		subWindow.setModal(true);
		subWindow.isResizeLazy();

		// Crea un layout y lo añade a la ventana
		contenedorVentada = new FormLayout();
		contenedorVentada.setMargin(true);
		contenedorVentada.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		subWindow.setContent(contenedorVentada);
		texto.setCaption(incidencia.getId() + incidencia.getDescripcionError());
		contenedorVentada.addComponents(texto);
		// Abrir en el UI de la aplicación
		ui.addWindow(subWindow);
		// contenedorVentada.addComponent(new PanelPaciente(proceso.getPaciente()));

		receiver = new Uploaderr();

		upload = new Upload(null, receiver);
		upload.setImmediateMode(true);
		upload.setButtonCaption("Selecciona fichero");
		upload.setIcon(VaadinIcons.UPLOAD);
		upload.addProgressListener(this);
		upload.addSucceededListener(this);
		upload.addFailedListener(this);
		contenedorVentada.addComponents(upload);
	}

	public VentanaUpload(String caption) {
		super(caption);
	}

	public VentanaUpload(String caption, Component content) {
		super(caption, content);
	}

	@Override
	public void uploadSucceeded(SucceededEvent event) {

		File file = receiver.getFile();
		contenedorVentada.removeAllComponents();
		contenedorVentada.addComponents(upload);

		if (event.getMIMEType().equals(VentanaUpload.TIPOPDF)) {

			Button grabar = new ObjetosComunes().getBoton("Grabar", "", "50px", VaadinIcons.CHECK);
			grabar.addClickListener(e -> clicGrabar(file));
			grabar.setCaption("Confirma grabación ");
			contenedorVentada.addComponent(grabar);

			Embedded pdf = new Embedded("", new FileResource(file));
			pdf.setMimeType("application/pdf");
			pdf.setType(Embedded.TYPE_BROWSER);
			pdf.setHeight("600px");
			pdf.setWidth("400px");
			contenedorVentada.addComponent(pdf);
		} else {
			new Notificaciones(Notificaciones.ERRORTIPOFICHERONOVALIDO);
		}

	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		logger.info("UPLOAD Status: " + readBytes + "----/" + contentLength);

	}

	@Override
	public void uploadFailed(FailedEvent event) {
		new Notificaciones(" Fallo en la carga del fichero ");
	}

	public void clicGrabar(File file) {
		LopdDocumento documento = new LopdDocumento();
		documento.setFecha(LocalDate.now());
		documento.setHora(Utilidades.getHoraAcualString());
		documento.setUsuCambio((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME));
		documento.setIdIncidenica(incidencia);
		documento.setFicheroAdjunto(file);
		documento.setEstado(Constantes.BBDD_ACTIVOSI);

		if (!new LopdDocumentoDAO().grabaDatos(documento)) {
			new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
		} else {
			new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);
			subWindow.close();
		}

	}

}
