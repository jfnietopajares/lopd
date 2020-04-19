package com.hnss.entidades;

import java.sql.Blob;

/**
 * The Class Campos_r.
 *
 *
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class Campos_r {

    private Long id;

    private Long registro;

    private Long item;

    private String userid;

    private Long fecha;

    private Long hora;

    private String descripcion;

    private int orden;

    private String unidades;

    private String flags;

    private String codigo;

    private String tipo_codigo;

    private int estado;

    private int tipobin;

    private String dato;

    private String referencias;

    private Blob datobin;

    private Long lateralidad;

    private Long canal;

    /**
     * Instantiates a new campos r.
     */
    public Campos_r() {
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the registro.
     *
     * @return the registro
     */
    public Long getRegistro() {
        return registro;
    }

    /**
     * Sets the registro.
     *
     * @param registro the new registro
     */
    public void setRegistro(Long registro) {
        this.registro = registro;
    }

    /**
     * Gets the item.
     *
     * @return the item
     */
    public Long getItem() {
        return item;
    }

    /**
     * Sets the item.
     *
     * @param item the new item
     */
    public void setItem(Long item) {
        this.item = item;
    }

    /**
     * Gets the userid.
     *
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }

    /**
     * Sets the userid.
     *
     * @param userid the new userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * Gets the fecha.
     *
     * @return the fecha
     */
    public Long getFecha() {
        return fecha;
    }

    /**
     * Sets the fecha.
     *
     * @param fecha the new fecha
     */
    public void setFecha(Long fecha) {
        this.fecha = fecha;
    }

    /**
     * Gets the hora.
     *
     * @return the hora
     */
    public Long getHora() {
        return hora;
    }

    /**
     * Sets the hora.
     *
     * @param hora the new hora
     */
    public void setHora(Long hora) {
        this.hora = hora;
    }

    /**
     * Gets the descripcion.
     *
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Sets the descripcion.
     *
     * @param descripcion the new descripcion
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * Gets the orden.
     *
     * @return the orden
     */
    public int getOrden() {
        return orden;
    }

    /**
     * Sets the orden.
     *
     * @param orden the new orden
     */
    public void setOrden(int orden) {
        this.orden = orden;
    }

    /**
     * Gets the unidades.
     *
     * @return the unidades
     */
    public String getUnidades() {
        return unidades;
    }

    /**
     * Sets the unidades.
     *
     * @param unidades the new unidades
     */
    public void setUnidades(String unidades) {
        this.unidades = unidades;
    }

    /**
     * Gets the flags.
     *
     * @return the flags
     */
    public String getFlags() {
        return flags;
    }

    /**
     * Sets the flags.
     *
     * @param flags the new flags
     */
    public void setFlags(String flags) {
        this.flags = flags;
    }

    /**
     * Gets the codigo.
     *
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Sets the codigo.
     *
     * @param codigo the new codigo
     */
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    /**
     * Gets the tipo codigo.
     *
     * @return the tipo codigo
     */
    public String getTipo_codigo() {
        return tipo_codigo;
    }

    /**
     * Sets the tipo codigo.
     *
     * @param tipo_codigo the new tipo codigo
     */
    public void setTipo_codigo(String tipo_codigo) {
        this.tipo_codigo = tipo_codigo;
    }

    /**
     * Gets the estado.
     *
     * @return the estado
     */
    public int getEstado() {
        return estado;
    }

    /**
     * Sets the estado.
     *
     * @param estado the new estado
     */
    public void setEstado(int estado) {
        this.estado = estado;
    }

    /**
     * Gets the tipobin.
     *
     * @return the tipobin
     */
    public int getTipobin() {
        return tipobin;
    }

    /**
     * Sets the tipobin.
     *
     * @param tipobin the new tipobin
     */
    public void setTipobin(int tipobin) {
        this.tipobin = tipobin;
    }

    /**
     * Gets the dato.
     *
     * @return the dato
     */
    public String getDato() {
        return dato;
    }

    /**
     * Sets the dato.
     *
     * @param dato the new dato
     */
    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getDescripyDato() {
        return descripcion.trim() + ":" + dato.trim();
    }

    /**
     * Gets the referencias.
     *
     * @return the referencias
     */
    public String getReferencias() {
        return referencias;
    }

    /**
     * Sets the referencias.
     *
     * @param referencias the new referencias
     */
    public void setReferencias(String referencias) {
        this.referencias = referencias;
    }

    /**
     * Gets the datobin.
     *
     * @return the datobin
     */
    public Blob getDatobin() {
        return datobin;
    }

    /**
     * Sets the datobin.
     *
     * @param datobin the new datobin
     */
    public void setDatobin(Blob datobin) {
        this.datobin = datobin;
    }

    /**
     * Gets the lateralidad.
     *
     * @return the lateralidad
     */
    public Long getLateralidad() {
        return lateralidad;
    }

    /**
     * Sets the lateralidad.
     *
     * @param lateralidad the new lateralidad
     */
    public void setLateralidad(Long lateralidad) {
        this.lateralidad = lateralidad;
    }

    /**
     * Gets the canal.
     *
     * @return the canal
     */
    public Long getCanal() {
        return canal;
    }

    /**
     * Sets the canal.
     *
     * @param canal the new canal
     */
    public void setCanal(Long canal) {
        this.canal = canal;
    }

}
