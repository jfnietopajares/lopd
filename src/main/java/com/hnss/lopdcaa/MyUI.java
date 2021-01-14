package com.hnss.lopdcaa;

import com.hnss.dao.ConexionDAO;
import com.hnss.dao.FuncionalidadDAO;
import com.hnss.dao.JimenaDAO;
import com.hnss.dao.PacienteDAO;
import com.hnss.dao.UsuarioDAO;
import com.hnss.entidades.Paciente;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.excepciones.SistemaException;
import com.hnss.ui.Menu;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.PantallaLogin;
import com.hnss.ui.lopd.LopdIncidenciaNueva;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Parametros;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.server.DeploymentConfiguration;
import com.vaadin.server.Page;
import com.vaadin.server.ServiceException;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.VaadinServletService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Properties;
import javax.servlet.annotation.WebServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This UI is the application entry point. A UI may either represent a browser
 * window (or tab) or some part of an HTML page where a Vaadin application is
 * embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is
 * intended to be overridden to add component to the user interface and
 * initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LogManager.getLogger(MyUI.class);

    public static Properties objParametros;

    static {
        try {
            objParametros = new Parametros().getParametros();
        } catch (Exception e) {
            logger.error("Error", e);
        }
    }

    final VerticalLayout pantallaPrincipal = new VerticalLayout();
    final HorizontalLayout filamenu = new HorizontalLayout();
    final HorizontalLayout filaformualrios = new HorizontalLayout();

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        this.getReconnectDialogConfiguration().setDialogText(" Cliente desconectado. Intentando reconectar. ");

        setErrorHandler(e -> controlErroresUI(e.getThrowable()));

        Page.getCurrent().setTitle(Constantes.APLICACION_NOMBRE_VENTANA);

        this.setSizeFull();
        setContent(pantallaPrincipal);

        try {

            validacionesPrevias();
            pantallaPrincipal.addComponents(filamenu, filaformualrios);
            pantallaPrincipal.setMargin(false);
            pantallaPrincipal.setSpacing(false);
            filamenu.setMargin(false);
            filamenu.setSpacing(false);
            filaformualrios.setMargin(false);
            filaformualrios.setSpacing(false);
            if (doRemoteLogin(vaadinRequest) == true) {
                LopdIncidencia incidencia = new LopdIncidencia();
                incidencia.setFechaHora(LocalDateTime.now());
                incidencia.setSujeto(LopdSujeto.SUJETO_PACIENTE);
                Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
                incidencia.setUsuarioRegistra(usuario);
                Paciente paciente = new PacienteDAO().getPacienteNhc(String.valueOf(getSession().getAttribute("NHC")));
                if (paciente != null) {
                    incidencia.setPaciente(paciente);
                } else {
                    incidencia.setPaciente(new Paciente(String.valueOf(getSession().getAttribute("NHC"))));
                }
                // incidencia.setUsuarioRegistra(usuario);
                pantallaPrincipal.addComponent(new LopdIncidenciaNueva(incidencia));
            } else {
                pantallaPrincipal.addComponent(new PantallaLogin());
            }

        } catch (Exception e1) {
            new Notificaciones(e1.getMessage(), true);
        }
    }

    public void showPublicComponent() {
        setContent(new PantallaLogin());
    }

    public void showFin() {
        Button cerrarButton = new Button("El dato se ha almacenado correctamente, pulsa  Enter para cerrar");
        cerrarButton.addClickListener(clickEvent -> cerrar());
        cerrarButton.setClickShortcut(KeyCode.ENTER);
        cerrarButton.addListener(KeyPressEvent -> cerrar());
        pantallaPrincipal.removeAllComponents();
        pantallaPrincipal.addComponent(cerrarButton);
        pantallaPrincipal.setSizeFull();
        pantallaPrincipal.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        pantallaPrincipal.setComponentAlignment(cerrarButton, Alignment.MIDDLE_CENTER);
        setContent(pantallaPrincipal);
    }

    public void cerrar() {

        final String context = VaadinServlet.getCurrent().getServletContext().getContextPath();
        getUI().getPage().setLocation("http://10.36.64.15");
        // getUI().getPage().setLocation("http://blank.html");
        getSession().close();

        /*
		 * Collection<Window> listaW = getUI().getWindows(); for (Window w : listaW) {
		 * w.close(); }
         */
    }

    public void showPrivateComponent(Usuario usuario) {
        pantallaPrincipal.removeAllComponents();
        pantallaPrincipal.addComponents(filamenu, filaformualrios);
        filamenu.addComponent(new Menu(usuario));
        filaformualrios.removeAllComponents();

    }

    public void actualiza(Component c) {
        pantallaPrincipal.setMargin(false);
        pantallaPrincipal.setSpacing(false);
        filamenu.setMargin(false);
        filamenu.setSpacing(false);
        filaformualrios.setMargin(false);
        filaformualrios.setSpacing(false);
        filaformualrios.removeAllComponents();
        filaformualrios.addComponent(c);
    }

    public void controlErroresUI(Throwable e) {
        logger.error(Notificaciones.ERROR_UI, e);
        new Notificaciones(Notificaciones.ERROR_UI + e.getMessage(), true);
    }

    boolean doRemoteLogin(VaadinRequest request) {
        try {
            String APL = String.valueOf(getSession().getAttribute("APL"));
            String USR = String.valueOf(getSession().getAttribute("USR"));
            String ADDR = String.valueOf(getSession().getAttribute("ADDR"));
            String NHC = String.valueOf(getSession().getAttribute("NHC"));

            if (APL.equals("null")) {
                return false;
            } else if (!APL.isEmpty()) {
                Usuario usuario = new UsuarioDAO().getUsuarioDni(USR, true);
                if (usuario == null) {
                    usuario = new JimenaDAO().getUsuario(USR);
                    if (usuario != null) {
                        usuario.setEstado(Usuario.USUARIO_ACTIVO);
                        Long idLong = new UsuarioDAO().insertaUsuario(usuario);
                        if (idLong != null) {
                            usuario.setId(idLong);
                            usuario.setEstado(Usuario.USUARIO_ACTIVO);
                            usuario.setLlamadaExterna(true);
                            usuario.setFucionalidadesArrayList(new FuncionalidadDAO().getListaFuncioUsuarioAl(usuario));
                        }
                    } else {
                        new Notificaciones("Usuario no registrado");
                        return false;
                    }
                } else {
                    usuario.setLlamadaExterna(true);
                }
                VaadinSession.getCurrent().setAttribute(Constantes.SESSION_USERNAME, usuario);
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            logger.error("Remote login: " + e);
            return false;
        }

    }

    @WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        protected VaadinServletService createServletService(DeploymentConfiguration deploymentConfiguration)
                throws ServiceException {
            VaadinServletService service = new VaadinServletService(this, deploymentConfiguration) {
                @Override
                public void requestEnd(VaadinRequest request, VaadinResponse response, VaadinSession session) {
                    super.requestEnd(request, response, session);
                    if (request.getParameter("APL") != null) {
                        session.setAttribute("APL", request.getParameter("APL"));
                        session.setAttribute("USR", request.getParameter("USR"));
                        session.setAttribute("ADDR", request.getParameter("ADDR"));
                        session.setAttribute("NHC", request.getParameter("NHC"));
                    }
                }
            };
            service.init();
            return service;
        }

        public void init() {
            System.out.println("fadfdad");

        }
    }

    public void validacionesPrevias() throws SistemaException, IOException, Exception {
        if (new ConexionDAO().getConexionBBDD() == null) {
            logger.error(ConexionDAO.ERROR_BBDD_SIN_CONEXION);
            throw new SistemaException(ConexionDAO.ERROR_BBDD_SIN_CONEXION);
        }

        new Parametros();
    }

}
