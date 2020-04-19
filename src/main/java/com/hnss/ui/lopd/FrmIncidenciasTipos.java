package com.hnss.ui.lopd;

import org.vaadin.dialogs.ConfirmDialog;

import com.hnss.dao.LopTiposDAO;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.entidades.lopd.LopdTipos;
import com.hnss.ui.FrmMaster;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class FrmIncidenciasTipos extends FrmMaster {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2526841218477911522L;

	private TextField id = new TextField("Id");
	private TextField descripcion = new TextField("Descripción");
	private ComboBox<LopdSujeto> sujetoCombo = new ComboBox<LopdSujeto>();// 1 paciente, 2
																			// trabajador, 3
																			// usuario, 4 otro
	private CheckBox mailReponsable = new CheckBox("Enviar mail reponsable LOPD");

	private LopdTipos lopdTipos;

	private Binder<LopdTipos> binder = new Binder<LopdTipos>();

	private Grid<LopdTipos> grid = new Grid<LopdTipos>();

	public FrmIncidenciasTipos(LopdTipos tipoprm) {
		super();
		this.lopdTipos = tipoprm;
		this.setCaption("Gestión de tipos de incidencias");
		contenedorCampos.addComponent(contenedorCamposLayout);
		contenedorGrid.addComponent(grid);

		id.setWidth("100px");
		id.setEnabled(false);
		binder.forField(id).withConverter(new StringToLongConverter("Debe ser un número")).bind(LopdTipos::getId,
				LopdTipos::setId);
		contenedorCamposLayout.addComponent(id);

		descripcion.setWidth("400px");
		descripcion.setMaxLength(50);
		binder.forField(descripcion).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(LopdTipos::getDescripcion,
				LopdTipos::setDescripcion);
		contenedorCamposLayout.addComponent(descripcion);

		sujetoCombo.setCaption("Individuo afectado");
		sujetoCombo.setItemCaptionGenerator(LopdSujeto::getDescripcion);
		sujetoCombo.setItems(LopdSujeto.LISTASUJETOS_COMPLETA);
		sujetoCombo.addSelectionListener(e -> saltaSujeto());
		binder.forField(sujetoCombo).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(LopdTipos::getSujeto,
				LopdTipos::setSujeto);
		contenedorCamposLayout.addComponent(sujetoCombo);

		binder.forField(mailReponsable).bind(LopdTipos::getMailReponsable, LopdTipos::setMailReponsable);
		contenedorCamposLayout.addComponent(mailReponsable);

		binder.readBean(lopdTipos);

		grid.addColumn(LopdTipos::getId).setCaption("Id");
		grid.addColumn(LopdTipos::getDescripcion).setCaption("Descripción");
		grid.addColumn(LopdTipos::getSujetoDescripcion).setCaption("Sujeto");
		grid.addColumn(LopdTipos::getMailReponsable).setCaption("Mail");
		grid.setItems(new LopTiposDAO().getListaIncidenciaTipos(null, null));
		grid.addSelectionListener(e -> selecciona());
		descripcion.focus();
		doActivaBotones(lopdTipos.getId());

	}

	public void saltaSujeto() {
		grid.setItems(new LopTiposDAO().getListaIncidenciaTipos(sujetoCombo.getValue(), null));
	}

	public void selecciona() {
		if (grid.getSelectedItems().size() == 1) {
			lopdTipos = grid.getSelectedItems().iterator().next();
			binder.readBean(lopdTipos);
			doActivaBotones(lopdTipos.getId());
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
			binder.writeBean(lopdTipos);
			if (new LopTiposDAO().grabaDatos(lopdTipos) == true) {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);
				lopdTipos = new LopdTipos();
				binder.readBean(lopdTipos);
				grid.setItems(new LopTiposDAO().getListaIncidenciaTipos(null, null));
			} else {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
			}
		} catch (ValidationException e) {
			new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
		}
		doActivaBotones(lopdTipos.getId());
	}

	@Override
	public void borrarClick() {
		if (lopdTipos != null) {
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
		if (new LopTiposDAO().borraDatos(lopdTipos)) {
			new Notificaciones(Notificaciones.DATO_BORRADO);
			lopdTipos = new LopdTipos();
			binder.readBean(lopdTipos);
			grid.setItems(new LopTiposDAO().getListaIncidenciaTipos(null, null));
		}
	}

	@Override
	public boolean doValidaFormulario() {
		return false;
	}

	@Override
	public void limpiarClick() {
		// TODO Auto-generated method stub

	}

}
