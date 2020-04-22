package com.hnss.ui;

import com.hnss.entidades.PagiLisReg;
import com.vaadin.icons.VaadinIcons;
import java.util.ArrayList;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

/**
 * The Class Pantalla_Paginacion.
 *
 * @author JuanNieto
 *
 * @author Juan Nieto
 * @version 23.5.2018
 *
 * Clase abstracta que definen los botones de paginación de las listas de datos
 * paginadas y los eventos de teclado comunes a los botones
 */
public abstract class PantallaPaginacion extends VerticalLayout {

    protected HorizontalLayout filaPaginacion = new HorizontalLayout();

    protected HorizontalLayout contenedorContenidoHorizontal = new HorizontalLayout();
    protected VerticalLayout contenedorContenidoVertical = new VerticalLayout();
    /**
     * The paginacion.
     */
    protected PagiLisReg paginacion = new PagiLisReg();

    /**
     * Botones.
     */
    protected Button primero, anterior, siguiente, ultimo, registros;

    /**
     * The numero registros.
     */
    protected Label numeroRegistros;

    /**
     * Instantiates a new pantalla paginacion.
     */
    public PantallaPaginacion() {
        this.setSizeFull();
        this.setMargin(false);
        this.setSpacing(false);
        this.setResponsive(true);
        this.addComponents(contenedorContenidoHorizontal);

        contenedorContenidoHorizontal.setMargin(false);
        contenedorContenidoHorizontal.setSpacing(false);
        contenedorContenidoHorizontal.setResponsive(false);

        contenedorContenidoVertical.setMargin(false);
        contenedorContenidoVertical.setSpacing(false);
        contenedorContenidoVertical.setResponsive(false);

        filaPaginacion.setMargin(false);
        filaPaginacion.setSpacing(false);
        filaPaginacion.setResponsive(false);

        primero = new ObjetosComunes().getBoton(null, "Primero", "15px", VaadinIcons.FAST_BACKWARD);
        primero.addClickListener(event -> clickPrimero());

        anterior = new ObjetosComunes().getBoton(null, "Anterior", "15px", VaadinIcons.BACKWARDS);
        anterior.addClickListener(event -> clickAnterior());

        numeroRegistros = new Label();
        numeroRegistros.setValue(" Reg. Total ");

        siguiente = new ObjetosComunes().getBoton(null, "Siguiente", "15px", VaadinIcons.FORWARD);
        siguiente.addClickListener(event -> clickSiguiente());

        ultimo = new ObjetosComunes().getBoton(null, "Úlimo", "15px", VaadinIcons.FAST_BACKWARD);
        ultimo.addClickListener(event -> clickUltimo());
        filaPaginacion.addComponents(primero, anterior, numeroRegistros, siguiente, ultimo);
        filaPaginacion.setEnabled(true);

    }

    /**
     * Métodos de control de botones de paginación.
     */
    /**
     * Ha click el boton primero.
     */
    public void clickPrimero() {
        this.setEnabled(false);
        paginacion.setAnterior(0);
        paginacion.setSiguiente(paginacion.getAnterior() + paginacion.getNumeroRegistrosPagina());
        setValoresGridBotones();
        this.setEnabled(true);
    }

    /**
     * Click anterior.
     */
    public void clickAnterior() {
        this.setEnabled(false);
        // orden ascendente
        if (paginacion.getOrden() == 0) {
            paginacion.setAnterior(paginacion.getAnterior() - paginacion.getNumeroRegistrosPagina());
            if (paginacion.getAnterior() < 1) {
                paginacion.setAnterior(1);
            }
            paginacion.setSiguiente(paginacion.getAnterior() + paginacion.getNumeroRegistrosPagina());
            paginacion.setDireccion(-1);
            setValoresGridBotones();
        } else {
            // orden descendente
            paginacion.setAnterior(paginacion.getAnterior() - paginacion.getNumeroRegistrosPagina());
            if (paginacion.getAnterior() < 1) {
                paginacion.setAnterior(1);
            }
            paginacion.setSiguiente(paginacion.getAnterior() + paginacion.getNumeroRegistrosPagina());
            paginacion.setDireccion(-1);
            setValoresGridBotones();
        }
        anterior.setCaption(Integer.toString(paginacion.getAnterior()));
        siguiente.setCaption(Integer.toString(paginacion.getSiguiente()));
        this.setEnabled(true);
    }

