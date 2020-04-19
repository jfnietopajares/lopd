package com.hnss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.Servicio;
import com.hnss.entidades.Usuario;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.VaadinSession;

public class ServicioDAO extends ConexionDAO {

	private static final Logger logger = LogManager.getLogger(ServicioDAO.class);

	public ServicioDAO() {
		super();
	}

	public boolean grabaDatos(Servicio servicio) {
		boolean actualizado = false;

		if (servicio.getId().equals(new Long(0))) {
			if (!this.insertaServicio(servicio).equals(new Long(0))) {
				actualizado = true;
			}

		} else {
			actualizado = this.actualizaDatos(servicio);
		}
		return actualizado;
	}

	public Long insertaServicio(Servicio servicio) {
		Connection connection = null;
		boolean insertado = false;
		Long id = null;
		Long idusuario = ((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME)).getId();
		try {
			connection = super.getConexionBBDD();
			id = new UtilidadesDAO().getSiguienteId("serviciosgfh");
			servicio.setId(id);
			sql = " INSERT INTO serviciosgfh (id,codigo,idjimena,descripcion,asistencial,estado,fechacambio,usucambio) "
					+ " VALUES (?,?,?,?,?,?,?,?)  ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, servicio.getId());
			statement.setString(2, servicio.getCodigo());
			statement.setLong(3, servicio.getIdjimena());
			statement.setString(4, servicio.getDescripcion());
			statement.setBoolean(5, servicio.getAsistencial());
			statement.setInt(6, Constantes.BBDD_ACTIVOSI);
			statement.setLong(7, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setLong(8,
					((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME)).getId());
			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(
					" INSERT INTO serviciosgfh (id,codigo,idjimena,descripcion,asistencial,estado,fechacambio,usucambio) "
							+ " VALUES (" + servicio.getId() + "," + servicio.getCodigo() + "," + servicio.getIdjimena()
							+ ",'" + servicio.getDescripcion() + "," + servicio.getAsistencial() + ","
							+ Constantes.BBDD_ACTIVOSI + "," + Utilidades.getFechayyymmdd(LocalDate.now()) + ","
							+ idusuario + ")");
		} catch (SQLException e) {
			logger.debug(
					" INSERT INTO serviciosgfh (id,codigo,idjimena,descripcion,asistencial,estado,fechacambio,usucambio) "
							+ " VALUES (" + servicio.getId() + "," + servicio.getCodigo() + "," + servicio.getIdjimena()
							+ ",'" + servicio.getDescripcion() + "," + servicio.getAsistencial() + ","
							+ Constantes.BBDD_ACTIVOSI + "," + Utilidades.getFechayyymmdd(LocalDate.now()) + ","
							+ idusuario + ")",
					e);

		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return id;
	}

	public boolean actualizaDatos(Servicio servicio) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			connection = super.getConexionBBDD();
			int valor = servicio.getAsistencial() ? 1 : 0;
			sql = " UPDATE serviciosgfh SET codigo='" + servicio.getCodigo() + "',idjimena=" + servicio.getIdjimena()
					+ ",descripcion='" + servicio.getDescripcion() + "',asistencial=" + valor + ",fechacambio="
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ",usucambio="
					+ ((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME)).getId()
					+ " WHERE id=" + servicio.getId();
			Statement statement = connection.createStatement();
			insertadoBoolean = statement.execute(sql);
			insertadoBoolean = true;
			statement.close();
			logger.debug(sql);
		} catch (

		SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return insertadoBoolean;
	}

	public boolean borraDatos(Servicio servicio) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuarioa = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();

			sql = " UPDATE serviciosgfh SET   usucambio='" + usuarioa.getDni() + "',  fechacambio="
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ",estado=" + Constantes.BBDD_ACTIVONO + " WHERE id="
					+ usuarioa.getId();
			Statement statement = connection.createStatement();
			insertadoBoolean = statement.execute(sql);
			insertadoBoolean = true;
			statement.close();
			logger.debug(sql);
		} catch (

		SQLException e) {
			logger.error(sql, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return insertadoBoolean;
	}

	public ArrayList<Servicio> getListaServicios() {
		Connection connection = null;
		ArrayList<Servicio> lista = new ArrayList<Servicio>();

		try {
			connection = super.getConexionBBDD();

			sql = " SELECT * FROM  serviciosgfh WHERE estado = " + Constantes.BBDD_ACTIVOSI
					+ " ORDER BY descripcion	";

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Servicio servicio = getServicioResulset(resulSet);
				lista.add(servicio);
			}
			statement.close();
			statement.close();
			logger.debug(sql);
		} catch (

		SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return lista;
	}

	public Servicio getServicioResulset(ResultSet resulSet) {
		Servicio servicio = null;
		try {
			servicio = new Servicio();
			servicio.setId(resulSet.getLong("id"));
			servicio.setIdjimena(resulSet.getLong("idjimena"));
			servicio.setCodigo(resulSet.getString("codigo"));
			servicio.setDescripcion(resulSet.getString("descripcion"));
			servicio.setAsistencial(resulSet.getBoolean("asistencial"));
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		}
		return servicio;
	}

	public Servicio getServicioId(Long id) {
		Connection connection = null;
		Servicio servicio = null;
		try {
			connection = super.getConexionBBDD();
			sql = "SELECT  *  FROM serviciosgfh WHERE estado=" + Constantes.BBDD_ACTIVOSI + " AND id=" + id;
			logger.debug(sql);
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				servicio = getServicioResulset(resulSet);
			}
			statement.close();
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(sql);
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return servicio;
	}

	public Servicio getServicioCodigo(String codigo) {
		Connection connection = null;
		Servicio servicio = null;
		try {
			connection = super.getConexionBBDD();
			sql = "SELECT  *  FROM serviciosgfh WHERE estado=" + Constantes.BBDD_ACTIVOSI + " AND codigo='" + codigo
					+ "'";
			logger.debug(sql);
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				servicio = getServicioResulset(resulSet);
			}
			statement.close();
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(sql);
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return servicio;
	}

	public Servicio getServicioCodigoExiste(String codigo, Servicio servicioParam) {
		Connection connection = null;
		Servicio servicio = null;
		try {
			connection = super.getConexionBBDD();
			sql = "SELECT  *  FROM serviciosgfh WHERE estado=" + Constantes.BBDD_ACTIVOSI + " AND codigo='" + codigo
					+ "' AND id!=" + servicioParam.getId();
			logger.debug(sql);
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				servicio = getServicioResulset(resulSet);
			}
			statement.close();
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(sql);
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		}
		try {
			connection.close();
		} catch (SQLException e) {
			logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
		}
		return servicio;
	}
}
