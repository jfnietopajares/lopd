package com.hnss.ui.admin;

import org.vaadin.dialogs.ConfirmDialog;

import com.hnss.dao.ServicioDAO;
import com.hnss.entidades.Servicio;
import com.hnss.ui.FrmMaster;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class FrmServicio extends FrmMaster {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4304598166690697513L;

	private TextField id = new TextField("Id");
	private TextField codigo = new TextField("Código");
	private TextField idjimena = new TextField("IdImena");
	private TextField descripcion = new TextField("Descripción");
	private CheckBox asistencial = new CheckBox("Asistencial");

	private Servicio servicio;
	private Binder<Servicio> binder = new Binder<Servicio>();
	private Grid<Servicio> grid = new Grid<Servicio>();

	public FrmServicio(Servicio servicioParam) {
		this.servicio = servicioParam;
		this.setCaption("<h4><b>Mantenimiento de servicios</b></h4>");
		this.setCaptionAsHtml(true);
		contenedorCampos.addComponent(contenedorCamposLayout);
		contenedorGrid.addComponent(grid);

		id.setWidth("100px");
		binder.forField(id).withConverter(new StringToLongConverter("Debe ser un número")).bind(Servicio::getId,
				Servicio::setId);
		id.setEnabled(false);
		contenedorCamposLayout.addComponent(id);

		descripcion.setWidth("400px");
		descripcion.setMaxLength(50);
		binder.forField(descripcion).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Servicio::getDescripcion,
				Servicio::setDescripcion);
		contenedorCamposLayout.addComponent(descripcion);

		codigo.setWidth("100px");
		codigo.setMaxLength(4);
		codigo.addBlurListener(e -> saltaCodigo());
		binder.forField(codigo).bind(Servicio::getCodigo, Servicio::setCodigo);
		contenedorCamposLayout.addComponent(codigo);

		idjimena.setWidth("100px");
		binder.forField(idjimena).withConverter(new StringToLongConverter("Debe ser un número"))
				.bind(Servicio::getIdjimena, Servicio::setIdjimena);
		contenedorCamposLayout.addComponent(idjimena);

		binder.forField(asistencial).bind(Servicio::getAsistencial, Servicio::setAsistencial);
		contenedorCamposLayout.addComponent(asistencial);

		binder.readBean(servicio);

		grid.addColumn(Servicio::getId).setCaption("Id");
		grid.addColumn(Servicio::getCodigo).setCaption("Cod");
		grid.addColumn(Servicio::getDescripcion).setCaption("Descripción");
		grid.setItems(new ServicioDAO().getListaServicios());
		grid.addSelectionListener(e -> selecciona());

		descripcion.focus();
		doActivaBotones(servicio.getId());
	}

	public void saltaCodigo() {
		if (!codigo.getValue().isEmpty()) {
			if (new ServicioDAO().getServicioCodigoExiste(codigo.getValue(), servicio) != null) {
				new Notificaciones("Código de servicio ya existe ", true);
				codigo.clear();
				codigo.focus();
			}
		}
	}

	public void selecciona() {
		if (grid.getSelectedItems().size() == 1) {
			servicio = grid.getSelectedItems().iterator().next();
			binder.readBean(servicio);
			doActivaBotones(servicio.getId());
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
			binder.writeBean(servicio);
			if (new ServicioDAO().grabaDatos(servicio) == true) {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);
				servicio = new Servicio();
				binder.readBean(servicio);
				grid.setItems(new ServicioDAO().getListaServicios());
			} else {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
			}
		} catch (ValidationException e) {
			new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
		}
		doActivaBotones(servicio.getId());
	}

	@Override
	public void borrarClick() {
		if (servicio != null) {
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
		if (new ServicioDAO().borraDatos(servicio)) {
			new Notificaciones(Notificaciones.DATO_BORRADO);
			servicio = new Servicio();
			binder.readBean(servicio);
			grid.setItems(new ServicioDAO().getListaServicios());
		}

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
