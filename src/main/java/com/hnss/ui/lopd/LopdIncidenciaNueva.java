package com.hnss.ui.lopd;

import com.hnss.controlador.AuthService;
import com.hnss.dao.FuncionalidadDAO;
import com.hnss.dao.JimenaDAO;
import com.hnss.dao.LopTiposDAO;
import com.hnss.dao.LopdDocumentoDAO;
import com.hnss.dao.LopdIncidenciaDAO;
import com.hnss.dao.PacienteDAO;
import com.hnss.dao.UsuarioDAO;
import com.hnss.entidades.Funcionalidad;
import com.hnss.entidades.Paciente;
import com.hnss.entidades.Servicio;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdDocumento;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.entidades.lopd.LopdTipos;
import com.hnss.excepciones.LoginException;
import com.hnss.excepciones.MailExcepciones;
import com.hnss.excepciones.PasswordException;
import com.hnss.excepciones.UsuarioBajaException;
import com.hnss.lopdcaa.MyUI;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.ObjetosComunes;
import com.hnss.ui.VentanaHtml;
import com.hnss.ui.VentanaUpload;
import com.hnss.ui.VentanaVerPdf;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.MandaMail;
import com.hnss.utilidades.Utilidades;
import com.hnss.validadores.ValidaDni;
import com.vaadin.data.Binder;
import com.vaadin.data.ValidationException;
import com.vaadin.data.converter.StringToLongConverter;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.RadioButtonGroup;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class LopdIncidenciaNueva extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = -6795711918678309764L;

    private HorizontalLayout fila0 = new HorizontalLayout();
    private HorizontalLayout fila1 = new HorizontalLayout();
    private HorizontalLayout contenedorIncidencia = new HorizontalLayout();
    private VerticalLayout contenedorClasificacion = new VerticalLayout();
    private HorizontalLayout contenedorSujetos = new HorizontalLayout();
    private VerticalLayout contenedorTipos = new VerticalLayout();

    private VerticalLayout contenedorCampos = new VerticalLayout();
    private VerticalLayout contenedorDescrip = new VerticalLayout();
    private HorizontalLayout fila2 = new HorizontalLayout();
    private HorizontalLayout filaPaci0 = new HorizontalLayout();
    private HorizontalLayout filaPaci1 = new HorizontalLayout();
    private HorizontalLayout filaPaci2 = new HorizontalLayout();
    private HorizontalLayout filaPaci3 = new HorizontalLayout();
    private HorizontalLayout filaPaci4 = new HorizontalLayout();

    private HorizontalLayout filausuario0 = new HorizontalLayout();
    private HorizontalLayout filausuario1 = new HorizontalLayout();
    private HorizontalLayout filausuario2 = new HorizontalLayout();
    private HorizontalLayout filausuario3 = new HorizontalLayout();
    private HorizontalLayout filausuario4 = new HorizontalLayout();

    private HorizontalLayout fila3 = new HorizontalLayout();
    private HorizontalLayout fila4 = new HorizontalLayout();
    private HorizontalLayout fila5 = new HorizontalLayout();
    private HorizontalLayout fila6 = new HorizontalLayout();

    private TextField dniRegistra;
    private PasswordField clave;
    private TextField apellidosNombreRegistra = new TextField("Usuario");
    private TextField mailRegistra = new TextField("Correo ");
    private TextField telefonoRegistra = new TextField("Teléfono ");
    private RadioButtonGroup<LopdSujeto> sujetoRadio = new RadioButtonGroup<LopdSujeto>("Persona afectada");
    private RadioButtonGroup<LopdTipos> tipoRadio = new RadioButtonGroup<LopdTipos>("Tipo de incidencia/gestión");

    private TextField id = new TextField("Nº");
    private DateTimeField fechaHora = new ObjetosComunes().getFechaHora("Fecha Hora", "");
    // datos del paciente
    private TextField numerohc;
    private TextField pacienteApellidos = new TextField("Paciente");
    private TextField idDocumento = new TextField("Id docu");
    private DateTimeField fechaHoraDocumento = new ObjetosComunes().getFechaHora("Fecha Hora docu/dato", "");
    private ComboBox<Servicio> comboServicio;
    private TextField descriDocu = new TextField("Descripción documento");
    // datos del usuario para incidencias de usuario
    private TextField idUsuarioAfectado = new TextField("Id");
    private TextField dniUsuarioAfectado = new TextField("Dni");
    private TextField apellido1UsuarioAfectado = new TextField("Apellido1");
    private TextField apellido2UsuarioAfectado = new TextField("Apellido2");
    private TextField nombreUsuarioAfectado = new TextField("Nombre");
    private TextField mailUsuarioAfectado = new TextField("Mail");
    private TextField telefonoUsuarioAfectado = new TextField("Telefono");

    private CheckBox perdidaDatos = new CheckBox("Marca este casilla si se han perdido datos.");
    private TextArea descripcionError = new TextArea("Desripción detallada del error");

    private Button grabar, adjuntar, ayuda, conectar;
    private Binder<LopdIncidencia> binder = new Binder<>();
    private Binder<Usuario> binderUsuarioAfectado = new Binder<>();
    private Binder<Usuario> binderUsuarioRegistra = new Binder<>();
    private LopdIncidencia incidencia;
    private ArrayList<LopdIncidencia> listaIncidencias = new ArrayList<>();

    private Grid<LopdIncidencia> grid = new Grid<LopdIncidencia>();

    private Usuario usuarioSesion = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
    private Usuario usuAfectado = new Usuario();
    private Usuario usuRegistra = new Usuario();

    public LopdIncidenciaNueva(LopdIncidencia inciParam) {
        this.incidencia = inciParam;
        this.setWidth("870px");
        this.setMargin(false);
        this.setCaption("<center>Registro de nueva incidencia de seguridad relacionada con la LOPD</center>");
        this.setCaptionAsHtml(true);
        this.addComponents(fila1, fila0, contenedorIncidencia, contenedorDescrip);
        this.addStyleName("green1");
        fila0.setMargin(false);
        fila0.addStyleName("green1");
        fila0.setSpacing(true);
        fila1.setMargin(false);
        fila1.addStyleName("green1");
        fila1.setSpacing(false);
        fila1.setCaption("Usuario que registra la incidencia");
        fila1.addStyleName(ValoTheme.LAYOUT_WELL);

        contenedorIncidencia.setMargin(false);
        contenedorIncidencia.setSpacing(false);
        contenedorIncidencia.setCaption("Datos de la incidencia");
        contenedorIncidencia.addComponents(fila2, contenedorCampos);
        contenedorIncidencia.addStyleName(ValoTheme.LAYOUT_WELL);
        contenedorIncidencia.addStyleName("green1");
        // contenedorIncidencia.setWidth("890px");
        contenedorIncidencia.setSizeFull();

        fila2.setMargin(false);
        fila2.setSpacing(false);
        fila2.addStyleName("green1");
        contenedorClasificacion.setMargin(false);
        contenedorClasificacion.setSpacing(false);
        contenedorClasificacion.addStyleName("green1");
        contenedorSujetos.setMargin(false);
        contenedorSujetos.setSpacing(false);
        contenedorSujetos.addStyleName("green1");
        contenedorTipos.setMargin(false);
        contenedorTipos.setSpacing(false);
        contenedorTipos.addStyleName("green1");

        contenedorCampos.setMargin(false);
        contenedorCampos.setSpacing(false);
        contenedorCampos.addStyleName("green1");
        contenedorClasificacion.addComponents(contenedorSujetos, contenedorTipos);
        fila2.addComponents(contenedorClasificacion, contenedorCampos);
        // contenedorCampos.setSizeFull();
        filaPaci0.setSpacing(false);
        filaPaci0.addStyleName("green1");
        filaPaci1.setSpacing(false);
        filaPaci1.addStyleName("green1");
        filaPaci2.setSpacing(false);
        filaPaci2.addStyleName("green1");
        filaPaci3.setSpacing(false);
        filaPaci3.addStyleName("green1");
        filaPaci4.setSpacing(false);
        filaPaci4.addStyleName("green1");

        filaPaci0.setVisible(false);
        filaPaci1.setVisible(false);
        filaPaci2.setVisible(false);
        filaPaci3.setVisible(false);
        filaPaci4.setVisible(false);

        filausuario0.setSpacing(false);
        filausuario0.addStyleName("green1");
        filausuario1.setSpacing(false);
        filausuario1.addStyleName("green1");
        filausuario2.setSpacing(false);
        filausuario2.addStyleName("green1");
        filausuario3.setSpacing(false);
        filausuario3.addStyleName("green1");
        filausuario4.setSpacing(false);
        filausuario4.addStyleName("green1");

        filausuario0.setVisible(false);
        filausuario1.setVisible(false);
        filausuario2.setVisible(false);
        filausuario3.setVisible(false);
        filausuario4.setVisible(false);

        fila3.setMargin(false);
        fila3.setSpacing(false);
        fila3.addStyleName("green1");
        fila4.setMargin(false);
        fila4.setSpacing(true);
        fila4.setWidth("800px");
        fila4.setDefaultComponentAlignment(Alignment.TOP_CENTER);
        fila4.addStyleName("green1");
        fila5.setMargin(false);
        fila5.setSpacing(false);
        fila5.addStyleName("green1");
        fila6.setMargin(false);
        fila6.setSpacing(false);
        fila6.setWidth("800px");
        fila6.addStyleName("green1");

        contenedorDescrip.setMargin(false);
        contenedorDescrip.setSpacing(false);
        contenedorDescrip.addStyleName("green1");
        // contenedorDescrip.setSizeFull();
        contenedorDescrip.addComponents(fila3, fila4, fila5, fila6);
        contenedorDescrip.addStyleName(ValoTheme.LAYOUT_WELL);

        dniRegistra = new ObjetosComunes().getDni("Dni", "dni");
        binderUsuarioRegistra.forField(dniRegistra).withValidator(new ValidaDni("DNI incorrecto "))
                .asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(Usuario::getDni, Usuario::setDni);
        dniRegistra.addBlurListener(e -> saltaDni());

        clave = new ObjetosComunes().getPassword("Clave", " clave");
        clave.addBlurListener(e -> conectarClic());
        binderUsuarioRegistra.forField(clave).bind(Usuario::getClave, Usuario::setClave);

        apellidosNombreRegistra.setWidth("335px");
        apellidosNombreRegistra.setIcon(VaadinIcons.USER);
        binderUsuarioRegistra.forField(apellidosNombreRegistra).bind(Usuario::getApellidosNombre,
                Usuario::setApellidosNombre);

        mailRegistra.setWidth("330px");
        mailRegistra.setMaxLength(50);
        mailRegistra.setIcon(VaadinIcons.AT);
        binderUsuarioRegistra.forField(mailRegistra).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
                .bind(Usuario::getMail, Usuario::setMail);

        telefonoRegistra.setWidth("100px");
        telefonoRegistra.setIcon(VaadinIcons.AT);
        binderUsuarioRegistra.forField(telefonoRegistra).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
                .bind(Usuario::getTelefono, Usuario::setTelefono);

        conectar = new ObjetosComunes().getBoton("Conectar", "", "100px", VaadinIcons.CHECK);
        conectar.setHeight("60px");
        conectar.addClickListener(e -> conectarClic());
        fila1.addComponents(dniRegistra, clave, conectar, apellidosNombreRegistra, mailRegistra, telefonoRegistra);

        id.setWidth("100px");
        id.setEnabled(false);
        binder.forField(id).withConverter(new StringToLongConverter("Debe ser un número")).bind(LopdIncidencia::getId,
                LopdIncidencia::setId);

        fechaHora.setEnabled(false);
        binder.forField(fechaHora).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
                .bind(LopdIncidencia::getFechaHora, LopdIncidencia::setFechaHora);
        perdidaDatos.setValue(false);
        perdidaDatos.setEnabled(false);
        binder.forField(perdidaDatos).bind(LopdIncidencia::getPerdidaDatos, LopdIncidencia::setPerdidaDatos);
        fila0.addComponents(id, fechaHora, perdidaDatos);

        sujetoRadio.setItems(LopdSujeto.LISTASUJETOS_COMPLETA);
        sujetoRadio.setItemCaptionGenerator(LopdSujeto::getDescripcion);
        sujetoRadio.addStyleName(ValoTheme.OPTIONGROUP_HORIZONTAL);
        sujetoRadio.setSelectedItem(LopdSujeto.SUJETO_PACIENTE);
        sujetoRadio.addSelectionListener(e -> saltaSujeto());
        binder.forField(sujetoRadio).bind(LopdIncidencia::getSujeto, LopdIncidencia::setSujeto);
        contenedorSujetos.addComponent(sujetoRadio);

        tipoRadio.setItemCaptionGenerator(LopdTipos::getDescripcion);
        tipoRadio.addBlurListener(e -> clicktipo());
        binder.forField(tipoRadio).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO).bind(LopdIncidencia::getTipo,
                LopdIncidencia::setTipo);

        contenedorTipos.addComponents(tipoRadio);
        // fila2.addComponent(tipo);

        // filausuario0.addComponents(fecha, hora, perdidaDatos);
        comboServicio = new ObjetosComunes().getComboServicio(null, null);
        filaPaci1.addComponent(comboServicio);

        numerohc = new ObjetosComunes().getNumerohc("NºHC", " nhc");
        binder.forField(numerohc).withValidator(returnNhc -> {
            if (returnNhc == null) {
                return true;
            } else {
                return returnNhc.matches("[0-9]*");
            }
        }, "numeros").bind(LopdIncidencia::getPacienteNumerohc, LopdIncidencia::setPacienteNumerohc);
        numerohc.addBlurListener(e -> saltaNumerohc());

        idDocumento.setWidth("100px");
        binder.forField(idDocumento).bind(LopdIncidencia::getIdDocumento, LopdIncidencia::setIdDocumento);

        fechaHoraDocumento = new ObjetosComunes().getFechaHora("Fecha Hora Doc/Reg", "fecha inf");
        binder.forField(fechaHoraDocumento).bind(LopdIncidencia::getFechaHoraDocumento,
                LopdIncidencia::setFechaHoraDocumento);

        comboServicio = new ObjetosComunes().getComboServicio(new Servicio(), new Servicio());
        binder.forField(comboServicio).bind(LopdIncidencia::getServicio, LopdIncidencia::setServicio);

        filaPaci2.addComponents(numerohc, fechaHoraDocumento);
        pacienteApellidos.setWidth("420px");
        pacienteApellidos.setIcon(VaadinIcons.USER_HEART);
        binder.forField(pacienteApellidos).bind(LopdIncidencia::getPacienteApellidos,
                LopdIncidencia::setPacienteApellidos);

        filaPaci3.addComponents(pacienteApellidos);

        descriDocu.setWidth("420px");
        descriDocu.setIcon(VaadinIcons.FILE_TEXT);
        binder.forField(descriDocu).bind(LopdIncidencia::getDescriDocu, LopdIncidencia::setDescriDocu);
        filaPaci4.addComponent(descriDocu);
