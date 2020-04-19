package com.hnss.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.Paciente;
import com.hnss.entidades.Usuario;
import com.hnss.entidades.lopd.LopdIncidencia;
import com.hnss.entidades.lopd.LopdNotas;
import com.hnss.entidades.lopd.LopdSujeto;
import com.hnss.entidades.lopd.LopdTipos;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.VaadinSession;

public class LopdIncidenciaDAO extends ConexionDAO {
	private static final Logger logger = LogManager.getLogger(LopdIncidenciaDAO.class);

	public LopdIncidenciaDAO() {
		super();
	}

	public boolean grabaDatos(LopdIncidencia incidencia) {
		boolean actualizado = false;

		if (incidencia.getId().equals(new Long(0))) {
			actualizado = this.insertaDatos(incidencia);
		} else {
			actualizado = this.actualizaDatos(incidencia);
		}
		return actualizado;
	}

	public boolean actualizaDatos(LopdIncidencia incidencia) {
		Connection connection = null;
		boolean insertado = false;
		try {
			connection = super.getConexionBBDD();

			sql = " UPDATE  lopd_incidencias set  idusuario=?, tipo=?,fecha=?,hora=?, numerohc=? "
					+ ",idDocumento=?, fechaDocu=?,horaDocu=?,servicio=?,	descriDocu=?,	perdidaDatos=?"
					+ ",descripcionError=?, descripcionSolucion=?,fechaCambio=?,usuCambio=?, resuelta=? , fechasolucion=?   "
					+ ", idusuarioafectado=? where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setLong(1, incidencia.getUsuarioRegistra().getId());
			statement.setLong(2, incidencia.getTipo().getId());
			statement.setLong(3, Utilidades.getFechaYYYYMMDD(incidencia.getFechaHora()));
			statement.setInt(4, Utilidades.getHoraHHMM(incidencia.getFechaHora()));

			if (incidencia.getPaciente() != null && incidencia.getPaciente().getNumerohc() != null)
				statement.setString(5, incidencia.getPaciente().getNumerohc());
			else
				statement.setNull(5, Types.INTEGER);

			if (incidencia.getIdDocumento() != null && !incidencia.getIdDocumento().isEmpty())
				statement.setString(6, incidencia.getIdDocumento());
			else
				statement.setNull(6, Types.CHAR);

			if (incidencia.getFechaHoraDocumento() == null) {
				statement.setNull(7, Types.INTEGER);
				statement.setNull(8, Types.INTEGER);
			} else {
				statement.setLong(7, Utilidades.getFechaYYYYMMDD(incidencia.getFechaHoraDocumento()));
				statement.setInt(4, Utilidades.getHoraHHMM(incidencia.getFechaHoraDocumento()));

			}

			if (incidencia.getServicio() != null)
				statement.setLong(9, incidencia.getServicio().getId());
			else
				statement.setNull(9, Types.INTEGER);

			if (incidencia.getDescriDocu() != null && !incidencia.getDescriDocu().isEmpty())
				statement.setString(10, incidencia.getDescriDocu());
			else
				statement.setNull(10, Types.VARCHAR);

			statement.setBoolean(11, incidencia.getPerdidaDatos());
			statement.setString(12, incidencia.getDescripcionError());

			if (incidencia.getDescripcionSolucion() != null && !incidencia.getDescripcionSolucion().isEmpty())
				statement.setString(13, incidencia.getDescripcionSolucion());
			else
				statement.setNull(13, Types.VARCHAR);

			statement.setLong(14, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setLong(15, incidencia.getUsuCambio().getId());

			statement.setBoolean(16, incidencia.getResuelta());
			if (incidencia.getFechaSolucion() != null)
				statement.setLong(17, Utilidades.getFechayyymmdd(incidencia.getFechaSolucion()));
			else {
				statement.setNull(17, Types.DOUBLE);
			}

			if (incidencia.getUsuarioAfectado() != null)
				statement.setLong(18, incidencia.getUsuarioAfectado().getId());
			else {
				statement.setNull(18, Types.DOUBLE);
			}
			statement.setLong(19, incidencia.getId());

			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return insertado;
	}

	public boolean actualizaRespuesta(LopdIncidencia incidencia) {
		Connection connection = null;
		boolean insertado = false;
		try {
			connection = super.getConexionBBDD();

			sql = " UPDATE  lopd_incidencias set   descripcionSolucion=?,fechaCambio=?,usuCambio=?, resuelta=? "
					+ ", fechasolucion=? where id=?";
			PreparedStatement statement = connection.prepareStatement(sql);

			if (incidencia.getDescripcionSolucion() != null && !incidencia.getDescripcionSolucion().isEmpty())
				statement.setString(1, incidencia.getDescripcionSolucion());
			else
				statement.setNull(1, Types.VARCHAR);

			statement.setLong(2, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setLong(3, incidencia.getUsuCambio().getId());

			statement.setBoolean(4, incidencia.getResuelta());
			if (incidencia.getFechaSolucion() != null)
				statement.setLong(5, Utilidades.getFechayyymmdd(incidencia.getFechaSolucion()));
			else {
				statement.setNull(5, Types.DOUBLE);
			}
			statement.setLong(6, incidencia.getId());

			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return insertado;
	}

	public boolean insertaDatos(LopdIncidencia incidencia) {
		Connection connection = null;
		boolean insertado = false;
		try {
			connection = super.getConexionBBDD();
			Long id = new UtilidadesDAO().getSiguienteId("lopd_incidencias");
			incidencia.setId(id);

			sql = " INSERT INTO lopd_incidencias (id, idusuario,  tipo,fecha,hora, numerohc "
					+ ",idDocumento, fechaDocu,horaDocu,servicio,	descriDocu,	perdidaDatos"
					+ ",descripcionError, descripcionSolucion,fechaCambio,usuCambio"
					+ ",resuelta,fechasolucion,estado,idusuarioafectado)"
					+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, incidencia.getId());
			statement.setLong(2, incidencia.getUsuarioRegistra().getId());
			statement.setLong(3, incidencia.getTipo().getId());
			statement.setLong(4, Utilidades.getFechaYYYYMMDD(incidencia.getFechaHora()));
			statement.setInt(5, Utilidades.getHoraHHMM(incidencia.getFechaHora()));

			if (incidencia.getPaciente() != null && !incidencia.getPaciente().getNumerohc().isEmpty())
				statement.setString(6, incidencia.getPaciente().getNumerohc());
			else
				statement.setNull(6, Types.INTEGER);

			if (incidencia.getIdDocumento() != null && !incidencia.getIdDocumento().isEmpty())
				statement.setString(7, incidencia.getIdDocumento());
			else
				statement.setNull(7, Types.CHAR);

			if (incidencia.getFechaHoraDocumento() == null) {
				statement.setNull(8, Types.INTEGER);
				statement.setNull(9, Types.INTEGER);
			} else {
				statement.setLong(8, Utilidades.getFechaYYYYMMDD(incidencia.getFechaHoraDocumento()));
				statement.setInt(9, Utilidades.getHoraHHMM(incidencia.getFechaHoraDocumento()));

			}

			if (incidencia.getServicio() != null)
				statement.setLong(10, incidencia.getServicio().getId());
			else
				statement.setNull(10, Types.INTEGER);

			if (incidencia.getDescriDocu() != null && !incidencia.getDescriDocu().isEmpty())
				statement.setString(11, incidencia.getDescriDocu());
			else
				statement.setNull(11, Types.VARCHAR);

			statement.setBoolean(12, incidencia.getPerdidaDatos());

			statement.setString(13, incidencia.getDescripcionError());

			if (incidencia.getDescripcionSolucion() != null && !incidencia.getDescripcionSolucion().isEmpty())
				statement.setString(14, incidencia.getDescripcionSolucion());
			else
				statement.setNull(14, Types.VARCHAR);

			statement.setLong(15, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setLong(16, incidencia.getUsuCambio().getId());

			statement.setBoolean(17, incidencia.getResuelta());
			if (incidencia.getFechaSolucion() != null)
				statement.setLong(18, Utilidades.getFechayyymmdd(LocalDate.now()));
			else {
				statement.setNull(18, Types.DOUBLE);
			}

			statement.setInt(19, Constantes.BBDD_ACTIVOSI);

			if (incidencia.getUsuarioAfectado() != null)
				statement.setLong(20, incidencia.getUsuarioAfectado().getId());
			else {
				statement.setNull(20, Types.DOUBLE);
			}
			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(sql);
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return insertado;
	}

	public LopdIncidencia getIncidenciaId(Long id, Usuario usuario, Paciente paciente) {
		Connection connection = null;
		LopdIncidencia incidencia = null;
		try {
			connection = super.getConexionBBDD();
			sql = " SELECT i.*,t.id as idtipo,t.descripcion as descripciontipo " + " FROM lopd_incidencias i "
					+ " JOIN lopd_tipos t ON t.id=i.tipo " + " JOIN usuarios u On u.id=i.idusuario " + " WHERE id= "
					+ id;

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				LopdTipos tipoI = new LopdTipos();
				tipoI.setId(resulSet.getLong("idtipo"));
				tipoI.setDescripcion(resulSet.getString("descripciontipo "));
				incidencia = getResulset(resulSet, usuario, paciente, tipoI);
			}
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return incidencia;
	}

	public ArrayList<LopdIncidencia> getListaInicidencias(LocalDate desde, LocalDate hasta, LopdTipos tipo,
			Usuario usuario, String pendiente) {
		Connection connection = null;
		ArrayList<LopdIncidencia> lista = new ArrayList<>();
		try {
			connection = super.getConexionBBDD();

			sql = " SELECT i.*,t.id as idtipo,t.descripcion as descripciontipo,t.sujeto  " + " FROM lopd_incidencias i "
					+ " JOIN lopd_tipos t ON t.id=i.tipo " + " JOIN usuarios u On u.id=i.idusuario " + " WHERE 1=1 ";
			if (desde != null)
				sql = sql.concat(" AND fecha>=" + Long.toString(Utilidades.getFechayyymmdd(desde)));

			if (hasta != null)
				sql = sql.concat(" AND fecha<=" + Long.toString(Utilidades.getFechayyymmdd(hasta)));

			if (tipo != null)
				sql = sql.concat(" AND tipo=" + Long.toString(tipo.getId()));

			if (usuario != null)
				sql = sql.concat(" AND idusuario=" + usuario.getId());

			if (pendiente != null) {
				if (pendiente.equals("S"))
					sql = sql.concat(" AND resuelta= 0");
				else
					sql = sql.concat(" AND resuelta=1 ");
			}
			sql = sql.concat(" order by id desc");
			logger.debug(sql);
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);

			while (resulSet.next()) {

				/*
				 * LopdSujeto sujeto = new LopdSujeto(resulSet.getInt("sujeto"),
				 * LopdSujeto.getDescripcionFromTipo(resulSet.getInt("sujeto"))); LopdTipos
				 * tipoI = new LopdTipos(); tipoI.setId(resulSet.getLong("idtipo"));
				 * tipoI.setDescripcion(resulSet.getString("descripciontipo"));
				 * tipoI.setSujeto(sujeto);
				 */

				LopdIncidencia incidencia = getResulset(resulSet, usuario, null, null);
				lista.add(incidencia);
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
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return lista;
	}

	public LopdIncidencia getResulset(ResultSet rs, Usuario usuario, Paciente paciente, LopdTipos tipo) {
		LopdIncidencia incidencia = new LopdIncidencia();
		try {
			incidencia.setId(rs.getLong("id"));

			if (usuario == null)
				incidencia.setUsuarioRegistra(new UsuarioDAO().getUsuarioId(rs.getLong("idusuario")));
			else
				incidencia.setUsuarioRegistra(usuario);

			if (tipo == null)
				incidencia.setTipo(new LopTiposDAO().getPorId(rs.getLong("tipo")));
			else
				incidencia.setTipo(tipo);

			incidencia.setSujeto(LopdSujeto.getTipoSujeto(incidencia.getTipo()));

			incidencia.setFechaHora(Utilidades.getFechaHora(rs.getLong("fecha"), rs.getInt("hora")));

			if (paciente == null) {
				if (rs.getString("numerohc") != null)
					incidencia.setPaciente(new PacienteDAO().getPacienteNhc(rs.getString("numerohc")));
			} else
				incidencia.setPaciente(paciente);

			incidencia.setIdDocumento(rs.getString("iddocumento"));
			incidencia.setFechaHoraDocumento(Utilidades.getFechaHora(rs.getLong("fechadocu"), rs.getInt("horadocu")));

			incidencia.setServicio(new ServicioDAO().getServicioId(rs.getLong("servicio")));

			incidencia.setPerdidaDatos(rs.getBoolean("perdidadatos"));
			incidencia.setDescriDocu(rs.getString("descridocu"));
			incidencia.setDescripcionError(rs.getString("descripcionerror"));
			incidencia.setDescripcionSolucion(rs.getString("descripcionsolucion"));
			incidencia.setResuelta(rs.getBoolean("resuelta"));
			incidencia.setFechaSolucion(Utilidades.getFechaLocalDate(rs.getLong("fechasolucion")));

			incidencia.setUsuarioAfectado(new UsuarioDAO().getUsuarioId(rs.getLong("idusuarioafectado")));
		} catch (SQLException e) {
			logger.error(Notificaciones.SQLERRORRESULSET, e);
		}

		return incidencia;
	}

	public Long grabaDatosNotas(LopdIncidencia incidencia, LopdNotas nota) {
		boolean actualizado = false;

		if (nota.getId().equals(new Long(0))) {
			return this.doInsertaNota(incidencia, nota);
		} else {
			return this.doActualizaNota(incidencia, nota);
		}
	}

	public Long doInsertaNota(LopdIncidencia incidencia, LopdNotas nota) {
		Connection connection = null;
		Boolean insertado = false;
		Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
		try {
			connection = super.getConexionBBDD();
			nota.setId(new UtilidadesDAO().getSiguienteId("lopd_notas"));

			sql = " INSERT INTO lopd_notas (id,idincidencia,texto,fechacambio,usucambio,estado) "
					+ " VALUES (?,?,?,?,?,?)";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, nota.getId());
			statement.setLong(2, incidencia.getId());
			statement.setString(3, nota.getDescripcion());
			statement.setLong(4, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setLong(5, usuario.getId());
			statement.setInt(6, Constantes.BBDD_ACTIVOSI);
			insertado = statement.executeUpdate() > 0;
			statement.close();
			logger.debug(sql + " valoree " + nota.getId() + "," + incidencia.getId() + "," + nota.getDescripcion() + ","
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + "," + usuario.getId() + ","
					+ Constantes.BBDD_ACTIVOSI);
		} catch (SQLException e) {
			logger.error(sql + " valoree " + nota.getId() + "," + incidencia.getId() + "," + nota.getDescripcion() + ","
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + "," + usuario.getId() + ","
					+ Constantes.BBDD_ACTIVOSI);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		if (insertado == true)
			return nota.getId();
		else
			return new Long(0);
	}

	public Long doActualizaNota(LopdIncidencia incidencia, LopdNotas nota) {
		Connection connection = null;
		Boolean insertado = false;
		try {
			connection = super.getConexionBBDD();

			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			sql = " UPDATE lopd_notas SET texto=?,fechacambio=?,usucambio=?,estado=?  WHERE  id=?";

			PreparedStatement statement = connection.prepareStatement(sql);

			statement.setString(1, nota.getDescripcion());
			statement.setLong(2, Utilidades.getFechayyymmdd(LocalDate.now()));
			statement.setString(3, usuario.getDni());
			statement.setInt(4, Constantes.BBDD_ACTIVOSI);
			statement.setLong(5, nota.getId());
			insertado = statement.executeUpdate() > 0;
			// insertado = true;
			statement.close();
			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		if (insertado == true)
			return nota.getId();
		else
			return new Long(0);
	}

	public ArrayList<LopdNotas> getNostasIncidencia(LopdIncidencia incidencia) {
		Connection connection = null;
		ArrayList<LopdNotas> lista = new ArrayList<>();
		try {
			connection = super.getConexionBBDD();

			sql = " SELECT * FROM lopd_notas WHERE estado= " + Constantes.BBDD_ACTIVOSI + " AND idincidencia= "
					+ incidencia.getId() + " order by fechacambio";
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);

			while (resulSet.next()) {
				LopdNotas nota = new LopdNotas();
				nota.setId(resulSet.getLong("id"));
				nota.setIdIncidenciaLong(resulSet.getLong("idincidencIa"));
				nota.setDescripcion(resulSet.getString("texto"));
				nota.setEstado(resulSet.getInt("estado"));
				nota.setFecha(Utilidades.getFechaLocalDate(resulSet.getLong("fechacambio")));
				nota.setUsucambio(new UsuarioDAO().getUsuarioId(resulSet.getLong("usucambio")));
				lista.add(nota);
			}
			statement.close();

			logger.debug(sql);
		} catch (SQLException e) {
			logger.error(sql);
			logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
		} catch (Exception e) {
			logger.error(Notificaciones.EXCEPTION_ERROR, e);
		} finally {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
			}
		}
		return lista;
	}

}
