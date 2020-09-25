package com.hnss.entidades.lopd;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import com.hnss.entidades.Paciente;
import com.hnss.entidades.Servicio;
import com.hnss.entidades.Usuario;

public class LopdIncidencia {

    private Long id;
    private Usuario usuarioRegistra;
    private LopdSujeto sujeto;
    private LopdTipos tipo;
    private LocalDateTime fechaHora;
    private Paciente paciente;
    private String idDocumento;
    private LocalDateTime fechaHoraDocumento;
    private Servicio servicio;
    private String descriDocu;
    private Boolean perdidaDatos;
    private String descripcionError;
    private String descripcionSolucion;
    private LocalDate fechaCambio;
    private Usuario usuCambio;
    private Usuario usuarioAfectado;

    private Boolean resuelta;
    private LocalDate fechaSolucion;

    private ArrayList<File> documentosAsociados = new ArrayList<>();

    public static String MAIL_ASUNTO_NUEVA = "Nueva incidencia relacionada con la seguiridad de los datos protegidos por  LOPD. ";
    public static String MAIL_ASUNTO_RESUELTA = "Resolución de incidencia de seguiridad  ";
    public static String MAIL_CONTENIDO_CABECERA = "Registro de una nueva incidencia de LOPD: \n"
            + "----------------------------------------------- \n ";

    public static String MAIL_CONTENIDO_PIE = "----------------------------------------------- \n"
            + "Desde el servicio de informática te mantendremos informado sobre el proceso de solución: \n" + "\n";

//http://localhost:8080/lopd/?APL=jjj&USR=06551256M&NOM=ASDFASDFADFAFADFADFA&MAI=jnieto@saludcastillayleon.es&ADDR=12.12.12.12&TIME=&NHC=29675&PAC=ASDFASDFADFASDFASFASDFAFASDF
    public LopdIncidencia() {
        this.id = new Long(0);
        this.resuelta = false;
        this.paciente = new Paciente();
        this.sujeto = LopdSujeto.SUJETO_PACIENTE;
    }

    public Long getId() {
        return id;
    }

