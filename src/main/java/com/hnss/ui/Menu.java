package com.hnss.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.vaadin.dialogs.ConfirmDialog;

import com.hnss.controlador.AuthService;
import com.hnss.dao.FuncionalidadDAO;
import com.hnss.entidades.Funcionalidad;
import com.hnss.entidades.Servicio;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdTipos;
import com.hnss.lopdcaa.MyUI;
import com.hnss.ui.admin.FrmFuncionalidades;
import com.hnss.ui.admin.FrmServicio;
import com.hnss.ui.admin.FrmUsuario;
import com.hnss.ui.lopd.FrmIncidenciasTipos;
import com.hnss.ui.lopd.LopdIncidenciaGestionar;
import com.hnss.ui.lopd.LopdIncidenciaNueva;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Parametros;
import com.hnss.utilidades.Utilidades;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;

/**
 * The Class MenuPrincipal. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class Menu extends MenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2524039260859750566L;
	public final static String MENU_LOPD = "Lopd";
	public final static String MENU_LOPD_NUEVA = "Nueva";
	public final static String MENU_LOPD_GESTIONAR = "Gestionar";
	public final static String MENU_LOPD_TIPOS = "Tipos";

	public final static String MENU_MISC = "Misc";
	public final static String MENU_MISC_VERDATOS = "Usuario";
	public final static String MENU_MISC_LOPDNUEVA = "Lopd";
	public final static String MENU_MISC_ENTORNO = "Entorno";
	public final static String MENU_MISC_VERSION = "A cerca de ";

	public final static String MENU_ADMIN = "Administrar";
	public final static String MENU_ADMIN_FUNCIONALIDADES = "Funcionalidades";
	public final static String MENU_ADMIN_PARAMETROS = "Parámetros";
	public final static String MENU_ADMIN_SERVICIOS = "Servicios";
	public final static String MENU_ADMIN_USUARIOS = "Usuarios";
	public final static String MENU_ADMIN_VER_PARÁMETROS = "Parámetros";

	public final static String MENU_AYUDA = "Ayuda";
	public final static String MENU_SALIR = "Salir";
	public final static String MENU_MISC_AYUDA = "Ayuda";

	// private final String basepath =
	// VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	/**
	 * Instantiates a new menu principal.
	 *
	 * @param usuario the usuario
	 */
	private static final Logger logger = LogManager.getLogger(Menu.class);
	private Usuario usuario;

	public Menu(Usuario usu) {
		this.usuario = usu;
		// this.setWidth(100.0f, Unit.PERCENTAGE);
		this.setSizeFull();

		MenuBar.Command unComando = new MenuBar.Command() {

			private static final long serialVersionUID = -4816907840054220837L;

			public void menuSelected(MenuItem selectedItem) {
				new Notificaciones(selectedItem.getText());
				switch (selectedItem.getText().trim()) {

				case Menu.MENU_MISC_VERDATOS:
					Label lbl = new Label(usuario.getHtmlDatos() + "<br>" + Utilidades.getInformacionCliente());
					new VentanaHtml(getUI(), lbl, "Usuario y entorno");
					break;
				case Menu.MENU_MISC_LOPDNUEVA:
					LopdIncidencia inci = new LopdIncidencia();
					usuario.setLlamadaExterna(true);
					inci.setUsuarioRegistra(usuario);
					inci.setFechaHora(LocalDateTime.now());
					((MyUI) getUI()).actualiza(new LopdIncidenciaNueva(inci));
					break;
				case Menu.MENU_MISC_ENTORNO:
					try {
						MyUI.objParametros = new Parametros().getParametros();

					} catch (Exception e) {
						logger.error(Notificaciones.EXCEPTION_ERROR, e);
					}
					break;
				case Menu.MENU_MISC_VERSION:
					new VentanaHtml(getParent().getUI(), new Label(
							Constantes.APLICACION_NOMBRE_VENTANA.concat("<br>").concat(Constantes.APLICACION_VERSION)));
					break;
// lopd
				case MENU_LOPD_NUEVA:
					LopdIncidencia incidencia = new LopdIncidencia();

					// Usuario usuario = (Usuario)
					// VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
					// incidencia.setUsuarioRegistra(usuario);
					incidencia.setFechaHora(LocalDateTime.now());
					((MyUI) getUI()).actualiza(new LopdIncidenciaNueva(incidencia));
					break;
				case MENU_LOPD_GESTIONAR:
					((MyUI) getUI()).actualiza(new LopdIncidenciaGestionar());
					break;
				case MENU_LOPD_TIPOS:
					((MyUI) getUI()).actualiza(new FrmIncidenciasTipos(new LopdTipos()));
					break;

				case Menu.MENU_SALIR:
					ConfirmDialog.show(((MyUI) getUI()), Constantes.CONFIRMACION_TITULO,
							Constantes.CONFIRMACION_SALIR_MENSAJE, Constantes.CONFIRMACION_BOTONSI,
							Constantes.CONFIRMACION_BOTONNO, new ConfirmDialog.Listener() {
								private static final long serialVersionUID = 6169352858399108337L;

								public void onClose(ConfirmDialog dialog) {
									if (dialog.isConfirmed()) {
										AuthService.logOut();
										((MyUI) getUI()).showPublicComponent();
									}
								}
							});

					break;
				// admin
				case MENU_ADMIN_FUNCIONALIDADES:
					((MyUI) getUI()).actualiza(new FrmFuncionalidades(new Funcionalidad()));
					break;
				case MENU_ADMIN_USUARIOS:
					((MyUI) getUI()).actualiza(new FrmUsuario(new Usuario()));
					break;
				case MENU_ADMIN_SERVICIOS:
					((MyUI) getUI()).actualiza(new FrmServicio(new Servicio()));
					break;
				case MENU_ADMIN_VER_PARÁMETROS:
					new VentanaHtml(getUI(), new Label(Parametros.verParametros(MyUI.objParametros)),
							"Listado de parámetros");
					break;

				}
			}
		};

		MenuItem miscelanea = this.addItem(Menu.MENU_MISC);
		miscelanea.addItem(Menu.MENU_MISC_LOPDNUEVA, null, unComando);
		miscelanea.addItem(Menu.MENU_MISC_VERDATOS, null, unComando);
		miscelanea.addItem(Menu.MENU_MISC_ENTORNO, null, unComando);
		miscelanea.addItem(Menu.MENU_MISC_VERSION, null, unComando);

		ArrayList<Funcionalidad> listaFuncionalidades = new FuncionalidadDAO().getListaFuncioUsuarioAl(usuario);
		if (usuario.getEstado() == Usuario.USUARIO_ADMINISTRADOR) {
			MenuItem menuAdm = this.addItem(Menu.MENU_ADMIN);
			menuAdm.addItem(MENU_ADMIN_FUNCIONALIDADES, null, unComando);
			menuAdm.addItem(MENU_ADMIN_PARAMETROS, null, unComando);
			menuAdm.addItem(MENU_ADMIN_SERVICIOS, null, unComando);
			menuAdm.addItem(MENU_ADMIN_USUARIOS, null, unComando);
			menuAdm.addItem(MENU_ADMIN_VER_PARÁMETROS, null, unComando);
		}
		for (Funcionalidad funcionalidad : listaFuncionalidades) {
			if (funcionalidad.getTextomenu().equals(MENU_LOPD)) {
				MenuItem menulopd = this.addItem(Menu.MENU_LOPD);
				menulopd.addItem(MENU_LOPD_NUEVA, null, unComando);
				menulopd.addItem(MENU_LOPD_GESTIONAR, null, unComando);
				menulopd.addItem(MENU_LOPD_TIPOS, null, unComando);
			} else if (funcionalidad.getTextomenu().equals(MENU_ADMIN)) {

			}
		}

		MenuItem usuarioActivo = this.addItem(Menu.MENU_SALIR, VaadinIcons.POWER_OFF, unComando);
		// usuarioActivo.setIcon(VaadinIcons.POWER_OFF);
		// usuarioActivo.setText(MenuPrincipal.MENU_SALIR);

	}

}
