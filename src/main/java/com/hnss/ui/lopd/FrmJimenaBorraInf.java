/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hnss.ui.lopd;

import com.hnss.dao.JimenaDAO;
import com.hnss.entidades.Informe;
import com.hnss.entidades.Paciente;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.ui.FrmMaster;
import com.hnss.ui.Notificaciones;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 *
 * @author 06551256M
 */
public class FrmJimenaBorraInf extends FrmMaster {

    private VerticalLayout contenedorGrid = new VerticalLayout();

    private VerticalLayout contenedorHtml = new VerticalLayout();

    private VerticalLayout contenedorPdf = new VerticalLayout();

    private Paciente paciente;

    private LocalDate fecha = null;

    protected DateTimeFormatter fechadma = DateTimeFormatter.ofPattern("dd/MM/YYYY");

    private ArrayList<Informe> listaInformes = new ArrayList<Informe>();

    private Grid<Informe> grid = new Grid<>();

    public FrmJimenaBorraInf(Paciente pacienteParam, LocalDate fechaParam) {
        this.paciente = pacienteParam;
        this.fecha = fechaParam;
        this.doHazPantalla();
    }

    public FrmJimenaBorraInf(String nhc, LocalDate fechaParam) {
        this.paciente = new JimenaDAO().getPaciente(nhc);
        this.fecha = fechaParam;
        if (paciente!=null) {
        this.doHazPantalla();
        } else {
            new Notificaciones("Sin paciente para el NHC:" + nhc,true );
        }
    }

    public void doHazPantalla() {
        String ancho="500px";
        this.contenedorGrid.setMargin(false);
        this.contenedorHtml.setMargin(false);
        this.contenedorPdf.setMargin(false);
        
        this.contenedorGrid.setWidth(ancho);
        this.contenedorHtml.setWidth(ancho);
        this.contenedorPdf.setWidth(ancho);
        
        this.addComponents(contenedorGrid, contenedorHtml, contenedorPdf);
        listaInformes = new JimenaDAO().getListaInformesPaciPaciente(paciente, Informe.CANAL_DEFECTO,Informe.ORDENFECHADESC);
        grid.setCaption("Informes del paciente:" + paciente.getApellidosnombre()+" NHC:" + paciente.getNumerohc());
        grid.addColumn(Informe::getId).setCaption("Nº");
        grid.addColumn(Informe::getFechaHora).setCaption("Fecha");
         grid.addColumn(Informe::getServicioCodigo).setCaption("Fecha");
        grid.addColumn(Informe::getDescripcion20).setCaption("Informe");
         grid.addColumn(Informe::getUsuarioApellidosNombre).setCaption("Usuario");
        grid.addSelectionListener(e -> seleccionaInforme());
        grid.setItems(listaInformes);
        grid.setWidth(ancho);
        contenedorGrid.addComponents(contenedorBotones, grid);
    }

    public void seleccionaInforme() {

    }

    @Override
    public void cerrarClick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void limpiarClick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void ayudaClick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void grabarClick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borrarClick() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void borraElRegistro() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean doValidaFormulario() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
