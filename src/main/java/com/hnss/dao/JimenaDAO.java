package com.hnss.dao;

import com.hnss.entidades.Campos_i;
import com.hnss.entidades.Centro;
import com.hnss.entidades.Informe;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.Paciente;
import com.hnss.entidades.PagiLisReg;
import com.hnss.entidades.RespuestaSql;
import com.hnss.entidades.Servicio;
import com.hnss.entidades.Usuario;
import com.hnss.lopdcaa.MyUI;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Parametros;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class JimenaDAO {

    private static final Logger logger = LogManager.getLogger(JimenaDAO.class);

    private static final String sqlInforme = "i.*"
            + ",p.ape1,p.ape2,p.nombre,p.id as idpaciente,p.fnac,p.sexo,p.tarjeta,p.nss,p.dni,p.telefono,p.movil,p.nbdp,p.cias"
            + ",h.nhc"
            + ", c.id as idcentro,c.codigo as codigocentro, c.descripcion as descentro,c.nemonico "
            + " , s.id as idservicio, s.codigo as codigoservicio, s.descripcion as descservcicio  "
            + ",u.userid as usuuserid, u.apellido1 as usuapellido1,u.apellido2 as usuapellido2,u.nombre as usunombre, u.categoria as usucategoria,u.estado as usuestado "
            + " FROM informes i "
            + " JOIN centros c ON c.id=i.centro "
            + " JOIN servicios s ON s.id=i.servicio "
            + " JOIN pacientes p ON p.id=i.paciente  "
            + " JOIN historias h ON h.paciente=p.id "
            + " LEFT JOIN usuarios U ON u.userid=i.userid "
            + " WHERE 1=1 ";

    public JimenaDAO() {
    }

    public Connection conecta() {
        Connection conn = null;
        String persistencia = ((String) MyUI.objParametros.get(Parametros.KEY_PERSISTENCIA)).trim();
        if (persistencia.equals(Constantes.MYSQL_STRING)) {
            String dbURL2 = "jdbc:mysql://localhost:8889/hcel";
            String username = "root";
            String password = "root";

            try {
                // Class.forName("oracle.jdbc.OracleDriver");
                Class.forName("com.mysql.jdbc.Driver");
                conn = DriverManager.getConnection(dbURL2, username, password);
            } catch (ClassNotFoundException ex) {
                logger.error("Error de clase ", ex);
            } catch (SQLException ex) {
                logger.error("Error sql  ", ex);
            }
        }

        if (persistencia.equals(Constantes.ORACLE_STRING)) {
            String dbURL2 = "jdbc:oracle:thin:@10.36.64.164:1525:exhnss01";
            String username = "USR_HCEL";
            String password = "ydda36hU";
            try {
                Class.forName("oracle.jdbc.OracleDriver");
                conn = DriverManager.getConnection(dbURL2, username, password);
                logger.debug("Conexion con bbdd" + dbURL2 + " realizada.");
            } catch (ClassNotFoundException ex) {
                logger.error("Error de conexión con bbdd ", ex);
            } catch (SQLException ex) {
                logger.error("Error de conexión sql con bbdd ", ex);
            }
        }
        return conn;
    }

    public Usuario getUsuario(String dni) {
        String sql;
        Connection conn = this.conecta();
        Usuario usuario = null;
        if (conn != null) {
            sql = " SELECT  * FROM usuarios WHERE userid='" + dni.trim().toUpperCase() + "'";
            try {
                Statement statement = conn.createStatement();
                ResultSet resulSet = statement.executeQuery(sql);
                if (resulSet.next()) {
                    usuario = getUsuarioResulset(resulSet);
                }
                statement.close();
                logger.debug(sql);
            } catch (SQLException e1) {
                logger.error(sql, e1);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
                }
            }
        }
        return usuario;
    }

    public Usuario getUsuarioResulset(ResultSet resulSet) {
        Usuario usuario = null;
        try {
            usuario = new Usuario();
            usuario.setDni(resulSet.getString("userid"));
            usuario.setApellido1(resulSet.getString("apellido1"));
            usuario.setApellido2(resulSet.getString("apellido2"));
            usuario.setNombre(resulSet.getString("nombre"));
            usuario.setMail(resulSet.getString("email"));
            usuario.setEstado(resulSet.getInt("estado"));
            usuario.setTelefono(resulSet.getString("telefono"));
        } catch (SQLException e) {
            logger.error(Notificaciones.SQLERRORRESULSET, e);
        }
        return usuario;
    }

    public Paciente getPaciente(String nhc) {
        String sql;
        Connection conn = this.conecta();
        Paciente paciente = null;
        if (conn != null) {
            sql = " SELECT  p.*,h.nhc FROM pacientes  p " + "JOIN historias h ON h.paciente = p.id " + " WHERE  nhc='"
                    + nhc + "' ";
            try {
                Statement statement = conn.createStatement();
                ResultSet resulSet = statement.executeQuery(sql);
                if (resulSet.next()) {
                    paciente = getPacienteResulset(resulSet);
                }
                statement.close();
                logger.debug(sql);
            } catch (SQLException e1) {
                logger.error(sql, e1);
            } finally {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
                }
            }
        }
        return paciente;
    }

    public Paciente getPacienteResulset(ResultSet resulSet) {
        Paciente paciente = new Paciente();
        try {

            /*
			 * ID APELLIDOSNOMBRE NUMEROHC ESTADO FECHACAMBIO USUCAMBIO IDJIMENA
             */
            String apellidosnombre = resulSet.getString("ape1").trim() + " " + resulSet.getString("ape2").trim() + ","
                    + resulSet.getString("nombre").trim();
            paciente.setIdJimena(resulSet.getLong("id"));
            paciente.setApellidosnombre(apellidosnombre);
            paciente.setNumerohc(resulSet.getString("nhc"));
            paciente.setEstado(Constantes.BBDD_ACTIVOSI);
        } catch (SQLException e) {
            logger.error(Notificaciones.SQLERRORRESULSET, e);
        }
        return paciente;
    }

    public PagiLisReg getPaginacionInformes(Paciente paciente, Integer canal) {
        String sql = "";
        Connection connection = this.conecta();
        PagiLisReg paginacion = new PagiLisReg(0, 0, 0, 0, 0, 1);
        int contador = 0;
        try {
            sql = "SELECT count(*) as numero   FROM informes i JOIN historias h ON h.paciente = i.paciente WHERE 1=1 ";
            sql = sql.concat(" AND i.estado=" + Informe.INFORME_ESTADO_CONSOLIDADO + " AND h.nhc='" + paciente.getNumerohc() + "'");
            if (canal != null) {
                sql = sql.concat("   AND i.canal=" + canal);
            }
            logger.debug(sql);
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            if (resulSet.next()) {
                contador = resulSet.getInt("numero");
            }
            paginacion.setPrimero(1);
            paginacion.setUltimo(contador);
            paginacion.setRegistrosTotales(contador);
            statement.close();
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error(sql);
            logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
        } catch (Exception e) {
            logger.error("Error.", e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
            }
        }
        return paginacion;
    }

    /*
    El paciente que pasamos como parámetros es un paciente LOPD
    por lo que buscamos por NHC en lugar de por el id
     */
    public ArrayList<Informe> getListaInformesPaciPaciente(PagiLisReg paginacion, Paciente paciente, Integer canal, int orden) {
        String sql = "";
        Connection connection = this.conecta();
        ArrayList<Informe> listaInformes = new ArrayList<Informe>();
        if (paciente != null) {
            try {
                switch (orden) {
                    case Informe.ORDENFECHA:
                        sql = " SELECT   row_number() over (ORDER BY i.fecha,i.hora  ) as numeroorden , ";
                        break;
                    case Informe.ORDENFECHADESC:
                        sql = " SELECT   row_number() over (ORDER BY i.fecha desc,i.hora desc ) as numeroorden ,";
                        break;
                }
                sql = sql.concat(sqlInforme);
                sql = sql.concat(" AND i.estado=" + Informe.INFORME_ESTADO_CONSOLIDADO + " AND h.nhc='" + paciente.getNumerohc() + "'");
                if (canal != null) {
                    sql = sql.concat("   AND i.canal=" + canal);
                }
                switch (orden) {
                    case Informe.ORDENFECHA:
                        sql = sql.concat("  ORDER BY i.fecha,hora ");
                        break;
                    case Informe.ORDENFECHADESC:
                        sql = sql.concat("  ORDER BY i.fecha DESC, i.hora DESC");
                        break;
                }

                logger.debug(sql);
                Statement statement = connection.createStatement();
                ResultSet resulSet = statement.executeQuery(sql);
                int contador = 0;
                while (resulSet.next()) {
                    Centro centroBd = new Centro(resulSet.getLong("idcentro"), resulSet.getString("codigocentro"),
                            resulSet.getString("descentro"), resulSet.getString("nemonico"));
                    Servicio servicioBd = new Servicio();
                    servicioBd.setAsistencial(Boolean.TRUE);
                    servicioBd.setCodigo(resulSet.getString("codigoservicio"));
                    servicioBd.setDescripcion(resulSet.getString("descservcicio"));
                    servicioBd.setIdjimena(resulSet.getLong("idservicio"));
                    Usuario usuarioBd = Usuario.getUsuairoResulSetJimena(resulSet, null);
                    Informe informe = Informe.getInformeResulsetJimena(resulSet, false, paciente, centroBd, servicioBd, usuarioBd);
                    informe.setNumeroOrden(resulSet.getInt("numeroorden"));
                    if (paginacion != null) {
                        if (paginacion.getDireccion() == 1) {
                            if (resulSet.getInt("numeroorden") > paginacion.getAnterior()) {
                                listaInformes.add(informe);
                                contador++;
                                if (contador >= paginacion.getNumeroRegistrosPagina()) {
                                    break;
                                }
                            }
                        } else {
                            if (resulSet.getInt("numeroorden") >= paginacion.getAnterior()) {
                                listaInformes.add(informe);
                                contador++;
                                if (contador >= paginacion.getNumeroRegistrosPagina()) {
                                    break;
                                }
                            }
                        }
                    } else {
                        informe.setNumeroOrden(resulSet.getInt("numeroorden"));
                        listaInformes.add(informe);
                        contador++;
                    }
                }
                statement.close();
            } catch (SQLException e) {
                logger.error(sql, e);
            } catch (Exception e) {
                logger.error(e);
            }

            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
            }
        }
        return listaInformes;
    }

    /**
     *
     * @param id
     * @return
     */
    public java.sql.Blob getBlobPdfInforme(Long id, Connection connectionParam) {
        String sql = "";
        Connection connection;
        if (connectionParam != null) {
            connection = connectionParam;
        } else {
            connection = this.conecta();
        }
        Statement statement;
        java.sql.Blob pdfBlob = null;
        try {
            sql = "SELECT docubin  FROM informes  WHERE id=" + id;
            logger.debug(sql);
            statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            if (resulSet.next()) {
                pdfBlob = resulSet.getBlob("docubin");
            }
            statement.close();
            logger.debug(sql);
        } catch (SQLException e) {
            logger.error(sql + id);

        } catch (Exception e) {
            logger.error(Notificaciones.EXCEPTION_ERROR, e);
        }
        try {
            if (connectionParam == null) 
                connection.close();
            }catch (SQLException e) {
            logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
        }
            return pdfBlob;
        }

    

    public File getFicheroPdfInforme(Long id) {
        File file = null;
        DateTimeFormatter fechadma = DateTimeFormatter.ofPattern("YYYYMMddmmss");
        String cadenaNombre = fechadma.format(LocalDateTime.now());
        FileOutputStream outpu = null;
        InputStream inStream = null;
        Connection  connection = this.conecta();
        try {
            String pathname = Constantes.DIRECTORIOREPORTS + System.getProperty("file.separator") + "Informe" + id
                    + cadenaNombre + ".pdf";
            file = new File(pathname);
            outpu = new FileOutputStream(file);
            Blob archivo = this.getBlobPdfInforme(id,connection);
            inStream = archivo.getBinaryStream();
            int size = (int) archivo.length();
            byte[] buffer = new byte[size];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                outpu.write(buffer, 0, length);
            }

        } catch (Exception ioe) {
            logger.error(ioe);
        } finally {
            try {
                outpu.close();
                inStream.close();
                connection.close();
            } catch (Exception ioe) {
                logger.error("Cerrendo objetos de conexiones ",ioe);
            }
        }

        return file;
    }

    public ArrayList<Campos_i> getListaCamposInformes(Long id) {
        String sql = "";
        Connection connection = this.conecta();
        Statement statement;
        ArrayList<Campos_i> listaCampos = new ArrayList<>();
        try {
            sql = "SELECT *  FROM campos_i  WHERE informe = " + id + " AND estado=" + Informe.INFORME_ESTADO_CONSOLIDADO + " ORDER BY  id";
            logger.debug(sql);

            statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            while (resulSet.next()) {
                Campos_i campo_i = Campos_i.getCamposResulSet(resulSet);
                listaCampos.add(campo_i);
            }
            statement.close();
        } catch (SQLException e) {
            logger.error(sql, e);
        } catch (Exception e) {
            logger.error(Notificaciones.EXCEPTION_ERROR, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
            }
        }
        return listaCampos;
    }
    
    /*
    Este método retorna un objeto RespuestaSql para insertar  los resultados 
    en la tabla de acciones asociado a la incidencia
    */
     public RespuestaSql doUpdateInformeBorrado(Long id) {
        String sql = "";
        Connection connection = this.conecta();
        PreparedStatement statement;
         ResultSet resulSet ;
         RespuestaSql respuesta= new RespuestaSql(false,"");
         Boolean actualizado=false;
        try {
            sql = "UPDATE  informes SET estado= ?  WHERE id = ?"  ;
            String sqlMsg= "UPDATE  informes SET estado= "+Informe.INFORME_ESTADO_SUSTITUIDO+"  WHERE id = "+id  ;
            logger.debug(sqlMsg);
            statement = connection.prepareStatement(sql);
             statement.setLong(1, Informe.INFORME_ESTADO_SUSTITUIDO);
            statement.setLong(2, id);
            respuesta.setResultado( statement.executeUpdate() > 0);
            respuesta.setSql(sqlMsg);
            statement.close();
        } catch (SQLException e) {
            logger.error(sql, e);
        } catch (Exception e) {
            logger.error(Notificaciones.EXCEPTION_ERROR, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
            }
        }
        return respuesta;
    }
     
       public RespuestaSql doUpdateInformeCampos_i(Long id) {
        String sql = "";
        Connection connection = this.conecta();
        PreparedStatement statement;
         ResultSet resulSet ;
         RespuestaSql respuesta= new RespuestaSql(false,"");
         Boolean actualizado=false;
        try {
            sql = "UPDATE  campos_i SET estado= ?  WHERE informe = ?"  ;
            String sqlMsg= "UPDATE  campos_i SET estado= "+Informe.INFORME_ESTADO_SUSTITUIDO+"  WHERE informe = "+id  ;
            logger.debug(sqlMsg);
            statement = connection.prepareStatement(sql);
             statement.setLong(1, Informe.INFORME_ESTADO_SUSTITUIDO);
            statement.setLong(2, id);
            respuesta.setResultado( statement.executeUpdate() > 0);
            respuesta.setSql(sqlMsg);
            statement.close();
        } catch (SQLException e) {
            logger.error(sql, e);
        } catch (Exception e) {
            logger.error(Notificaciones.EXCEPTION_ERROR, e);
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
            }
        }
        return respuesta;
    }
}