// campos para incidencias asociadas a usuarios
        idUsuarioAfectado.setWidth("100px");
        idUsuarioAfectado.addBlurListener(e -> saltaIdAfectado());
        binderUsuarioAfectado.forField(idUsuarioAfectado).withConverter(new StringToLongConverter("Debe ser un número"))
                .bind(Usuario::getId, Usuario::setId);

        dniUsuarioAfectado = new ObjetosComunes().getDni("Dni usu", "");
        dniUsuarioAfectado.addBlurListener(e -> saltaDniAfectado());
        binderUsuarioAfectado.forField(dniUsuarioAfectado).bind(Usuario::getDni, Usuario::setDni);

        nombreUsuarioAfectado = new ObjetosComunes().getApeNombre("Nomb Usu", "");
        binderUsuarioAfectado.forField(nombreUsuarioAfectado).bind(Usuario::getNombre, Usuario::setNombre);
        filausuario1.addComponents(idUsuarioAfectado, dniUsuarioAfectado, nombreUsuarioAfectado);

        apellido1UsuarioAfectado = new ObjetosComunes().getApeNombre("Ape1 Usu", "");
        binderUsuarioAfectado.forField(apellido1UsuarioAfectado).bind(Usuario::getApellido1, Usuario::setApellido1);
        apellido2UsuarioAfectado = new ObjetosComunes().getApeNombre("Ape2 Usu", "");
        binderUsuarioAfectado.forField(apellido2UsuarioAfectado).bind(Usuario::getApellido2, Usuario::setApellido2);
        filausuario2.addComponents(apellido1UsuarioAfectado, apellido2UsuarioAfectado);

        mailUsuarioAfectado = new ObjetosComunes().getMail("Mail usu");
        binderUsuarioAfectado.forField(mailUsuarioAfectado).bind(Usuario::getMail, Usuario::setMail);
        telefonoUsuarioAfectado = new ObjetosComunes().getTelefono("Tf usu", "");
        binderUsuarioAfectado.forField(telefonoUsuarioAfectado).bind(Usuario::getTelefono, Usuario::setTelefono);
        filausuario3.addComponents(mailUsuarioAfectado, telefonoUsuarioAfectado);

        filausuario4.addComponent(comboServicio);

        descripcionError.setWidth("865px");
        descripcionError.setHeight("100px");
        descripcionError.setIcon(VaadinIcons.WARNING);
        binder.forField(descripcionError).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
                .bind(LopdIncidencia::getDescripcionError, LopdIncidencia::setDescripcionError);
        fila3.addComponent(descripcionError);

        if (!incidencia.getId().equals(new Long(0))) {
            doFilaDocumentos();
        }

        grabar = new ObjetosComunes().getBoton("Grabar", "Almacena los datos actuales", "50px", VaadinIcons.CHECK);
        grabar.addClickListener(e -> grabarClick());
        adjuntar = new ObjetosComunes().getBoton("AdjuntarDocu", "Adjunta documento a la incidencia", "50px",
                VaadinIcons.UPLOAD);
        adjuntar.addClickListener(e -> clickUpload());

        if (incidencia.getId().equals(new Long(0))) {
            adjuntar.setVisible(false);
        } else {
            adjuntar.setVisible(true);
        }

        ayuda = new ObjetosComunes().getBoton("Ayuda", "Información de los tipos de datos ", "50px",
                VaadinIcons.QUESTION);
        ayuda.addClickListener(e -> clickAyuda());
        fila5.addComponents(grabar, adjuntar, ayuda);
        fila5.addStyleName("green1");

        grid.setSizeFull();
        grid.setCaption("Incidencias recientes del usuario ");
        grid.addColumn(LopdIncidencia::getId).setCaption("Id");
        grid.addColumn(LopdIncidencia::getFechaHoraFormato).setCaption("Fecha hora");
        grid.addColumn(LopdIncidencia::getDescripcionErrorCorto).setCaption("Descripción");
        grid.addColumn(LopdIncidencia::getResuelta).setCaption("Resuelta");
        grid.addSelectionListener(e -> selecciona());
        grid.setVisible(false);
        // grid.setStyleGenerator(lopdIncidencia -> lopdIncidencia.getResuelta() ? "red"
        // : "yellow");
        grid.setStyleGenerator(e -> getEstilo(e));

        fila6.addComponent(grid);
        binder.readBean(incidencia);
        binderUsuarioRegistra.readBean(incidencia.getUsuarioRegistra());

        if (incidencia.getUsuarioAfectado() != null) {
            binderUsuarioAfectado.readBean(incidencia.getUsuarioAfectado());
        }

        if (usuarioSesion != null && usuarioSesion.isLlamadaExterna() == true) {
            // está registrando un usuario por llamada externa
            apellidosNombreRegistra.setVisible(false);
            mailRegistra.setVisible(false);
            telefonoRegistra.setVisible(false);
            clave.setVisible(true);
            conectar.setVisible(true);

            contenedorIncidencia.setEnabled(false);
            contenedorCampos.setEnabled(false);
            contenedorDescrip.setEnabled(false);
            saltaSujeto();
        } else {
            // esta registrando un administrador
            dniRegistra.clear();
            apellidosNombreRegistra.clear();
            mailRegistra.clear();
            apellidosNombreRegistra.setVisible(true);
            mailRegistra.setVisible(true);
            fechaHora.setEnabled(true);
            perdidaDatos.setEnabled(true);
            clave.setVisible(false);
            conectar.setVisible(false);

            contenedorIncidencia.setEnabled(true);
            contenedorCampos.setEnabled(true);
            contenedorDescrip.setEnabled(true);
        }

    }

    private String getEstilo(LopdIncidencia e) {
        if (e.getResuelta() == true) {
            return "yellow";
        } else {
            return "green";
        }

    }

    public void saltaDniAfectado() {
        if (!dniUsuarioAfectado.isEmpty()) {
            usuAfectado = new UsuarioDAO().getUsuarioDni(dniUsuarioAfectado.getValue(), true);
            if (usuAfectado == null) {
                usuAfectado = new JimenaDAO().getUsuario(dniUsuarioAfectado.getValue());
                usuAfectado.setFucionalidadesArrayList(new FuncionalidadDAO().getListaFuncioUsuarioAl(usuAfectado));
            }
            if (usuAfectado != null) {
                binderUsuarioAfectado.readBean(usuAfectado);
            }
        }
    }

    public void saltaIdAfectado() {
        if (!idUsuarioAfectado.isEmpty() && Utilidades.isNumeric(idUsuarioAfectado.getValue())) {
            usuAfectado = new UsuarioDAO().getUsuarioId(Long.parseLong(dniUsuarioAfectado.getValue()));
            if (usuAfectado != null) {
                binderUsuarioAfectado.readBean(usuAfectado);
            }
        }
    }

    public void saltaSujeto() {
        if (!sujetoRadio.isEmpty()) {
            LopdSujeto sujetoelegido = (LopdSujeto) sujetoRadio.getSelectedItem().get();
            if (sujetoelegido == null) {
                new Notificaciones("Es necesario elegir el sujeto afectado");
                doOcultaCamposUsuAfectado();
                doOcultaCamposPaciente();
                contenedorCampos.removeAllComponents();

            } else {
                ArrayList<LopdTipos> lista = new LopTiposDAO().getListaIncidenciaTipos(sujetoelegido, usuarioSesion);
                tipoRadio.setItems(lista);
                if (lista.size() > 0) {
                    if (incidencia.getTipo() != null) {
                        for (LopdTipos t : lista) {
                            if (t.getId().equals(incidencia.getTipo().getId())) {
                                tipoRadio.setSelectedItem(t);
                            }
                        }
                    } else {
                        tipoRadio.setSelectedItem(lista.get(0));
                    }
                }
            }
            if (sujetoelegido.equals(LopdSujeto.SUJETO_PACIENTE)) {
                if (!numerohc.isEmpty()) {
                    Paciente paciente = new PacienteDAO().getPacienteNhc(numerohc.getValue());
                    if (paciente != null) {
                        incidencia.setPaciente(paciente);
                        pacienteApellidos.setValue(paciente.getApellidosnombre());
                    }
                }
                filaPaci1.setVisible(true);
                filaPaci2.setVisible(true);
                filaPaci3.setVisible(true);
                filaPaci4.setVisible(true);
                contenedorCampos.removeAllComponents();
                contenedorCampos.addComponents(filaPaci0, filaPaci1, filaPaci2, filaPaci3, filaPaci4);

            } else if (sujetoelegido.equals(LopdSujeto.SUJETO_USUARIO)) {
                doOcultaCamposPaciente();
                filausuario0.setVisible(true);
                filausuario1.setVisible(true);
                filausuario2.setVisible(true);
                filausuario3.setVisible(true);
                contenedorCampos.removeAllComponents();
                contenedorCampos.addComponents(filausuario0, filausuario1, filausuario2, filausuario3, filausuario4);
            } else {
                doOcultaCamposUsuAfectado();
                doOcultaCamposPaciente();
                contenedorCampos.removeAllComponents();
            }
        } else {
            sujetoRadio.setSelectedItem(LopdSujeto.SUJETO_PACIENTE);
        }
    }

    public void doOcultaCamposUsuAfectado() {
        idUsuarioAfectado.clear();
        dniUsuarioAfectado.clear();
        apellido1UsuarioAfectado.clear();
        apellido2UsuarioAfectado.clear();
        mailUsuarioAfectado.clear();
        telefonoUsuarioAfectado.clear();
        filausuario0.setVisible(false);
        filausuario1.setVisible(false);
        filausuario2.setVisible(false);
        filausuario3.setVisible(false);
        filausuario4.setVisible(false);
    }

    public void doOcultaCamposPaciente() {
        filaPaci1.setVisible(false);
        filaPaci2.setVisible(false);
        filaPaci3.setVisible(false);
        filaPaci4.setVisible(false);
        numerohc.clear();
        pacienteApellidos.clear();
        idDocumento.clear();
        fechaHoraDocumento.clear();
        comboServicio.clear();
        descriDocu.clear();
    }

    private void conectarClic() {
        try {
            usuRegistra = new Usuario(dniRegistra.getValue(), clave.getValue());
            Usuario usuarioLogeado = AuthService.login(usuRegistra, false);
            if (usuarioLogeado != null) {
                usuRegistra = usuarioLogeado;
                incidencia.setUsuarioRegistra(usuRegistra);
                binder.readBean(incidencia);
                binderUsuarioRegistra.readBean(usuRegistra);
                apellidosNombreRegistra.setVisible(true);
                telefonoRegistra.setVisible(true);
                mailRegistra.setVisible(true);
                fechaHora.setEnabled(true);
                perdidaDatos.setEnabled(true);
                clave.setVisible(false);
                conectar.setVisible(false);
                listaIncidencias = new LopdIncidenciaDAO().getListaInicidencias(null, null, null, usuRegistra, null);
                grid.setItems(listaIncidencias);
                if (listaIncidencias.size() > 0) {
                    grid.setVisible(true);
                }
                contenedorIncidencia.setEnabled(true);
                contenedorCampos.setEnabled(true);
                contenedorDescrip.setEnabled(true);

                if (usuRegistra.tieneLaFuncionalidad(Funcionalidad.PEDIRUSUARIO)
                        || usuRegistra.getEstado() == usuRegistra.USUARIO_ADMINISTRADOR) {
                    sujetoRadio.setItems(LopdSujeto.LISTASUJETOS_COMPLETA);
                } else {
                    sujetoRadio.setItems(LopdSujeto.LISTASUJETOS_SINUSUARIOS);
                }

            }
        } catch (LoginException | UsuarioBajaException | PasswordException e) {
            new Notificaciones(Notificaciones.LONGIN_USUARIO_NO_ENCONTRADO);
        }
    }

    private void doFilaDocumentos() {
        fila4.removeAllComponents();
        for (LopdDocumento documento : new LopdDocumentoDAO().getDocumentos(incidencia)) {
            FileResource resourcePdf = new FileResource(new File(
                    VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/images/pdf.png"));

            Button pdfButton = new ObjetosComunes().getBoton("", "", "", resourcePdf);
            pdfButton.setIcon(resourcePdf);
            // pdfButton.setCaption(Documento.getDescripcion());
            pdfButton.addClickListener(e -> clickPdf(documento));
            fila4.addComponent(pdfButton);
        }
    }

    public void clickPdf(LopdDocumento documento) {

        new VentanaVerPdf(new LopdDocumentoDAO().getStreamPDF(documento), this.getUI());

    }

    public void selecciona() {
        if (grid.getSelectedItems().size() == 1) {
            incidencia = grid.getSelectedItems().iterator().next();
            // System.out.println(incidencia.getTipo().getDescripcion());

            binder.readBean(incidencia);
            binderUsuarioRegistra.readBean(incidencia.getUsuarioRegistra());
            sujetoRadio.setSelectedItem(incidencia.getTipo().getSujeto());
            saltaSujeto();
            doFilaDocumentos();

            if (incidencia.getResuelta() == true) {
                grabar.setEnabled(false);
                adjuntar.setEnabled(false);
                new Notificaciones("Incidencia cerrada no se puede modificar");
            } else {
                adjuntar.setVisible(true);
            }

        }
    }

    public void saltaDni() {
        usuRegistra = new UsuarioDAO().getUsuarioDni(dniRegistra.getValue(), true);
        if (usuRegistra == null) {
            usuRegistra = new JimenaDAO().getUsuario(dniRegistra.getValue());
            if (usuRegistra != null) {
                usuRegistra.setEstado(Usuario.USUARIO_ACTIVO);
                Long idLong = new UsuarioDAO().insertaUsuario(usuRegistra);
                if (idLong != null) {
                    usuRegistra.setId(idLong);
                    binderUsuarioRegistra.readBean(usuRegistra);
                    incidencia.setUsuarioRegistra(usuRegistra);
                    sujetoRadio.focus();
                    grid.setItems(new LopdIncidenciaDAO().getListaInicidencias(null, null, null, usuRegistra, null));
                    grid.setVisible(true);
                }
            } else {
                new Notificaciones("Usuario no encontrado");
                dniRegistra.focus();
            }
        } else {
            binderUsuarioRegistra.readBean(usuRegistra);
            incidencia.setUsuarioRegistra(usuRegistra);
            sujetoRadio.focus();
            grid.setItems(new LopdIncidenciaDAO().getListaInicidencias(null, null, null, usuRegistra, null));
            grid.setVisible(true);

        }

    }

    public void saltaNumerohc() {
        if (!numerohc.getValue().isEmpty()) {
            Paciente paciente = new PacienteDAO().getPacienteNhc(numerohc.getValue());
            if (paciente == null) {
                paciente = new JimenaDAO().getPaciente(numerohc.getValue());
                if (paciente == null) {
                    new Notificaciones("Paciente no encontrado para esa historia", true);
                    numerohc.clear();
                    numerohc.focus();
                } else {
                    new PacienteDAO().insertaPaciente(paciente);
                    incidencia.setPaciente(paciente);
                    pacienteApellidos.setValue(paciente.getApellidosnombre());
                }
            } else {
                incidencia.setPaciente(paciente);
                pacienteApellidos.setValue(paciente.getApellidosnombre());
            }
        }
    }

    public void clickUpload() {
        VentanaUpload ventanaUpload = new VentanaUpload(this.getUI(), incidencia);
        ventanaUpload.addCloseListener(e -> doFilaDocumentos());
    }

    public void grabarClick() {
        if (sujetoRadio.getSelectedItem().isPresent()) {
            LopdSujeto sujetoelegido = sujetoRadio.getSelectedItem().get();
            try {
                dniRegistra.setValue(dniRegistra.getValue().toUpperCase());
                binderUsuarioRegistra.writeBean(usuRegistra);
                if (new UsuarioDAO().actualizaMailTf(usuRegistra)) {
                }

                binder.writeBean(incidencia);
                incidencia.setUsuarioRegistra(usuRegistra);
                incidencia.setUsuCambio(usuarioSesion);
                if (incidencia.getId().equals(new Long(0))) {
                    incidencia.setFechaHora(LocalDateTime.now());
                    incidencia.setResuelta(false);
                }
                if (sujetoelegido.equals(LopdSujeto.SUJETO_PACIENTE)) {

                } else if (sujetoelegido.equals(LopdSujeto.SUJETO_USUARIO)) {
                    Usuario usuAfectado = new Usuario();
                    binderUsuarioAfectado.writeBean(usuAfectado);
                    if (usuAfectado.getId().equals(new Long(0))) {
                        usuAfectado.setEstado(Usuario.USUARIO_ACTIVO);
                        Long id = new UsuarioDAO().insertaUsuario(usuAfectado);
                        if (!id.equals(new Long(0))) {
                            usuAfectado.setId(id);
                            incidencia.setUsuarioAfectado(usuAfectado);
                        }
                    }
                }
                if (new LopdIncidenciaDAO().grabaDatos(incidencia) == false) {
                    new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
                } else {
                    new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);

                    listaIncidencias = new LopdIncidenciaDAO().getListaInicidencias(null, null, null, usuRegistra,
                            null);
                    if (listaIncidencias.size() > 0) {
                        grid.setVisible(true);
                    }
                    grid.setItems(listaIncidencias);

                    new MandaMail().sendEmail(incidencia.getUsuarioRegistra().getMail(),
                            LopdIncidencia.MAIL_ASUNTO_NUEVA, LopdIncidencia.MAIL_CONTENIDO_CABECERA
                            + incidencia.getHtmlContenidoSolicitud() + LopdIncidencia.MAIL_CONTENIDO_PIE);
                    doLimpiaValores();
                    if (usuarioSesion.isLlamadaExterna() == true) {
                        doCerrarVentana();
                    }
                }
            } catch (ValidationException e) {
                new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
            } catch (MailExcepciones e) {
                doLimpiaValores();
                new Notificaciones(MandaMail.MAIL_ERRORENVIO_STRING);
                if (usuarioSesion.isLlamadaExterna() == true) {
                    doCerrarVentana();
                }
            } finally {

            }
        } else {
            new Notificaciones("Debes selecionar un sujeto");
        }

    }

    public void doCerrarVentana() {
        ((MyUI) getUI()).showFin();
    }

    public void doLimpiaValores() {
        incidencia = new LopdIncidencia();
        incidencia.setFechaHora(LocalDateTime.now());
        usuRegistra = new Usuario();
        usuAfectado = new Usuario();
        binder.readBean(incidencia);
        binderUsuarioRegistra.readBean(usuRegistra);
        binderUsuarioAfectado.readBean(usuAfectado);
        contenedorCampos.removeAllComponents();
    }

    public void clicktipo() {

    }

    public void clickAyuda() {
        new VentanaHtml(this.getUI(), new Label(incidencia.getAyudaHtml()),
                "Ayuda formulario de registro de incidencias LOPD.");
    }
}
