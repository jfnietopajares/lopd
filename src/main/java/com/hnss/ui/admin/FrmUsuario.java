package com.hnss.ui.admin;

import java.util.ArrayList;

import org.vaadin.dialogs.ConfirmDialog;

import com.hnss.dao.FuncionalidadDAO;
import com.hnss.dao.UsuarioDAO;
import com.hnss.entidades.Funcionalidad;
import com.hnss.entidades.Usuario;
import com.hnss.ui.FrmMaster;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.ObjetosComunes;
import com.hnss.utilidades.Constantes;
import com.hnss.validadores.ValidaDni;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.ui.CheckBoxGroup;
import com.vaadin.ui.Grid;
import com.vaadin.ui.TextField;

public class FrmUsuario extends FrmMaster {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4811245158048204994L;

	private TextField id = new TextField("Id");

	private TextField dni;

	private TextField clave;

	private TextField apellido1;

	private TextField apellido2;

	private TextField nombre;

	private TextField mail;

	private TextField telefono;

	private CheckBoxGroup<Funcionalidad> checkFuncionalidades = new CheckBoxGroup<Funcionalidad>("Funcionalidades");
	private CheckBoxGroup<String> checkFunciona = new CheckBoxGroup<String>("Funcionalidades");
	private Usuario usuario;
	private Binder<Usuario> binder = new Binder<Usuario>();
	private Grid<Usuario> grid = new Grid<>();

	public FrmUsuario(Usuario usuarioParam) {
		this.usuario = usuarioParam;
		this.setCaption("<h4><b>Mantenimiento de usuarios</b></h4>");
		this.setCaptionAsHtml(true);
		contenedorCampos.addComponents(fila1, fila2, fila3, fila4);
		contenedorGrid.addComponent(grid);

		id.setWidth("40px");
		binder.forField(id).withConverter(new StringToLongConverter("Debe ser un número")).bind(Usuario::getId,
				Usuario::setId);
		id.setEnabled(false);
		fila1.addComponent(id);

		dni = new ObjetosComunes().getDni("DNI", "dni");
		dni.addBlurListener(e -> saltaDni());
		binder.forField(dni).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
				.withValidator(new ValidaDni("DNI incorrecto ")).bind(Usuario::getDni, Usuario::setDni);
		fila1.addComponent(dni);

		apellido1 = new ObjetosComunes().getApeNombre("Apellido 1º", " apellido 1");
		binder.forField(apellido1).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getApellido1,
				Usuario::setApellido1);
		fila2.addComponent(apellido1);

		apellido2 = new ObjetosComunes().getApeNombre("Apellido 2º", " apellido 2");
		binder.forField(apellido2).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getApellido1,
				Usuario::setApellido2);
		fila2.addComponent(apellido2);

		nombre = new ObjetosComunes().getApeNombre("Nombre ", " nombre ");
		binder.forField(nombre).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getNombre,
				Usuario::setNombre);
		fila2.addComponent(nombre);

		mail = new ObjetosComunes().getMail("Mail");
		binder.forField(mail).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getMail,
				Usuario::setMail);
		fila3.addComponent(mail);

		telefono = new ObjetosComunes().getTelefono("Teléfono", "tf");
		binder.forField(telefono).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getTelefono,
				Usuario::setTelefono);
		fila3.addComponent(telefono);

		// checkFuncionalidades.setItemDescriptionGenerator(Funcionalidad::getDescripcion);
		// checkFuncionalidades.setItemCaptionGenerator(Funcionalidad::getDescripcion);

		binder.forField(checkFunciona).bind(Usuario::getFuncionalidadStrings, Usuario::setFuncionalidadStrings);
		fila4.addComponent(checkFunciona);

		binder.readBean(usuario);

		grid.addColumn(Usuario::getId).setCaption("Id");
		grid.addColumn(Usuario::getDni).setCaption("Dni");
		grid.addColumn(Usuario::getApellidosNombre).setCaption("Apellidos nombre");
		grid.setItems(new UsuarioDAO().getListaUsuario());
		grid.addSelectionListener(e -> selecciona());

		dni.focus();
		doActivaBotones(usuario.getId());

	}

	public void saltaDni() {
		if (!dni.getValue().isEmpty()) {
			usuario = new UsuarioDAO().getUsuarioDni(dni.getValue(), true);
			binder.readBean(usuario);
		}
	}

	@Override
	public void doActivaBotones(Long id) {
		if (id > 0) {
			borrar.setEnabled(true);
			checkFunciona.setItems(new FuncionalidadDAO().getListaFuncionalidadString());
			ArrayList<String> llArrayList = new FuncionalidadDAO().getListaFuncioUsuarioString(usuario);
			for (String f : llArrayList) {
				checkFunciona.select(f);
			}
		} else {

			borrar.setEnabled(false);
		}
	}

	public void selecciona() {
		if (grid.getSelectedItems().size() == 1) {
			usuario = grid.getSelectedItems().iterator().next();
			binder.readBean(usuario);
			doActivaBotones(usuario.getId());
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
			binder.writeBean(usuario);
			if (new UsuarioDAO().grabaDatos(usuario) == true) {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);
				usuario = new Usuario();
				binder.readBean(usuario);
				grid.setItems(new UsuarioDAO().getListaUsuario());
			} else {
				new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
			}
		} catch (ValidationException e) {
			new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
		}
		doActivaBotones(usuario.getId());
	}

	@Override
	public void borrarClick() {
		if (usuario != null) {
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
		if (new UsuarioDAO().borraDatos(usuario)) {
			new Notificaciones(Notificaciones.DATO_BORRADO);
			usuario = new Usuario();
			binder.readBean(usuario);
			grid.setItems(new UsuarioDAO().getListaUsuario());
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
