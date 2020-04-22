package com.hnss.ui.lopd;

import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import com.hnss.dao.LopdDocumentoDAO;
import com.hnss.dao.LopdIncidenciaDAO;
import com.hnss.entidades.Paciente;
import com.hnss.entidades.Servicio;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdDocumento;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdNotas;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.excepciones.MailExcepciones;
import com.hnss.listados.IncidenciaPdfInformeCompleto;
import com.hnss.listados.IncidenciaPdfUsuario;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.ObjetosComunes;
import com.hnss.ui.VentanaFrm;
import com.hnss.ui.VentanaHtml;
import com.hnss.ui.VentanaUpload;
import com.hnss.ui.VentanaVerPdf;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.MandaMail;
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
import com.vaadin.ui.DateField;
import com.vaadin.ui.DateTimeField;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LopdIncidenciaRespuesta extends VerticalLayout {

    /**
     *
     */
    private static final long serialVersionUID = -6795711918678309764L;

    private HorizontalLayout fila1 = new HorizontalLayout();
    private HorizontalLayout fila2 = new HorizontalLayout();
    private HorizontalLayout fila3 = new HorizontalLayout();
    private HorizontalLayout fila4 = new HorizontalLayout();
    private HorizontalLayout fila5 = new HorizontalLayout();
    private HorizontalLayout fila6 = new HorizontalLayout();
    private HorizontalLayout fila7 = new HorizontalLayout();
    private HorizontalLayout fila8 = new HorizontalLayout();
    private HorizontalLayout fila9 = new HorizontalLayout();
    private HorizontalLayout contenedorBotonesAccion = new HorizontalLayout();
    private HorizontalLayout contenedorBotonesAyuda = new HorizontalLayout();
    private HorizontalLayout contenedorNotas = new HorizontalLayout();

    private TextField dni;
    private TextField apellidosNombre = new TextField("Usuario");
    private TextField mail = new TextField("Correo ");
    private TextField telefono = new TextField("Teléfono ");
    private TextField tipo = new TextField("Tipo de incidencia");

    private TextField id = new TextField("Id");
    private DateTimeField fechaHora = new DateTimeField("Fecha Hora");
    private CheckBox perdidaDatos = new CheckBox("Marca la cailla si se han perdido datos.");

    private TextField numerohc;
    private TextField pacienteApellidos = new TextField("Paciente");
    private TextField idDocumento = new TextField("Id docu");
    private DateTimeField fechaHoraDocumento = new DateTimeField("Fecha Hora");
    private ComboBox<Servicio> comboServicio;
    private TextField descriDocu = new TextField("Descripción documento");
    // datos del usuario para incidencias de usuario
    private TextField idUsuarioAfectado = new TextField("Id");
    private TextField dniUsuarioAfectado = new TextField("Dni usu");
    private TextField apellido1UsuarioAfectado = new TextField("Apellido1 ");
    private TextField apellido2UsuarioAfectado = new TextField("Apellido2 ");
    private TextField nombreUsuarioAfectado = new TextField("Nombre");
    private TextField mailUsuarioAfectado = new TextField("Mail usu");
    private TextField telefonoUsuarioAfectado = new TextField("Telefono usu");

    private TextArea descripcionError = new TextArea("Desripción detallada del error");
    private TextArea descripcionSolucion = new TextArea("Desripción detallada de la solución");
    private CheckBox resuelta = new CheckBox("Inicidencia resuleta: cerrar");
    private DateField fechaSolucion;

    private Button jimenaBorraInf;
    private Button grabar, limpiar, adjuntar, notanueva, imprimir, hojaUsu, ayuda;

    private Grid<LopdNotas> gridNotas = new Grid<>();
    private Binder<LopdIncidencia> binder = new Binder<>();
    private LopdIncidencia incidencia;

    private Usuario usuAfectado = new Usuario();
    private Usuario usuRegistra = new Usuario();
    private Paciente paciente = new Paciente();
    private Binder<Usuario> binderUsuarioAfectado = new Binder<Usuario>();
    private Binder<Usuario> binderUsuarioRegistra = new Binder<Usuario>();
    private Binder<Paciente> binderPaciente = new Binder<Paciente>();

    public LopdIncidenciaRespuesta(LopdIncidencia inciParam) {
        this.incidencia = inciParam;
        this.paciente = inciParam.getPaciente();
        this.usuRegistra = inciParam.getUsuarioRegistra();
        this.setWidth("870px");
        this.setMargin(false);
        this.setSpacing(false);
        this.addComponents(fila1, fila2, fila3, fila4, fila5, fila6, fila7, fila8, fila9, contenedorBotonesAyuda, contenedorBotonesAccion, contenedorNotas);
        fila1.setMargin(false);
        fila1.setSpacing(false);
        fila1.addStyleName(ValoTheme.LAYOUT_WELL);

        fila2.setMargin(false);

        fila3.setMargin(false);
        fila4.setMargin(false);
        fila5.setMargin(false);
        fila5.setMargin(false);
        fila6.setMargin(false);
        fila7.setMargin(false);
        fila8.setMargin(false);
        fila9.setMargin(false);

        contenedorBotonesAyuda.setMargin(false);
        contenedorBotonesAyuda.setSpacing(true);

        contenedorBotonesAyuda.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        contenedorBotonesAccion.setMargin(false);
        contenedorBotonesAccion.setSpacing(true);
        // fila11.setMargin(false);

        contenedorBotonesAccion.setWidth("800px");
        contenedorBotonesAccion.setDefaultComponentAlignment(Alignment.TOP_CENTER);

        contenedorNotas.setWidth("800px");

        dni = new ObjetosComunes().getDni("Dni", "dni");
        dni.setEnabled(false);
        binderUsuarioRegistra.forField(dni).bind(Usuario::getDni, Usuario::setDni);

        apellidosNombre.setWidth("340");
        apellidosNombre.setEnabled(false);
        binderUsuarioRegistra.forField(apellidosNombre).bind(Usuario::getApellidosNombre, Usuario::setApellidosNombre);

        mail.setWidth("330");
        mail.setEnabled(false);
        binderUsuarioRegistra.forField(mail).bind(Usuario::getMail, Usuario::setMail);

        telefono.setWidth("100px");
        telefono.setIcon(VaadinIcons.AT);
        binderUsuarioRegistra.forField(telefono).asRequired(Notificaciones.FORMULARIOCAMPOREQUERIDO)
                .bind(Usuario::getTelefono, Usuario::setTelefono);

        fila1.addComponents(dni, apellidosNombre, mail, telefono);

        tipo.setWidth("700px");
        tipo.setEnabled(false);
        binder.forField(tipo).bind(LopdIncidencia::getTipoDescripcion, LopdIncidencia::setTipo);
        fila2.addComponent(tipo);

        id.setWidth("100px");
        id.setEnabled(false);
        binder.forField(id).withConverter(new StringToLongConverter("Debe ser un número")).bind(LopdIncidencia::getId,
                LopdIncidencia::setId);
        fechaHora = new ObjetosComunes().getFechaHora("Fecha Hora ", " ");
        fechaHora.setEnabled(false);
        binder.forField(fechaHora).bind(LopdIncidencia::getFechaHora, LopdIncidencia::setFechaHora);

        binder.forField(perdidaDatos).bind(LopdIncidencia::getPerdidaDatos, LopdIncidencia::setPerdidaDatos);
        fila3.addComponents(id, fechaHora, perdidaDatos);

        // datos para incidencia de paciente
        numerohc = new ObjetosComunes().getNumerohc("NºHC", " nhc");
        numerohc.setEnabled(false);
        binderPaciente.forField(numerohc).bind(Paciente::getNumerohc, Paciente::setNumerohc);

        idDocumento.setWidth("100px");

        binder.forField(idDocumento).bind(LopdIncidencia::getIdDocumento, LopdIncidencia::setIdDocumento);

        fechaHoraDocumento = new ObjetosComunes().getFechaHora("Fecha Hora Inf/Dato", " ");
        fechaHoraDocumento.setEnabled(false);
        binder.forField(fechaHoraDocumento).bind(LopdIncidencia::getFechaHoraDocumento,
                LopdIncidencia::setFechaHoraDocumento);

        comboServicio = new ObjetosComunes().getComboServicio(new Servicio(), new Servicio());
        comboServicio.setEnabled(false);

        binder.forField(comboServicio).bind(LopdIncidencia::getServicio, LopdIncidencia::setServicio);

        pacienteApellidos.setWidth("420px");
        pacienteApellidos.setIcon(VaadinIcons.USER_HEART);
        pacienteApellidos.setEnabled(false);
        binderPaciente.forField(pacienteApellidos).bind(Paciente::getApellidosnombre, Paciente::setApellidosnombre);

        descriDocu.setWidth("420px");
        descriDocu.setIcon(VaadinIcons.FILE_TEXT);
        descriDocu.setEnabled(false);
        binder.forField(descriDocu).bind(LopdIncidencia::getDescriDocu, LopdIncidencia::setDescriDocu);

// datos para incidencia de usuario
        // campos para incidencias asociadas a usuarios
        idUsuarioAfectado.setWidth("100px");
        binderUsuarioAfectado.forField(idUsuarioAfectado).withConverter(new StringToLongConverter("Debe ser un número"))
                .bind(Usuario::getId, Usuario::setId);

        dniUsuarioAfectado = new ObjetosComunes().getDni("Dni usu", "");
        binderUsuarioAfectado.forField(dniUsuarioAfectado).bind(Usuario::getDni, Usuario::setDni);

        nombreUsuarioAfectado = new ObjetosComunes().getApeNombre("Nomb Usu", "");
        binderUsuarioAfectado.forField(nombreUsuarioAfectado).bind(Usuario::getNombre, Usuario::setNombre);

        apellido1UsuarioAfectado = new ObjetosComunes().getApeNombre("Ape1 Usu", "");
        binderUsuarioAfectado.forField(apellido1UsuarioAfectado).bind(Usuario::getApellido1, Usuario::setApellido1);
        apellido2UsuarioAfectado = new ObjetosComunes().getApeNombre("Ape2 Usu", "");
        binderUsuarioAfectado.forField(apellido2UsuarioAfectado).bind(Usuario::getApellido2, Usuario::setApellido2);

        mailUsuarioAfectado = new ObjetosComunes().getMail("Mail usu");
        binderUsuarioAfectado.forField(mailUsuarioAfectado).bind(Usuario::getMail, Usuario::setMail);
        telefonoUsuarioAfectado = new ObjetosComunes().getTelefono("Tf", "");
        binderUsuarioAfectado.forField(telefonoUsuarioAfectado).bind(Usuario::getTelefono, Usuario::setTelefono);

        if (incidencia.getTipo() != null
                && incidencia.getTipo().getSujeto().getId() == LopdSujeto.SUJETO_PACIENTE.getId()) {
            fila4.addComponents(numerohc, idDocumento, fechaHoraDocumento, comboServicio);
            fila5.addComponents(pacienteApellidos, descriDocu);
        } else if (incidencia.getTipo() != null
                && incidencia.getTipo().getSujeto().getId() == LopdSujeto.SUJETO_USUARIO.getId()) {
            fila4.addComponents(idUsuarioAfectado, dniUsuarioAfectado, nombreUsuarioAfectado, apellido1UsuarioAfectado,
                    apellido2UsuarioAfectado);
            fila5.addComponents(mailUsuarioAfectado, telefonoUsuarioAfectado, comboServicio);
        }

        descripcionError.setWidth("865px");
        descripcionError.setHeight("100px");
        descripcionError.setIcon(VaadinIcons.WARNING);
        descripcionError.setEnabled(false);
        binder.forField(descripcionError).bind(LopdIncidencia::getDescripcionError,
                LopdIncidencia::setDescripcionError);
        fila6.addComponent(descripcionError);

        descripcionSolucion.setWidth("865px");
        descripcionSolucion.setHeight("100px");
        descripcionSolucion.setIcon(VaadinIcons.THUMBS_UP);
        binder.forField(descripcionSolucion).bind(LopdIncidencia::getDescripcionSolucion,
                LopdIncidencia::setDescripcionSolucion);
        fila7.addComponent(descripcionSolucion);

        binder.forField(resuelta).bind(LopdIncidencia::getResuelta, LopdIncidencia::setResuelta);
        resuelta.addValueChangeListener(e -> salaResuleta());

        fechaSolucion = new ObjetosComunes().getFecha("F.Solucion", "");
        binder.forField(fechaSolucion).withValidator(returnfecha -> {
            if (resuelta.getValue() == false && returnfecha == null) {
                return true;
            } else if (resuelta.getValue() == true && returnfecha != null) {
                return true;
            } else {
                return false;
            }
        }, " Si resuelto fecha obligatorio ").bind(LopdIncidencia::getFechaSolucion, LopdIncidencia::setFechaSolucion);

        fila8.addComponents(resuelta, fechaSolucion);

        jimenaBorraInf = new ObjetosComunes().getBoton("J.BorraInf", "Borrado lógico del informe de jimena ", "50px", VaadinIcons.DEL);
        jimenaBorraInf.addClickListener(e -> doJimenaBorrarClick());
        contenedorBotonesAyuda.addComponents(jimenaBorraInf);

        grabar = new ObjetosComunes().getBoton("Grabar", "Almacena los datos actuales", "50px", VaadinIcons.CHECK);
        grabar.addClickListener(e -> grabarClick());

        limpiar = new ObjetosComunes().getBoton("Limpia", "", "40px", VaadinIcons.COMPILE);
        limpiar.addClickListener(e -> doLimpiaDatos());

        notanueva = new ObjetosComunes().getBoton("Nota", " nueva nota", "50px", VaadinIcons.PLUS);
        notanueva.addClickListener(e -> notanuevaClick());

        adjuntar = new ObjetosComunes().getBoton("AdjuntarDocu", "Adjunta documento a la incidencia", "50px",
                VaadinIcons.UPLOAD);
        adjuntar.addClickListener(e -> clickUpload());

        imprimir = new ObjetosComunes().getBoton("Informe", "", "40px", VaadinIcons.PRINT);
        imprimir.addClickListener(e -> doInforme());

        hojaUsu = new ObjetosComunes().getBoton("Imprime", "", "40px", VaadinIcons.PRINT);
        hojaUsu.addClickListener(e -> doImprime());

        ayuda = new ObjetosComunes().getBoton("Ayuda", "Información de los tipos de datos ", "50px",
                VaadinIcons.QUESTION);
        ayuda.addClickListener(e -> clickAyuda());

        contenedorBotonesAccion.addComponents(grabar, limpiar, adjuntar, notanueva, imprimir, hojaUsu, ayuda);
        binder.readBean(incidencia);
        binderUsuarioRegistra.readBean(incidencia.getUsuarioRegistra());
        binderUsuarioAfectado.readBean(incidencia.getUsuarioAfectado());
        binderPaciente.readBean(incidencia.getPaciente());

        gridNotas.setSizeFull();
        gridNotas.addColumn(LopdNotas::getId).setCaption("Nº");
        gridNotas.addColumn(LopdNotas::getFecha).setCaption("Fecha");
        gridNotas.addColumn(LopdNotas::getDescripcion).setCaption("Descripcion");
        gridNotas.addSelectionListener(e -> selecciona());
        String textoString = "Notas asocidas a la incidencia actual Nº:" + incidencia.getIdString();
        gridNotas.setCaption(textoString);
        // gridNotas.setCaption("Notas asocidas a la incidencia " + incidencia == null ?
        // "" : incidencia.getIdString());
        contenedorNotas.addComponent(gridNotas);
        doFilaDocumentos();
        doActivaBotones();
        doActualizaGridNotas();
    }

    public void doJimenaBorrarClick() {
        new Notificaciones(paciente.getApellidosnombre());
        FrmJimenaBorraInf jimenaBorrarInf = new FrmJimenaBorraInf(this.getUI(), incidencia, paciente, null);
        VentanaFrm ventanaFrm = new VentanaFrm(this.getUI(), jimenaBorrarInf.getContenedorContenido(), "Borrar informe de jimena");
        ventanaFrm.addCloseListener(event -> {
            if (jimenaBorrarInf.getBorradoConfirmado() == Boolean.TRUE) {
                doActualizaGridNotas();
                resuelta.setValue(Boolean.TRUE);
                descripcionSolucion.setValue("Borrado desde la utilidad de jimena");
                fechaSolucion.setValue(LocalDate.now());
                grabarClick();
            }
        });
    }

    public void cierraBorrarInf() {

    }

    public void salaResuleta() {
        if (resuelta.getValue() == true) {
            fechaSolucion.setValue(LocalDate.now());
        } else {
            fechaSolucion.clear();
        }
    }

    public void selecciona() {
        if (gridNotas.getSelectedItems().size() > 0) {
            LopdNotas nota = gridNotas.getSelectedItems().iterator().next();
            FrmInicidenciaNota frm = new FrmInicidenciaNota(incidencia, nota);
            VentanaFrm vfmFrm = new VentanaFrm(this.getUI(), frm, "Nota para incidencia" + incidencia.getId());
            vfmFrm.addCloseListener(e -> closeVentana());
        }
    }

    public void notanuevaClick() {
        if (incidencia != null && incidencia.getId() > 0) {
            FrmInicidenciaNota frm = new FrmInicidenciaNota(incidencia, new LopdNotas());
            VentanaFrm vfmFrm = new VentanaFrm(this.getUI(), frm, "Nota para incidenica" + incidencia.getId());
            vfmFrm.addCloseListener(e -> closeVentana());
        }
    }

    public void closeVentana() {
        doActualizaGridNotas();
    }

    public void doActivaBotones() {
        if (incidencia.getId().equals(new Long(0))) {
            grabar.setEnabled(false);
            limpiar.setEnabled(false);
            adjuntar.setEnabled(false);
            notanueva.setEnabled(false);
            imprimir.setEnabled(false);
            hojaUsu.setEnabled(false);

            jimenaBorraInf.setEnabled(false);
        } else {
            grabar.setEnabled(true);
            limpiar.setEnabled(true);
            adjuntar.setEnabled(true);
            notanueva.setEnabled(true);
            imprimir.setEnabled(true);
            hojaUsu.setEnabled(true);
            if (incidencia.getSujeto().equals(LopdSujeto.PACIENTE)) {
                jimenaBorraInf.setEnabled(true);
            }
        }
        if (incidencia.getResuelta() == true
                && ChronoUnit.DAYS.between(incidencia.getFechaSolucion(), LocalDate.now()) > 10) {
            new Notificaciones("Incidencia Cerrada, no modificable");
            grabar.setEnabled(false);
            adjuntar.setEnabled(false);
            notanueva.setEnabled(false);
            limpiar.setEnabled(true);
            imprimir.setEnabled(true);
            hojaUsu.setEnabled(true);
        }
    }

    public void clickUpload() {
        incidencia.setUsuCambio((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME));
        VentanaUpload ventanaUpload = new VentanaUpload(this.getUI(), incidencia);
        ventanaUpload.addCloseListener(e -> doFilaDocumentos());
    }

    public void clickPdf(LopdDocumento documento) {
        new VentanaVerPdf(new LopdDocumentoDAO().getStreamPDF(documento), this.getUI());

    }

    private void doFilaDocumentos() {
        fila9.removeAllComponents();
        for (LopdDocumento documento : new LopdDocumentoDAO().getDocumentos(incidencia)) {
            FileResource resourcePdf = new FileResource(new File(
                    VaadinService.getCurrent().getBaseDirectory().getAbsolutePath() + "/WEB-INF/images/pdf.png"));

            Button pdfButton = new ObjetosComunes().getBoton("", "", "", resourcePdf);
            pdfButton.setIcon(resourcePdf);
            pdfButton.addClickListener(e -> clickPdf(documento));
            fila9.addComponent(pdfButton);
        }
    }

    public void doInforme() {
        new VentanaVerPdf(new IncidenciaPdfInformeCompleto(incidencia).getFile(), this.getUI());
    }

    public void doImprime() {
        new VentanaVerPdf(new IncidenciaPdfUsuario(incidencia).getFile(), this.getUI());
    }

    public void grabarClick() {
        Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
        incidencia.setUsuCambio(usuario);
        try {
//			binderPaciente.writeBean(paciente);
            binder.writeBean(incidencia);
            // incidencia.setPaciente(paciente);
            // solo actualiza los datos de la respuesta
            if (new LopdIncidenciaDAO().actualizaRespuesta(incidencia) == false) {
                new Notificaciones(Notificaciones.FORMULARIO_DATOS_ERROR_GUARDADOS);
            } else {
                new Notificaciones(Notificaciones.FORMULARIO_DATOS_GUARDADOS);

                if (incidencia.getResuelta() == true) {
                    new VentanaVerPdf(new IncidenciaPdfUsuario(incidencia).getFile(), this.getUI());

                    new MandaMail().sendEmail(incidencia.getUsuarioRegistra().getMail(),
                            LopdIncidencia.MAIL_ASUNTO_RESUELTA,
                            Constantes.HTMLFIN +  LopdIncidencia.MAIL_CONTENIDO_CABECERA + incidencia.getHtmlContenidoSolicitud() + "\n\n"
                            + incidencia.getHtmlContenidoSolución() + Constantes.HTMLFIN);
                }
                doLimpiaDatos();
            }
        } catch (ValidationException e) {
            new Notificaciones(Notificaciones.BINDER_DATOS_ERRORVALIDACION);
        } catch (MailExcepciones e) {
            doLimpiaDatos();
            new Notificaciones(MandaMail.MAIL_ERRORENVIO_STRING);
        } finally {

        }
    }

    public void doLimpiaDatos() {
        incidencia = new LopdIncidencia();
        binder.readBean(incidencia);
        binderUsuarioRegistra.readBean(new Usuario());
        binderUsuarioAfectado.readBean(new Usuario());
        binderPaciente.readBean(new Paciente());
        doActivaBotones();
        gridNotas.setItems(new ArrayList<LopdNotas>());
    }

    public void doActualizaGridNotas() {
        gridNotas.setItems(new LopdIncidenciaDAO().getNostasIncidencia(incidencia));
    }

    public void clickAyuda() {
        new VentanaHtml(this.getUI(), new Label(incidencia.getAyudaHtml()),
                "Ayuda formulario de registro de incidencias LOPD.");
    }
}
