package com.hnss.ui.lopd;

import com.hnss.dao.LopdIncidenciaDAO;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdNotas;
import com.hnss.lopdcaa.MyUI;
import com.hnss.ui.FrmMaster;
import com.hnss.ui.Notificaciones;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

public class FrmInicidenciaNota extends FrmMaster {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1065892957542470218L;

	private FormLayout contenedorCamposLayout = new FormLayout();

	private TextField id = new TextField("Id");
	private TextArea descripcion = new TextArea("Descripción");

	private Binder<LopdNotas> binder = new Binder<LopdNotas>();
	private LopdNotas nota;
	private LopdIncidencia incidencia;

	public FrmInicidenciaNota(LopdIncidencia incidenciaParam, LopdNotas notaParam) {
		super();
		this.incidencia = incidenciaParam;
		this.nota = notaParam;
		this.setCaption("Gestión de Notas de incidencia una incidencia ");

		contenedorCamposLayout.setSpacing(true);
		contenedorCamposLayout.setMargin(false);
		contenedorCampos.addComponent(contenedorCamposLayout);

		id.setWidth("100px");
		binder.forField(id).bind(LopdNotas::getIdString, LopdNotas::setId);
		contenedorCamposLayout.addComponent(id);

		descripcion.setWidth("400px");
		descripcion.setHeight("100px");
		binder.forField(descripcion).bind(LopdNotas::getDescripcion, LopdNotas::setDescripcion);
		contenedorCamposLayout.addComponent(descripcion);

		binder.readBean(nota);
		doActivaBotones(nota.getId());
	}

	@Override
	public void cerrarClick() {
		MyUI.getCurrent().getWindows().iterator().next().close();
	}

	@Override
	public void ayudaClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void grabarClick() {
		try {
			binder.writeBean(nota);
			Long idLong = new LopdIncidenciaDAO().grabaDatosNotas(incidencia, nota);
			if (!idLong.equals(new Long(0))) {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);
				nota = new LopdNotas();
				binder.readBean(nota);
				this.cerrarClick();
			} else {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
			}
		} catch (ValidationException e) {
			new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
		}
	}

	@Override
	public void borrarClick() {
		// TODO Auto-generated method stub

	}

	@Override
	public void borraElRegistro() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean doValidaFormulario() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void limpiarClick() {
		// TODO Auto-generated method stub

	}

}
