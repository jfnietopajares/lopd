package com.hnss.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.Funcionalidad;
import com.hnss.entidades.Usuario;
import com.hnss.ui.Notificaciones;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.VaadinSession;

public class FuncionalidadDAO extends ConexionDAO {

	private static final Logger logger = LogManager.getLogger(FuncionalidadDAO.class);

	public FuncionalidadDAO() {
		super();
	}

	public Funcionalidad getRegistroResulset(ResultSet rs) {
		Funcionalidad funcionalidad = new Funcionalidad();
		try {
			funcionalidad.setId(rs.getLong("id"));
			funcionalidad.setDescripcion(rs.getString("descripcion"));
			funcionalidad.setTextomenu(rs.getString("textomenu"));
			funcionalidad.setEstado(rs.getBoolean("estado"));
			funcionalidad.setFechacambio(Utilidades.getFechaLocalDate(rs.getLong("fechacambio")));
			funcionalidad.setUsucambio(new UsuarioDAO().getUsuarioId(rs.getLong("usucambio")));

		} catch (SQLException e) {
			logger.error(Notificaciones.SQLERRORRESULSET, e);
		}

		return funcionalidad;
	}

	public Funcionalidad getPorId(Long id) {
		Connection connection = null;
		Funcionalidad funcionalidad = null;

		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM funcionalidad WHERE   id=	" + id;
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				funcionalidad = getRegistroResulset(resulSet);
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
		return funcionalidad;
	}

	public Funcionalidad getPorDescripcion(String descString) {
		Connection connection = null;
		Funcionalidad funcionalidad = null;

		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM funcionalidad WHERE   descripcion='" + descString + "'";
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			if (resulSet.next()) {
				funcionalidad = getRegistroResulset(resulSet);
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
		return funcionalidad;
	}

	public ArrayList<Funcionalidad> getListaFuncionalidad() {
		Connection connection = null;
		ArrayList<Funcionalidad> lista = new ArrayList<>();

		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM  funcionalidad WHERE estado= " + Constantes.BBDD_ACTIVOSI;
			sql = sql.concat(" order by descripcion ");

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Funcionalidad funcionalidad = getRegistroResulset(resulSet);
				lista.add(funcionalidad);
			}
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

	public Set<Funcionalidad> getListaFuncionalidadSet() {
		Connection connection = null;
		Set<Funcionalidad> lista = new HashSet<Funcionalidad>();

		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM  funcionalidad WHERE estado= " + Constantes.BBDD_ACTIVOSI;
			sql = sql.concat(" order by descripcion ");

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Funcionalidad funcionalidad = getRegistroResulset(resulSet);
				lista.add(funcionalidad);
			}
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

	public ArrayList<String> getListaFuncionalidadString() {
		Connection connection = null;
		ArrayList<String> lista = new ArrayList<String>();

		try {
			connection = super.getConexionBBDD();
			sql = " SELECT * FROM  funcionalidad WHERE estado= " + Constantes.BBDD_ACTIVOSI;
			sql = sql.concat(" order by descripcion ");

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Funcionalidad funcionalidad = getRegistroResulset(resulSet);
				lista.add(funcionalidad.getDescripcion());
			}
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

	public boolean grabaDatos(Funcionalidad funcionalidad) {
		boolean actualizado = false;

		if (funcionalidad.getId().equals(new Long(0))) {
			actualizado = this.insertaDatos(funcionalidad);
		} else {
			actualizado = this.actualizaDatos(funcionalidad);
		}
		return actualizado;
	}

	public boolean insertaDatos(Funcionalidad funcionalidad) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();
			Long id = new UtilidadesDAO().getSiguienteId("funcionalidad");
			funcionalidad.setId(id);
			sql = " INSERT INTO  funcionalidad (id,descripcion,textomenu,estado,usucambio,fechacambio) " + " VALUES ("
					+ funcionalidad.getId() + ",'" + funcionalidad.getDescripcion() + "','"
					+ funcionalidad.getTextomenu() + "'," + Constantes.BBDD_ACTIVOSI + "," + usuario.getId() + ","
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ")";
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

	public boolean actualizaDatos(Funcionalidad funcionalidad) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();
			sql = " UPDATE   funcionalidad SET descripcion='" + funcionalidad.getDescripcion() + "',textomenu='"
					+ funcionalidad.getTextomenu() + "',usucambio='" + usuario.getDni() + "',  fechacambio="
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + " WHERE id=" + funcionalidad.getId();

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

	public boolean borraDatos(Funcionalidad funcionalidad) {
		Connection connection = null;
		Boolean insertadoBoolean = false;
		try {
			Usuario usuario = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
			connection = super.getConexionBBDD();
			sql = " UPDATE   funcionalidad SET usucambio='" + usuario.getDni() + "',  fechacambio="
					+ Utilidades.getFechayyymmdd(LocalDate.now()) + ",estado=" + Constantes.BBDD_ACTIVONO + " WHERE id="
					+ funcionalidad.getId();
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

	public Set<Funcionalidad> getListaFuncioUsuario(Usuario usuario) {
		Connection connection = null;
		Set<Funcionalidad> lista = new HashSet<Funcionalidad>();

		try {
			connection = super.getConexionBBDD();

			sql = "SELECT f.*	" + " FROM funcionalidad f 	"
					+ "  JOIN us_funcionalidad  u ON  f.id=u.idfuncionalidad  and  u.idusuario=" + usuario.getId()
					+ " WHERE f.estado=" + Constantes.BBDD_ACTIVOSI;

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Funcionalidad funcionalidad = getRegistroResulset(resulSet);
				// funcionalidad.setPermitida(resulSet.getBoolean("permitida"));
				lista.add(funcionalidad);
			}
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

	public ArrayList<Funcionalidad> getListaFuncioUsuarioAl(Usuario usuario) {
		Connection connection = null;
		ArrayList<Funcionalidad> lista = new ArrayList<Funcionalidad>();

		try {
			connection = super.getConexionBBDD();

			if (usuario.getEstado() == Usuario.USUARIO_ADMINISTRADOR) {
				sql = "SELECT f.*	" + " FROM funcionalidad f 	" + "   WHERE f.estado=" + Constantes.BBDD_ACTIVOSI
						+ " ORDER BY textomenu ";
			} else {
				sql = "SELECT f.*	" + " FROM funcionalidad f 	"
						+ "  JOIN us_funcionalidad  u ON  f.id=u.idfuncionalidad AND u.permitida=1 and  u.idusuario="
						+ usuario.getId() + " WHERE f.estado=" + Constantes.BBDD_ACTIVOSI + " ORDER BY textomenu ";
			}
			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Funcionalidad funcionalidad = getRegistroResulset(resulSet);
				lista.add(funcionalidad);
			}
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

	public ArrayList<String> getListaFuncioUsuarioString(Usuario usuario) {
		Connection connection = null;
		ArrayList<String> lista = new ArrayList<String>();

		try {
			connection = super.getConexionBBDD();

			sql = "SELECT f.*	" + " FROM funcionalidad f 	"
					+ "  JOIN us_funcionalidad  u ON  f.id=u.idfuncionalidad  and u.permitida=1 AND u.estado="
					+ Constantes.BBDD_ACTIVOSI + " AND u.idusuario=" + usuario.getId() + " WHERE f.estado="
					+ Constantes.BBDD_ACTIVOSI;

			Statement statement = connection.createStatement();
			ResultSet resulSet = statement.executeQuery(sql);
			while (resulSet.next()) {
				Funcionalidad funcionalidad = getRegistroResulset(resulSet);
				lista.add(funcionalidad.getDescripcion());
			}
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
}