    /**
     * Click siguiente.
     */
    public void clickSiguiente() {
        this.setEnabled(false);
        paginacion.setAnterior(paginacion.getSiguiente());
        paginacion.setSiguiente(paginacion.getAnterior() + paginacion.getNumeroRegistrosPagina());
        paginacion.setDireccion(1);
        setValoresGridBotones();
        this.setEnabled(true);
    }

    /**
     * Click ultimo.
     */
    public void clickUltimo() {
        this.setEnabled(false);
        paginacion.setAnterior(paginacion.getUltimo() - paginacion.getNumeroRegistrosPagina());
        paginacion.setSiguiente(paginacion.getUltimo());
        paginacion.setDireccion(1);
        setValoresGridBotones();
        this.setEnabled(true);
    }

    /**
     * Este método debe ser sobreescrito por las clases que hereden. ya que
     * tienen que cargar el arraylist de registros de la tabla para luego llamar
     * a setBotonesPaginacion que carga los valores y habilita los botones de
     * navegación activos.
     */
    public abstract void setValoresGridBotones();

    /**
     * Sets the botones paginacion.
     *
     * @param listaNordenes Lista de los numeros de orden de los registros que
     * se estan tabulando.
     *
     * Es llamado por las clases hijas
     *
     * Asigna los valores de los botoens de paginación en función de las lista
     * de numero de orden de los registros seleccionados
     *
     * Es llamado por el método de clase setValoresPgHeredados
     */
    public void setBotonesPaginacion(ArrayList<Integer> listaNordenes) {

        if (listaNordenes.size() > 0) {
            filaPaginacion.setEnabled(true);
            ultimo.setCaption(Integer.toString(paginacion.getUltimo()));
            primero.setCaption(Integer.toString(paginacion.getPrimero()));
            // orden ascendente 

            paginacion.setAnterior(listaNordenes.get(0));
            paginacion.setSiguiente(listaNordenes.get(listaNordenes.size() - 1));

            anterior.setCaption(Integer.toString(paginacion.getAnterior()));
            siguiente.setCaption(Integer.toString(paginacion.getSiguiente()));

            if (paginacion.getRegistrosTotales() <= paginacion.getNumeroRegistrosPagina()) {
                filaPaginacion.setEnabled(false);
                // filaPaginacion.setVisible(false);
            } else {
                filaPaginacion.setEnabled(true);
                // filaPaginacion.setVisible(true);
            }

            if (Integer.parseInt(siguiente.getCaption()) >= Integer.parseInt(ultimo.getCaption())) {
                ultimo.setEnabled(false);
                siguiente.setEnabled(false);
            } else {
                ultimo.setEnabled(true);
                siguiente.setEnabled(true);
            }
            if (Integer.parseInt(anterior.getCaption()) == Integer.parseInt(primero.getCaption())) {
                primero.setEnabled(false);
                anterior.setEnabled(false);
            } else {
                anterior.setEnabled(true);
                primero.setEnabled(true);
            }
        }

        numeroRegistros.setValue(" Total :" + paginacion.getRegistrosTotales() + "  de " + paginacion.getAnterior()
                + " a " + paginacion.getSiguiente());

    }

    public HorizontalLayout getContenedorContenido() {
        return contenedorContenidoHorizontal;
    }

    public void setContenedorContenido(HorizontalLayout contenedorContenidoHorizontal) {
        this.contenedorContenidoHorizontal = contenedorContenidoHorizontal;
    }

}
