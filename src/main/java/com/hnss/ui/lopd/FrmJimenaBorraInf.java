/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hnss.ui.lopd;

import com.hnss.dao.JimenaDAO;
import com.hnss.dao.LopdIncidenciaDAO;
import com.hnss.dao.UtilidadesDAO;
import com.hnss.entidades.Informe;
import com.hnss.entidades.Paciente;
import com.hnss.entidades.RespuestaSql;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdNotas;
import com.hnss.lopdcaa.MyUI;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.ObjetosComunes;
import com.hnss.ui.PantallaPaginacion;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.FileResource;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import java.io.File;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.paint.Material;
import org.vaadin.dialogs.ConfirmDialog;

/**
 *
 * @author 06551256M
 */
public class FrmJimenaBorraInf extends PantallaPaginacion {

    //  private HorizontalLayout contenedorPrincipal = new HorizontalLayout();
    private VerticalLayout contenedorPrincipal = new VerticalLayout();

    private VerticalLayout contenedorGrid = new VerticalLayout();

    private VerticalLayout contenedorHtml = new VerticalLayout();

    private VerticalLayout contenedorPdf = new VerticalLayout();

    private Paciente paciente;

    private LopdIncidencia incidencia = null;

    private LocalDate fecha = null;

    protected DateTimeFormatter fechadma = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    private ArrayList<Informe> listaInformes = new ArrayList<Informe>();

    private Grid<Informe> grid = new Grid<>();

    private Button borrarBotonHml = new ObjetosComunes().getBoton("Borrar informe", "Borra el informe seleccionado", "80px", VaadinIcons.MINUS);
    private Button borrarBotonPdf = new ObjetosComunes().getBoton("Borrar informe", "Borra el informe seleccionado", "80px", VaadinIcons.MINUS);

    private Informe informe = null;

    private UI ui;

    private Boolean borradoConfirmado = Boolean.FALSE;

    private Label incidenciaLbl = new Label("Solicitud");

    public FrmJimenaBorraInf(UI uiParam, LopdIncidencia incidenciaParam, Paciente pacienteParam, LocalDate fechaParam) {
        super();
        this.ui = uiParam;
        this.incidencia = incidenciaParam;
        this.paciente = pacienteParam;
        this.fecha = fechaParam;
        this.doHazPantalla();
    }

    public FrmJimenaBorraInf(String nhc, LocalDate fechaParam) {
        super();
        this.paciente = new JimenaDAO().getPaciente(nhc);
        this.fecha = fechaParam;
        if (paciente != null) {
            this.doHazPantalla();
        } else {
            new Notificaciones("Sin paciente para el NHC:" + nhc, true);
        }
    }

    public void doHazPantalla() {
        String ancho = "600px";

       
        // this.addComponent(peticionLbl);
        this.contenedorGrid.setMargin(false);
        this.contenedorHtml.setMargin(false);
        this.contenedorPdf.setMargin(false);

        this.contenedorGrid.setWidth("800px");
        this.contenedorHtml.setWidth("350");
        this.contenedorPdf.setWidth("400px");

    //    this.contenedorGrid.setCaption("<b>Informes  cancal= 5 del paciente:</b>" + paciente.getApellidosnombre() + "<b> NHC: </b>" + paciente.getNumerohc());
     //   this.contenedorGrid.setCaptionAsHtml(true);
        this.contenedorHtml.setCaption("Datos del informe");
        this.contenedorPdf.setCaption("Pdf");

    //    contenedorContenidoHorizontal.setSpacing(true);
      //  contenedorContenidoHorizontal.addComponents(peticionLbl);

      contenedorContenidoHorizontal.setVisible(true);
      contenedorContenidoVertical.setVisible(true);
   //   super.addComponents(contenedorContenidoHorizontal,contenedorContenidoVertical);
     // super.contenedorContenidoVertical.setSpacing(true);
       contenedorContenidoHorizontal.addComponents(contenedorGrid, contenedorHtml, contenedorPdf);

        incidenciaLbl.setCaption(incidencia.getHtmlContenidoSolicitud());
        incidenciaLbl.setWidth("600px");
        incidenciaLbl.setCaptionAsHtml(true);
       // incidenciaLbl.setWidthUndefined();
       incidenciaLbl.setSizeFull();
                
        grid.setWidth("800px");
        grid.addStyleName(ValoTheme.TABLE_COMPACT);
        grid.addColumn(Informe::getFechaHoraInforme).setCaption("Fecha Informe");
        grid.addColumn(Informe::getServicioCodigo).setCaption("Serv");
        grid.addColumn(Informe::getFechaHoraProceso).setCaption("Fecha Proceso");
        //   grid.addColumn(Informe::getServicioCodigo).setCaption("Serv");
        grid.addColumn(Informe::getDescripcion20).setCaption("Informe");
        grid.addColumn(Informe::getUsuarioApellidosNombre).setCaption("Médico");
        grid.setHeightByRows(10);
         grid.addSelectionListener(e -> doSeleccionaInforme());
        contenedorGrid.addComponents(incidenciaLbl, grid, filaPaginacion);
        paginacion = new JimenaDAO().getPaginacionInformes(paciente, Informe.CANAL_DEFECTO);

        borrarBotonHml.addClickListener(e -> borraElInforme());
        borrarBotonPdf.addClickListener(e -> borraElInforme());
        setValoresGridBotones();
    }

