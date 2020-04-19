package com.hnss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.Paciente;
import com.hnss.entidades.Usuario;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.VaadinSession;

public class PacienteDAO extends ConexionDAO {
	private static final Logger logger = LogManager.getLogger(PacienteDAO.class);

	public PacienteDAO() {
		super();
	}

	public Paciente getPacienteNhc(String numerohc) {
		Connection connection = null;
		Paciente paciente = null;
		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM pacientes WHERE numerohc='" + numerohc + "'";
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				paciente = getRegistroResulset(resulSet);
			}
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
		return paciente;
	}

	public Paciente getRegistroResulset(ResultSet rs) {
		Paciente paciente = new Paciente();
		try {
			paciente.setId(rs.getLong("id"));
			paciente.setIdJimena(rs.getLong("idjimena"));
			paciente.setNumerohc(rs.getString("numerohc"));
			paciente.setApellidosnombre(rs.getString("apellidosnombre"));
		} catch (Exception e) {
			logger.error(Notificaciones.SQLERRORRESULSET);
		}
		return paciente;
	}

	public Long insertaPaciente(Paciente paciente) {
		Connection connection = null;
		boolean insertado = false;
		Long id = null;
		try {
			connection = super.getConexionBBDD();
			id = new UtilidadesDAO().getSiguienteId("pacientes");
			paciente.setId(id);
			paciente.setEstado(Usuario.USUARIO_ACTIVO);
			// ID APELLIDOSNOMBRE NUMEROHC ESTADO FECHACAMBIO USUCAMBIO IDJIMENA
			sql = " INSERT INTO pacientes (id,apellidosnombre,numerohc,estado,fechacambio,usucambio,idjimena) "
					+ " VALUES (?,?,?,?,?,?,?)  ";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, paciente.getId());
			statement.setString(2, paciente.getApellidosnombre());
			statement.setString(3, paciente.getNumerohc());
			statement.setInt(4, Constantes.BBDD_ACTIVOSI);
			statement.setLong(5, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setLong(6,
					((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME)).getId());
			statement.setLong(7, paciente.getIdJimena());
			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(" INSERT INTO pacientes (id,apellidosnombre,numerohc,estado,fechacambio,usucambio,idjimena) "
					+ " VALUES (" + paciente.getId() + ",'" + paciente.getApellidosnombre() + "','"
					+ paciente.getNumerohc() + "'," + Constantes.BBDD_ACTIVOSI + ","
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ",'"
					+ ((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME)).getId() + ",'"
					+ paciente.getIdJimena() + ")");

		} catch (SQLException e) {
			logger.error(" INSERT INTO pacientes (id,apellidosnombre,numerohc,estado,fechacambio,usucambio,idjimena) "
					+ " VALUES (" + paciente.getId() + "," + paciente.getApellidosnombre() + "','"
					+ paciente.getNumerohc() + "'," + Constantes.BBDD_ACTIVOSI + ","
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ",'"
					+ ((Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME)).getId() + ",'"
					+ paciente.getIdJimena() + ")", e);

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

}
