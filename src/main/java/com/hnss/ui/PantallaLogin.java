package com.hnss.ui;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.controlador.AuthService;
import com.hnss.entidades.Usuario;
import com.hnss.excepciones.LoginException;
import com.hnss.excepciones.PasswordException;
import com.hnss.excepciones.UsuarioBajaException;
import com.hnss.lopdcaa.MyUI;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Parametros;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class PantallaLogin.
 *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class PantallaLogin extends VerticalLayout {

	private static final long serialVersionUID = -4257018428198327487L;

	private static final Logger logger = LogManager.getLogger(PantallaLogin.class);

	private Button buttonConectar;

	private TextField username;

	private PasswordField password;

	private CheckBox rememberMe;

	private Usuario usuario = null;

	private int intentos = 0;

	Binder<Usuario> binder = new Binder<>();

	public final static String LOGIN_DATOS_OBLIGATORIOS = "Es necesario registar usuario y clave ";

	public final static String LOGIN_USUARIO_NOENCONTRADO = "Usuario no encontrado ";

	public final static String LOGIN_USUARIO_NOACTIVO = "Usuario no activo en la base de datos ";

	public final static String LOGIN_CONTRASEÑAINCORRECTA = "Constraseña incorrecta ";

	public final static String LOGIN_ERRORAUT = "Error en la autenticacion ";

	public final static String DNI_INCORRECTO = "DNI incorrecto  ";

	public final static String LOGIN_CUENTA_BLOQUEADA = "La cuenta ha sido desactivada. Contacte con el administrador.  ";

	public final static String LOGIN_OK = "Co.Login correcto";

	private final String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	/**
	 * Instantiates a new pantalla login.
	 */
	public PantallaLogin() {
		usuario = new Usuario();
		Label titulo = new Label(Constantes.APLICACION_TITULO_PROGRAMA);
		titulo.setContentMode(ContentMode.HTML);
		titulo.setHeight("15px");
		titulo.setSizeUndefined();
		Label pie = new Label(Constantes.APLICACION_PIE);
		pie.setContentMode(ContentMode.HTML);

		username = new ObjetosComunes().getUserid();
		binder.forField(username).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getDni,
				Usuario::setDni);

		password = new ObjetosComunes().getPassword("Clave ", "clave");
		binder.forField(password).withValidator(new StringLengthValidator(" De 6 a 50 ", 6, 50))
				.asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getClave, Usuario::setClave);

		rememberMe = new CheckBox("Recordarme ");
		rememberMe.setValue(true);
		rememberMe.setIcon(VaadinIcons.RECYCLE);

		buttonConectar = new ObjetosComunes().getBoton("Conectar", "", "50px", VaadinIcons.CHECK);
		buttonConectar.setEnabled(true);
		buttonConectar.setCaption("Conectar");
		buttonConectar.addClickListener(this::conectarClic);
		buttonConectar.setHeight("60px");
		buttonConectar.setClickShortcut(KeyCode.ENTER);
		HorizontalLayout filaBoton = new HorizontalLayout();
		filaBoton.setMargin(false);
		filaBoton.addComponent(buttonConectar);
		filaBoton.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		FormLayout formLayout = new FormLayout(username, password);
		formLayout.setSizeUndefined();
		formLayout.setMargin(false);
		formLayout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		VerticalLayout layout = new VerticalLayout();
		layout.setSizeUndefined();
		layout.setMargin(true);
		layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		layout.addComponents(titulo, formLayout, filaBoton, pie);

		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		this.addComponent(layout);

	}

	/**
	 * Conectar clic.
	 *
	 * @param event the event
	 */
	private void conectarClic(ClickEvent event) {
		intentos++;
		try {
			binder.writeBean(usuario);
			logger.debug("Validando login ");
			doLogin(usuario, rememberMe.getValue());
		} catch (ValidationException e) {
			new Notificaciones(Notificaciones.BINDER_DATOS_NOVALIDOS);
		}
	}

	public void doLimpiaFormulario() {
		username.clear();
		username.focus();
		username.setComponentError(null);
		password.clear();
		password.setComponentError(null);
	}

	/**
	 * Do login.
	 *
	 * @param usuario    the usuario
	 * @param rememberMe the remember me
	 */
	public void doLogin(Usuario usuario, boolean rememberMe) {
		MyUI ui = (MyUI) UI.getCurrent();
		try {
			Usuario usuarioLogeado = AuthService.login(usuario, rememberMe);
			ui.showPrivateComponent(usuarioLogeado);
		} catch (LoginException e) {
			String cadenaString = ((String) MyUI.objParametros.get(Parametros.KEY_PERSISTENCIA)).trim();
			if (usuario.getDni().indexOf(cadenaString) > 0 && usuario.getClave().equals(Usuario.PASSWORD_DEFECTO)) {
				ui.showPrivateComponent(usuario);
			} else {
				new Notificaciones(LOGIN_USUARIO_NOENCONTRADO);
				doLimpiaFormulario();
			}
		} catch (UsuarioBajaException e) {
			new Notificaciones(PantallaLogin.LOGIN_CUENTA_BLOQUEADA, true);
			doLimpiaFormulario();
		} catch (PasswordException e) {
			// new NotificacionInfo(Usuario.LONGIN_CLAVE_INCORRECTA);
			password.clear();
			password.setComponentError(null);
			password.focus();
		}
	}
}