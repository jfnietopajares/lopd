package com.hnss.entidades;

/**
 * The Class Centro.
 *
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class Centro {

    private int numeroOrden;

    private Long id;

    private String codigo;

    private String descripcion;

    private String area;

    private String nemonico;

    private String gerente;

    private String cargo;

    private String localidad;

    private String provincia;

    private String telefono;

    private String fax;

    private String cp;

    private Integer tipo;

    private String centroreferencia;

    private String direccion;

    public final static Centro CENTRO_DEFECTO = new Centro(new Long(1), "050101", "Hospital Nuestra Señora de Sonsoles",
            "HNSS");

    public final static Centro HNSS = new Centro(new Long(1), "050101", "Hospital Nuestra Señora de Sonsoles", "HNSS");
    public final static Centro PROVINCIAL = new Centro(new Long(2), "050102", "Provincial de Ávila", "PROVINCIAL");
    public final static Centro CEPAVILA = new Centro(new Long(115), "050103", "Centro de Especialidades de Ávila",
            "CEPAVILA");
    public final static Centro CEPAREANAS = new Centro(new Long(116), "050101",
            "Centro de Especialidades de Arenas de San Pedro", "CEPARENAS");

    public final static int TIPO_CENTRO_HOSPITAL = 1;

    public final static int TIPO_CENTRO_CEP = 4;

    /**
     * Instantiates a new centro.
     */
    public Centro() {
        this.id = new Long(0);
    }

    /**
     * Instantiates a new centro.
     *
     * @param id the id
     */
    public Centro(Long id) {
        this.id = id;
    }

    /**
     * Instantiates a new centro.
     *
     * @param id the id
     * @param codigo the codigo
     * @param descripcion the descripcion
     * @param nemonico the nemonico
     */
    public Centro(Long id, String codigo, String descripcion, String nemonico) {
        this.id = id;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.nemonico = nemonico;
    }

    /**
     * Gets the numero orden.
     *
     * @return the numero orden
     */
    public int getNumeroOrden() {
        return numeroOrden;
    }

    /**
     * Sets the numero orden.
     *
     * @param numeroOrden the new numero orden
     */
    public void setNumeroOrden(int numeroOrden) {
        this.numeroOrden = numeroOrden;
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
     * Gets the string id.
     *
     * @return the string id
     */
    public String getStringId() {
        return Long.toString(getId());
    }

    /**
     * Sets the string id.
     *
     * @param id the new string id
     */
    public void setStringId(String id) {
        this.id = Long.parseLong(id);
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
     * Gets the area.
     *
     * @return the area
     */
    public String getArea() {
        return area;
    }

    /**
     * Sets the area.
     *
     * @param area the new area
     */
    public void setArea(String area) {
        this.area = area;
    }

    /**
     * Gets the nemonico.
     *
     * @return the nemonico
     */
    public String getNemonico() {
        if (nemonico != null) {
            if (nemonico.isEmpty()) {
                return descripcion.substring(6);
            } else {
                return nemonico;
            }
        } else {
            return null;
        }
    }

    /**
     * Sets the nemonico.
     *
     * @param nemonico the new nemonico
     */
    public void setNemonico(String nemonico) {
        this.nemonico = nemonico;
    }

    /**
     * Gets the gerente.
     *
     * @return the gerente
     */
    public String getGerente() {
        return gerente;
    }

    /**
     * Sets the gerente.
     *
     * @param gerente the new gerente
     */
    public void setGerente(String gerente) {
        this.gerente = gerente;
    }

    /**
     * Gets the cargo.
     *
     * @return the cargo
     */
    public String getCargo() {
        return cargo;
    }

    /**
     * Sets the cargo.
     *
     * @param cargo the new cargo
     */
    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    /**
     * Gets the localidad.
     *
     * @return the localidad
     */
    public String getLocalidad() {
        return localidad;
    }

    /**
     * Sets the localidad.
     *
     * @param localidad the new localidad
     */
    public void setLocalidad(String localidad) {
        this.localidad = localidad;
    }

    /**
     * Gets the provincia.
     *
     * @return the provincia
     */
    public String getProvincia() {
        return provincia;
    }

    /**
     * Sets the provincia.
     *
     * @param provincia the new provincia
     */
    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    /**
     * Gets the telefono.
     *
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Sets the telefono.
     *
     * @param telefono the new telefono
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Gets the fax.
     *
     * @return the fax
     */
    public String getFax() {
        return fax;
    }

    /**
     * Sets the fax.
     *
     * @param fax the new fax
     */
    public void setFax(String fax) {
        this.fax = fax;
    }

    /**
     * Gets the cp.
     *
     * @return the cp
     */
    public String getCp() {
        return cp;
    }

    /**
     * Sets the cp.
     *
     * @param cp the new cp
     */
    public void setCp(String cp) {
        this.cp = cp;
    }

    /**
     * Gets the tipo.
     *
     * @return the tipo
     */
    public Integer getTipo() {
        return tipo;
    }

    /**
     * Sets the tipo.
     *
     * @param tipo the new tipo
     */
    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    /**
     * Gets the centroreferencia.
     *
     * @return the centroreferencia
     */
    public String getCentroreferencia() {
        return centroreferencia;
    }

    /**
     * Sets the centroreferencia.
     *
     * @param centroreferencia the new centroreferencia
     */
    public void setCentroreferencia(String centroreferencia) {
        this.centroreferencia = centroreferencia;
    }

    /**
     * Gets the direccion.
     *
     * @return the direccion
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Sets the direccion.
     *
     * @param direccion the new direccion
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * To string.
     *
     * @return the string
     */
    public String toString() {
        return "id=" + getId() + " codigo=" + getCodigo() + " descripcion=" + getDescripcion();
    }
}