    public String getIdString() {

        return Long.toString(id);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuarioRegistra() {
        return usuarioRegistra;
    }

    public void setUsuarioRegistra(Usuario usuarioRegistra) {
        this.usuarioRegistra = usuarioRegistra;
    }

    public LopdSujeto getSujeto() {
        return sujeto;
    }

    public void setSujeto(LopdSujeto sujeto) {
        this.sujeto = sujeto;
    }

    public LopdTipos getTipo() {
        return tipo;
    }

    public String getTipoDescripcion() {
        if (tipo != null) {
            return tipo.getDescripcion();
        } else {
            return "";
        }
    }

    public void setTipo(LopdTipos tipo) {
        this.tipo = tipo;
    }

    public void setTipo(String valor) {
        this.tipo.setDescripcion(valor);
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getFechaHoraFormato() {
        DateTimeFormatter fechaformato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        if (this.fechaHora != null) {
            return fechaformato.format(this.fechaHora);
        } else {
            return "";
        }
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public String getPacienteNumerohc() {
        if (paciente != null) {
            return paciente.getNumerohc();
        } else {
            return "";
        }
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public void setPacienteNumerohc(String numerohc) {
        if (paciente != null) {
            this.paciente.setNumerohc(numerohc);
        }

    }

    public String getIdDocumento() {
        return idDocumento;
    }

    public void setIdDocumento(String idDocumento) {
        this.idDocumento = idDocumento;
    }

    /*
	 * public String getFechaHora() { DateTimeFormatter fechadma =
	 * DateTimeFormatter.ofPattern("dd/MM/YYYY"); if (this.getFecha() != null)
	 * return fechadma.format(this.getFecha()) + " " + getHora(); else return ""; }
     */
    public LocalDateTime getFechaHoraDocumento() {
        return fechaHoraDocumento;
    }

    public void setFechaHoraDocumento(LocalDateTime fechaHoraDocumento) {
        this.fechaHoraDocumento = fechaHoraDocumento;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public String getPacienteApellidos() {
        if (paciente != null) {
            return paciente.getApellidosnombre();
        } else {
            return "";
        }
    }

    public void setPacienteApellidos(String apellidosnombre) {
        if (paciente != null) {
            paciente.setApellidosnombre(apellidosnombre);
        }
    }

    public String getDescriDocu() {
        return descriDocu;
    }

    public void setDescriDocu(String descriDocu) {
        this.descriDocu = descriDocu;
    }

    public Boolean getPerdidaDatos() {
        return perdidaDatos;
    }

    public void setPerdidaDatos(Boolean perdidaDatos) {
        this.perdidaDatos = perdidaDatos;
    }

    public String getDescripcionError() {
        return descripcionError;
    }

    public String getDescripcionErrorCorto() {

        if (descripcionError != null && descripcionError.length() > 50) {
            return descripcionError.substring(0, 49);
        } else {
            return descripcionError;
        }
    }

    public void setDescripcionError(String descripcionError) {
        this.descripcionError = descripcionError;
    }

    public String getDescripcionSolucion() {
        return descripcionSolucion;
    }

    public void setDescripcionSolucion(String descripcionSolucion) {
        this.descripcionSolucion = descripcionSolucion;
    }

    public LocalDate getFechaCambio() {
        return fechaCambio;
    }

    public void setFechaCambio(LocalDate fechaCambio) {
        this.fechaCambio = fechaCambio;
    }

    public Usuario getUsuCambio() {
        return usuCambio;
    }

    public void setUsuCambio(Usuario usuCambio) {
        this.usuCambio = usuCambio;
    }

    public Boolean getResuelta() {
        return resuelta;
    }

    public String getResueltaString() {
        if (resuelta == true) {
            return "S";
        } else {
            return "N";
        }
    }

    public void setResuelta(Boolean resuelta) {
        this.resuelta = resuelta;
    }

    public LocalDate getFechaSolucion() {
        return fechaSolucion;
    }

    public String getFechaSolucionString() {
        DateTimeFormatter fechadma = DateTimeFormatter.ofPattern("dd/MM/YYYY");
        if (this.getFechaSolucion() != null) {
            return fechadma.format(this.fechaSolucion);
        } else {
            return "";
        }
    }

    public void setFechaSolucion(LocalDate fechaSolucion) {
        this.fechaSolucion = fechaSolucion;
    }

    public ArrayList<File> getDocumentosAsociados() {
        return documentosAsociados;
    }

    public void setDocumentosAsociados(ArrayList<File> documentosAsociados) {
        this.documentosAsociados = documentosAsociados;
    }

    public Usuario getUsuarioAfectado() {
        return usuarioAfectado;
    }

    public void setUsuarioAfectado(Usuario usuarioAfectado) {
        this.usuarioAfectado = usuarioAfectado;
    }

    public String getHtmlContenidoSolicitud() {
        DateTimeFormatter fechaFormato = DateTimeFormatter.ofPattern("dd/mm/YYYY hh:mm");
        String textHtml = "<b>Número:</b>" + this.getId() + "\n";
        textHtml = textHtml.concat("<b>Fecha hora: </b>" + fechaFormato.format(this.getFechaHora()) + " \n");
        textHtml = textHtml.concat("<b>Tipo: </b> " + this.getTipo().getDescripcion() + "<br> \n");
        if (this.getPaciente() != null && this.getPaciente().getNumerohc() != null
                && !this.getPaciente().getNumerohc().isEmpty()) {
            textHtml = textHtml.concat("<b>Númerohc:  </b>" + this.getPaciente().getNumerohc() + " \n");
            textHtml = textHtml.concat("<b>Paciente: </b> " + this.getPaciente().getApellidosnombre() + " <br> \n");
            if (this.fechaHoraDocumento != null) {
                textHtml = textHtml.concat("<b>Documento fecha: </b> " + fechaFormato.format(this.getFechaHoraDocumento()) + " \n");
            } else {
                textHtml = textHtml.concat("<b>Documento fecha: </b> \n");
            }

            if (this.getDescriDocu()!=null) {
            textHtml = textHtml.concat("<b>Documento: </b> " + this.getDescriDocu() + " <br> \n");
            } else {
textHtml = textHtml.concat("<b>Documento: </b>   <br> \n");                
            }
        } else {

        }
        if (this.getDescripcionError()!=null) {
        textHtml = textHtml.concat("<b>Descripción error: </b> " + this.getDescripcionError() + "<hr>");
        } else {
            textHtml = textHtml.concat("<b>Descripción error: </b>  <hr>");
        }
        return textHtml;
    }

    public String getHtmlContenidoSolución() {
        String textHtml = "Fecha hora: " + this.getFechaSolucion() + " \n";
        textHtml = textHtml.concat("Solución: " + this.getDescripcionSolucion() + " \n");
        textHtml = textHtml.concat("Técnico: " + this.getUsuCambio().getApellidosNombre() + " \n");

        return textHtml;
    }

    public String getAyudaHtml() {
        String ayudaHtml = " <b>Descripción de los campos de la incidencia:\n  </b> " + "<ul> "
                + "<li><b>Dni:</b> Dni del  profesional que indica la incidencia.</li>"
                + "<li><b>Usuario:</b>Apellidos y nombre.</li>"
                + "<li><b>Correo:</b>El correo electrónico del usuario.</li>"
                + "<li><b>Tipo:</b>Clasificación de la incidencia según los siguientes criterios.</li>"
                + "<ul> "
                + "<li><b>Paciente:Datos de identificación incorrectos:</b>Cuando algún dato de filiación del paciente no es correcto.</li>"
                + "<li><b>Paciente:Informe no corresponde:</b>El informe no corresponde a ese paciente.</li>"
                + "<li><b>Paciente:Contenido de informe erróneo:</b>Algún dato del contenido del informe no es correcto.</li>"
                + "<li><b>Paciente:Arquetipo no corresponde a paciente:</b>El paciente tiene un registro o arquetipo que no es suyo, no le corresponde.</li>"
                + "<li><b>Paciente:Arquetipo contenido erróneo:</b>En un registro o arquetipo algún dato no es correcto.</li>"
                + "<li><b>Paciente:Resolución Gerente:</b>Resolución de Gerencia para modificar un dato.</li>"
                + "<li><b>Usuario: Incidencia de usuario:</b>Olvio de contraseña, acceso no reconocido u otras incicencias relacionadas con un usuario.</li>"
                + "<li><b>Trabajador: Datos del trabajador:</b>Incidencias relacionadas con datos de los trabajadores.</li>"
                + "<li><b>Incidencias con datos impresos o en soportes:</b>Incidencias relativas a documentos impresos, historias en papel o datos personales en otros soportes infomáticos.</li>"
                + "<li><b>Otra incidencia de seguridad:</b>Incidencias de alimentación eléctrica, fallos en las comunicaciones,.</li>"
                + "</ul>" + "<li><b>Fecha y hora:</b>Del registro de la incidencia.</li>"
                + "<li><b>Datos para incidencias de pacientes</b></li>"
                + "<ul> "
                + "<li><b>NHC:</b> Número de historia clínica</li>"
                + "<li><b>idDocumento:</b> El número de identificación del documento o del informe afectado</li>"
                + "<li><b>Fecha docu:</b> Fecha del documento afectado</li>"
                + "<li><b>Hora docu:</b> Hora del documento afectado</li>"
                + "<li><b>Servicio:</b> Servicio al que pertenece el documento afectado.</li>"
                + "<li><b>Paciente:</b> Apellidos y nombre del paciente.</li>"
                + "<li><b>Descripción del documento:</b> La descripción del documento afectado.</li>"
                + "</ul>"
                + "<li><b>Desripción detallada del error:</b> La descripción detallada del error que permite a los técnicos hacer la corrección de forma segura.</li>"
                + "<li><b>Botones de acción</b> </li>" + "<ul> "
                + "<li><b>Grabar. </b>Almacena los datos de forma permanente.</li>"
                + "<li><b>Adjuntar documento. </b>Permite añadir un documento PDF que aclaratorio de la indicencia</li>"
                + "<li><b>Ayuda. </b>Esta pantalla.</li>" + "</ul>"
                + "</ul>";

        return ayudaHtml;
    }
}