    public void borraElInforme() {
        if (informe != null) {
            ConfirmDialog.show(ui, Constantes.CONFIRMACION_TITULO, Constantes.CONFIRMACION_BORRADO_MENSAJE,
                    Constantes.CONFIRMACION_BOTONSI, Constantes.CONFIRMACION_BOTONNO, new ConfirmDialog.Listener() {
                public void onClose(ConfirmDialog dialog) {
                    if (dialog.isConfirmed()) {
                        //  borraElRegistro();
                        RespuestaSql respuesta = new JimenaDAO().doUpdateInformeBorrado(informe.getId());
                        // inserta una nota en la incidencia con la sentencia sql que se ha  ejecutado.
                         RespuestaSql respuestaCampos = new JimenaDAO().doUpdateInformeCampos_i(informe.getId());
                        if (respuesta.getResultado() == true) {
                            LopdNotas nota = new LopdNotas();
                            nota.setIdIncidenciaLong(incidencia.getId());
                            nota.setFecha(LocalDate.now());
                            nota.setHora(Utilidades.getHoraNumeroAcualInteger());
                            nota.setDescripcion(respuesta.getSql() +"\n" + respuestaCampos.getSql());
                            Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
                            nota.setUsucambio(usuario);
                            nota.setEstado(Constantes.BBDD_ACTIVOSI);
                            Long idLong = new LopdIncidenciaDAO().grabaDatosNotas(incidencia, nota);
                            borradoConfirmado = true;
                            cerrarClick();
                        } else {
                            new Notificaciones("Imposible actualizar estado del informe en jimena");
                        }
                    }
                }
            });

        }

    }

    public Boolean getBorradoConfirmado() {
        return borradoConfirmado;
    }

    public void setBorradoConfirmado(Boolean borradoConfirmado) {
        this.borradoConfirmado = borradoConfirmado;
    }

    public void cerrarClick() {

        ui.getCurrent().getWindows().iterator().next().close();

    }

    public void doSeleccionaInforme() {
        grid.setEnabled(false);
        if (grid.getSelectedItems().size() > 0) {
            informe = grid.getSelectedItems().iterator().next();
            informe.setListaCampos(new JimenaDAO().getListaCamposInformes(informe.getId()));
            if (informe.getListaCampos().size() > 0) {
                Label lblConetnido = new Label();
                lblConetnido.setContentMode(ContentMode.HTML);
                lblConetnido.setValue(informe.getHtmlCabecera() + "<hr>" + informe.getHtmlCampos_i());
                lblConetnido.setWidth("350px");
                contenedorHtml.removeAllComponents();
                contenedorHtml.addComponents(borrarBotonHml, lblConetnido);
                contenedorHtml.setVisible(true);
            } else {
                contenedorHtml.setVisible(false);
            }
            File fichero = new JimenaDAO().getFicheroPdfInforme(informe.getId());
            if (fichero != null) {
                Embedded pdf = new Embedded("", new FileResource(fichero));
                pdf.setMimeType("application/pdf");
                pdf.setType(Embedded.TYPE_BROWSER);
                pdf.setWidth("390px");
                pdf.setHeight("610px");
                contenedorPdf.removeAllComponents();
                contenedorPdf.addComponents(borrarBotonPdf, pdf);
                contenedorPdf.setVisible(true);
            } else {
                contenedorPdf.setVisible(false);
            }
        } else {
            new Notificaciones("Sin seleccionar dato");
        }
        grid.setEnabled(true);
    }

    @Override
    public void setValoresGridBotones() {
        listaInformes = new JimenaDAO().getListaInformesPaciPaciente(paginacion, paciente, Informe.CANAL_DEFECTO, Informe.ORDENFECHADESC);
        grid.setItems(listaInformes);
        setValoresPgHeredados(listaInformes);
        if (listaInformes.size() == 0) {
            new Notificaciones(Notificaciones.FORMULARIONOHAYDATOSPARACRITERIO);
        } else {
            grid.setItems(listaInformes);
            if (listaInformes.size() > 0) {
                grid.select(listaInformes.get(0));
                doSeleccionaInforme();
            }
        }
    }

    public void setValoresPgHeredados(ArrayList<Informe> listaInformes) {
        ArrayList<Integer> listaValoresArrayList = new ArrayList<>();
        for (Informe informe : listaInformes) {
            listaValoresArrayList.add(informe.getNumeroOrden());
        }
        setBotonesPaginacion(listaValoresArrayList);
    }

}
