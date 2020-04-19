package com.hnss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;

/**
 * The Class UtilidadesDAO. *
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class UtilidadesDAO extends ConexionDAO {

	private String sql;

	private static final Logger logger = LogManager.getLogger(UtilidadesDAO.class);

	/**
	 * Instantiates a new utilidades dao.
	 */
	public UtilidadesDAO() {
		super();
	}

	/**
	 * Gets the siguiente id.
	 *
	 * @param tabla the tabla
	 * @return the siguiente id
	 */
	public Long getSiguienteId(String tabla) {
		Connection connection = null;
		Long resultado = new Long(0);
		try {
			connection = super.getConexionBBDD();
			if (persistencia.equals(Constantes.MYSQL_STRING)) {
				sql = "SELECT max(id)*1 +1  AS id FROM  " + tabla;
			} else if (persistencia.equals(Constantes.ORACLE_STRING)) {
				String secuencia = "SEC_ID_" + tabla + ".nextval ";
				sql = "select " + secuencia + " AS id from dual ";
			}
			logger.debug(sql);
			PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet res = statement.executeQuery();
			if (res.next()) {
				resultado = res.getLong("id");
				if (resultado.equals(new Long(0)))
					resultado = new Long(1);
			}
			statement.close();
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
		return resultado;
	}
}
