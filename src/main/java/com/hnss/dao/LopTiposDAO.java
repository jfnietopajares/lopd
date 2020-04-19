package com.hnss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.Funcionalidad;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.entidades.lopd.LopdTipos;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.VaadinSession;

public class LopTiposDAO extends ConexionDAO {
	private static final Logger logger = LogManager.getLogger(LopTiposDAO.class);

	public LopTiposDAO() {
		super();
	}

	public LopdTipos getRegistroResulset(ResultSet rs) {
		LopdTipos incidenciaTipos = new LopdTipos();
		try {
			incidenciaTipos.setId(rs.getLong("id"));
			incidenciaTipos.setDescripcion(rs.getString("descripcion"));
			switch (rs.getInt("sujeto")) {
			case 1:
				incidenciaTipos.setSujeto(LopdSujeto.SUJETO_PACIENTE);
				break;
			case 2:
				incidenciaTipos.setSujeto(LopdSujeto.SUJETO_TRABAJADOR);
				break;
			case 3:
				incidenciaTipos.setSujeto(LopdSujeto.SUJETO_OTROS);
				break;
			case 4:
				incidenciaTipos.setSujeto(LopdSujeto.SUJETO_USUARIO);
				break;
			default:
				break;
			}
			incidenciaTipos.setMailReponsable(rs.getBoolean("mailresponsable"));
		} catch (SQLException e) {
			logger.error(Notificaciones.SQLERRORRESULSET, e);
		}

		return incidenciaTipos;
	}

	public LopdTipos getPorId(Long id) {
		Connection connection = null;
		LopdTipos incidenciaTipos = null;

		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM lopd_tipos WHERE  estado=" + Constantes.BBDD_ACTIVOSI + " AND id=	" + id;
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				incidenciaTipos = getRegistroResulset(resulSet);
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
		return incidenciaTipos;
	}

	/*
	 * Selecciona los tipos de incidencia en función del El sujeto asociado. Si el
	 * sujeto no es null filtra los tipos del sujeto a sociado Si el sujeto es null
	 * seleccionada todos Si el usuario es null no filtra nada Si el usuario es
	 * distinto de nulll filta por funcionaliad: Si estado=2 administrador no filtra
	 * nada Si el usuario tiene la funcionalidad
	 * 
	 */
	public ArrayList<LopdTipos> getListaIncidenciaTipos(LopdSujeto sujeto, Usuario usuario) {
		Connection connection = null;
		ArrayList<LopdTipos> lista = new ArrayList<>();

		try {
			connection = super.getConexionBBDD();
			if (sujeto == null) {
				if (usuario == null) {
					sql = " SELECT * FROM  lopd_tipos WHERE estado = " + Constantes.BBDD_ACTIVOSI
							+ " ORDER BY sujeto,descripcion	";
				} else {
					if (usuario.getEstado() == 2 || usuario.tieneLaFuncionalidad(Funcionalidad.PEDIRUSUARIO)) {
						sql = " SELECT * FROM  lopd_tipos WHERE estado = " + Constantes.BBDD_ACTIVOSI
								+ " ORDER BY sujeto,descripcion	";
					} else {
						sql = " SELECT * FROM  lopd_tipos WHERE estado = " + Constantes.BBDD_ACTIVOSI + "  AND sujeto!="
								+ LopdSujeto.USUARIO + " ORDER BY sujeto,descripcion	";

					}
				}
			} else
				sql = " SELECT * FROM  lopd_tipos WHERE estado = " + Constantes.BBDD_ACTIVOSI + "  AND sujeto="
						+ sujeto.getId() + " ORDER BY sujeto,descripcion	";

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				LopdTipos incidenciaTipos = getRegistroResulset(resulSet);
				lista.add(incidenciaTipos);
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

	public boolean grabaDatos(LopdTipos incidenciaTipos) {
		boolean actualizado = false;

		if (incidenciaTipos.getId().equals(new Long(0))) {
			actualizado = this.insertaDatos(incidenciaTipos);
		} else {
			actualizado = this.actualizaDatos(incidenciaTipos);
		}
		return actualizado;
	}

	public boolean insertaDatos(LopdTipos incidenciaTipos) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();
			Long id = new UtilidadesDAO().getSiguienteId("lopd_tipos");
			incidenciaTipos.setId(id);
			int valor;
			if (incidenciaTipos.getMailReponsable() == true)
				valor = 1;
			else
				valor = 0;
			sql = " INSERT INTO  lopd_tipos (id,descripcion,sujeto,mailresponsable,estado,usucambio,fechacambio) "
					+ " VALUES (" + incidenciaTipos.getId() + ",'" + incidenciaTipos.getDescripcion() + "' " + ","
					+ incidenciaTipos.getSujeto().getId() + "," + valor + "," + Constantes.BBDD_ACTIVOSI + ","
					+ usuario.getId() + "," + Utilidades.getFechayyymmdd(LocalDate.now()) + ")";
			Statement statement = connection.createStatement();
			insertadoBoolean = statement.execute(sql);
			insertadoBoolean = true;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
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

	public boolean actualizaDatos(LopdTipos incidenciaTipos) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();
			int valor = incidenciaTipos.getMailReponsable() ? 1 : 0;
			sql = " UPDATE   lopd_tipos SET descripcion='" + incidenciaTipos.getDescripcion() + "',sujeto='"
					+ incidenciaTipos.getSujeto().getId() + "',mailresponsable='" + valor + "', usucambio="
					+ Long.toString(usuario.getId()) + ",  fechacambio=" + Utilidades.getFechayyymmdd(LocalDate.now())
					+ ", estado=" + Constantes.BBDD_ACTIVOSI + " WHERE id=" + incidenciaTipos.getId();
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

	public boolean borraDatos(LopdTipos incidenciaTipos) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();

			sql = " UPDATE   lopd_tipos SET usucambio=" + Long.toString(usuario.getId()) + ",  fechacambio="
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ", estado=" + Constantes.BBDD_ACTIVONO
					+ " WHERE id=" + incidenciaTipos.getId();
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
}
