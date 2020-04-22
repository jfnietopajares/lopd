/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hnss.entidades;

/**
 *
 * @author 06551256M
 */
public class RespuestaSql {
    private Boolean resultado;
    private String sql;
    
    public RespuestaSql(){
        
    }
 public RespuestaSql(Boolean resultadoParam, String sqlParam){
        this.resultado=resultadoParam;
        this.sql=sqlParam;
    }
    public Boolean getResultado() {
        return resultado;
    }

    public void setResultado(Boolean resultado) {
        this.resultado = resultado;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }
    
}
