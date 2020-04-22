package com.hnss.entidades;



/**
 * The Class PaginacionListaRegistros.
 *
 * *
 *
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class PagiLisReg {

    private int primero = 0;

    private int ultimo = 0;

    private int anterior = 0;

    private int siguiente = 0;

    private int registrosTotales = 0;

    private int direccion = 1;

    private int orden = 0; // 0 ascendente  1 descentente 
    private int numeroRegistrosPagina = 10;

    /**
     * Instantiates a new paginacion lista registros.
     */
    public PagiLisReg() {

    }

    /**
     * Instantiates a new paginacion lista registros.
     *
     * @param primero the primero
     * @param anterior the anterior
     * @param siguiente the siguiente
     * @param ultimo the ultimo
     * @param registrosTotales the registros totales
     * @param direccion the direccion
     */
    public PagiLisReg(int primero, int anterior, int siguiente, int ultimo, int registrosTotales, int direccion) {
        this.primero = primero;
        this.anterior = anterior;
        this.siguiente = siguiente;
        this.ultimo = ultimo;
        this.registrosTotales = registrosTotales;
        this.direccion = direccion;
        this.orden = 0;
    }

    /**
     * Gets the primero.
     *
     * @return the primero
     */
    public int getPrimero() {
        return primero;
    }

    /**
     * Sets the primero.
     *
     * @param primero the new primero
     */
    public void setPrimero(int primero) {
        this.primero = primero;
    }

    /**
     * Gets the ultimo.
     *
     * @return the ultimo
     */
    public int getUltimo() {
        return ultimo;
    }

    /**
     * Sets the ultimo.
     *
     * @param ultimo the new ultimo
     */
    public void setUltimo(int ultimo) {
        this.ultimo = ultimo;
    }

    /**
     * Gets the anterior.
     *
     * @return the anterior
     */
    public int getAnterior() {
        return anterior;
    }

    /**
     * Sets the anterior.
     *
     * @param anterior the new anterior
     */
    public void setAnterior(int anterior) {
        this.anterior = anterior;
    }

    /**
     * Gets the siguiente.
     *
     * @return the siguiente
     */
    public int getSiguiente() {
        return siguiente;
    }

    /**
     * Sets the siguiente.
     *
     * @param siguiente the new siguiente
     */
    public void setSiguiente(int siguiente) {
        this.siguiente = siguiente;
    }

    /**
     * Gets the registros totales.
     *
     * @return the registros totales
     */
    public int getRegistrosTotales() {
        return registrosTotales;
    }

    /**
     * Sets the registros totales.
     *
     * @param registrosTotales the new registros totales
     */
    public void setRegistrosTotales(int registrosTotales) {
        this.registrosTotales = registrosTotales;
    }

    /**
     * Gets the direccion.
     *
     * @return the direccion
     */
    public int getDireccion() {
        return direccion;
    }

    /**
     * Sets the direccion.
     *
     * @param direccion the new direccion
     */
    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }

    /**
     * Gets the numero registros pagina.
     *
     * @return the numero registros pagina
     */
    public int getNumeroRegistrosPagina() {
        return numeroRegistrosPagina;
    }

    public int getOrden() {
        return orden;
    }

    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * Sets the numero registros pagina.
     *
     * @param numeroRegistrosPagina the new numero registros pagina
     */
    public void setNumeroRegistrosPagina(int numeroRegistrosPagina) {
        this.numeroRegistrosPagina = numeroRegistrosPagina;
    }

}
