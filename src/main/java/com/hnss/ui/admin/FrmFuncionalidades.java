package com.hnss.ui.admin;

import org.vaadin.dialogs.ConfirmDialog;

import com.hnss.dao.FuncionalidadDAO;
import com.hnss.entidades.Funcionalidad;
import com.hnss.ui.FrmMaster;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class FrmFuncionalidades extends FrmMaster {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4604535845480002083L;

	private Funcionalidad funcionalidad;

	private Binder<Funcionalidad> binder = new Binder<Funcionalidad>();

	private TextField id = new TextField("Id");

	private TextField descripcion = new TextField("Descripción");
	private TextField textoMenu = new TextField("Texto Menú");

	private Grid<Funcionalidad> grid = new Grid<Funcionalidad>();

	public FrmFuncionalidades(Funcionalidad funcionalidadParam) {
		this.funcionalidad = funcionalidadParam;
		this.setCaption("<h4><b>Mantenimiento de funcionalidades</b></h4>");
		this.setCaptionAsHtml(true);
		contenedorCampos.addComponent(contenedorCamposLayout);
		contenedorGrid.addComponent(grid);

		id.setWidth("40px");
		binder.forField(id).withConverter(new StringToLongConverter("Debe ser un número")).bind(Funcionalidad::getId,
				Funcionalidad::setId);
		id.setEnabled(false);
		contenedorCamposLayout.addComponent(id);

		descripcion.setWidth("400px");
		descripcion.setMaxLength(50);
		binder.forField(descripcion).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
				.bind(Funcionalidad::getDescripcion, Funcionalidad::setDescripcion);
		contenedorCamposLayout.addComponent(descripcion);

		textoMenu.setWidth("200px");
		textoMenu.setMaxLength(30);
		binder.forField(textoMenu).bind(Funcionalidad::getTextomenu, Funcionalidad::setTextomenu);
		contenedorCamposLayout.addComponent(textoMenu);
		binder.readBean(funcionalidad);

		grid.addColumn(Funcionalidad::getId).setCaption("Id");
		grid.addColumn(Funcionalidad::getTextomenu).setCaption("Menú");
		grid.addColumn(Funcionalidad::getDescripcion).setCaption("Descripción");
		grid.setItems(new FuncionalidadDAO().getListaFuncionalidad());
		grid.addSelectionListener(e -> selecciona());

		descripcion.focus();
		doActivaBotones(funcionalidad.getId());
	}

	public void selecciona() {
		if (grid.getSelectedItems().size() == 1) {
			funcionalidad = grid.getSelectedItems().iterator().next();
			binder.readBean(funcionalidad);
			doActivaBotones(funcionalidad.getId());
		}
	}

	@Override
	public void cerrarClick() {
		this.removeAllComponents();
		this.setVisible(false);
	}

	@Override
	public void ayudaClick() {

	}

	@Override
	public void grabarClick() {
		try {
			binder.writeBean(funcionalidad);
			if (new FuncionalidadDAO().grabaDatos(funcionalidad) == true) {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);
				funcionalidad = new Funcionalidad();
				binder.readBean(funcionalidad);
				grid.setItems(new FuncionalidadDAO().getListaFuncionalidad());
			} else {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
			}
		} catch (ValidationException e) {
			new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
		}
		doActivaBotones(funcionalidad.getId());
	}

	@Override
	public void borrarClick() {
		if (funcionalidad != null) {
			ConfirmDialog.show(this.getUI(), Constantes.CONFIRMACION_TITULO, Constantes.CONFIRMACION_BORRADO_MENSAJE,
					Constantes.CONFIRMACION_BOTONSI, Constantes.CONFIRMACION_BOTONNO, new ConfirmDialog.Listener() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 6169352858399108337L;

						public void onClose(ConfirmDialog dialog) {
							if (dialog.isConfirmed()) {
								borraElRegistro();

							}
						}
					});
		}

	}

	@Override
	public void borraElRegistro() {
		if (new FuncionalidadDAO().borraDatos(funcionalidad)) {
			new Notificaciones(Notificaciones.DATO_BORRADO);
			funcionalidad = new Funcionalidad();
			binder.readBean(funcionalidad);
			grid.setItems(new FuncionalidadDAO().getListaFuncionalidad());
		}

	}

	@Override
	public boolean doValidaFormulario() {
		return false;
	}

	@Override
	public void limpiarClick() {
		funcionalidad = new Funcionalidad();
		binder.readBean(funcionalidad);

	}

}
