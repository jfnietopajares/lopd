package com.hnss.dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.lopdcaa.MyUI;
import com.hnss.utilidades.Parametros;

/**
 * The Class ConexionDao.
 *
 * @author JuanNieto
 * @version 23.5.2018
 */
public class ConexionDAO {

	private DataSource dataSource = null;

	private final Logger logger = LogManager.getLogger(ConexionDAO.class);

	private InitialContext ctx;

	protected String persistencia = null;

	protected String sql;

	public final static String ERROR_BBDD_SIN_CONEXION = "No se ha podido establecer la conexión con la base de datos ";

	public final static String ERROR_BBDD_SQL = "Error en sentencia SQL. ";

	public final static String ERROR_CLOSE_BBDD_SQL = "Error cerrando conexión. ";

	public final static String ERROR_BBDD_CONTEXTO = "Error iniciando contexto.";

	public ConexionDAO() {
		persistencia = ((String) MyUI.objParametros.get(Parametros.KEY_PERSISTENCIA)).trim();
	}

	/**
	 * Gets the conexion BBDD.
	 *
	 * @return the conexion BBDD
	 */
	public Connection getConexionBBDD() {
		try {

			if (dataSource == null) {
				ctx = new InitialContext();
				if (ctx == null) {
					throw new Exception(ConexionDAO.ERROR_BBDD_CONTEXTO);
				} else {
					// dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/HCEL");

					dataSource = (DataSource) ctx.lookup("java:comp/env/jdbc/dataSourceHnss");

					if (dataSource != null)
						return dataSource.getConnection();
					else
						logger.error(ConexionDAO.ERROR_BBDD_SIN_CONEXION);
				}
			} else {
				return dataSource.getConnection();
			}
		} catch (Exception e) {
			logger.error(ConexionDAO.ERROR_BBDD_SIN_CONEXION, e);
		}
		return null;
	}
}
