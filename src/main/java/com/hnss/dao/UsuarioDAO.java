package com.hnss.dao;

import com.hnss.entidades.Funcionalidad;
import com.hnss.entidades.Usuario;
import com.hnss.excepciones.LoginException;
import com.hnss.excepciones.UsuarioBajaException;
import com.hnss.ui.Notificaciones;
import com.hnss.ui.PantallaLogin;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.Utilidades;
import com.vaadin.server.VaadinSession;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The Class UsuarioDao.
 *
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class UsuarioDAO extends ConexionDAO {

    private Usuario usuario;

    private static final Logger logger = LogManager.getLogger(UsuarioDAO.class);

    /**
     * Instantiates a new usuario dao.
     */
    public UsuarioDAO() {
        super();

    }

    /**
     * Gets the usuario userid.
     *
     * @param userid the userid
     * @return the usuario userid
     * @throws LoginException
     * @throws UsuarioBajaException
     */
    public Usuario getUsuarioLogin(String userid, Boolean conFunciolidades)
            throws LoginException, UsuarioBajaException {
        Connection connection = null;
        usuario = null;
        try {
            connection = super.getConexionBBDD();
            sql = "SELECT  *  FROM usuarios WHERE dni='" + userid + "'";
            logger.debug(sql);
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            if (resulSet.next()) {
                usuario = getUsuarioResulset(resulSet);
                if (conFunciolidades == true) {
                    usuario.setFucionalidadesArrayList(new FuncionalidadDAO().getListaFuncioUsuarioAl(usuario));
                }
            } else {

                throw new LoginException(PantallaLogin.LOGIN_USUARIO_NOENCONTRADO);
            }
            statement.close();

        } catch (SQLException e) {
            logger.error(sql);
            logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
        } catch (Exception e) {
            logger.error(sql, e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(ConexionDAO.ERROR_CLOSE_BBDD_SQL, e);
        }
        return usuario;
    }

    public Usuario getUsuarioDni(String dni, Boolean conFunciolidades) {
        Connection connection = null;
        usuario = null;
        try {
            connection = super.getConexionBBDD();
            sql = "SELECT  *  FROM usuarios WHERE dni='" + dni.trim() + "'";
            logger.debug(sql);
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            if (resulSet.next()) {
                usuario = getUsuarioResulset(resulSet);
                if (conFunciolidades == true) {
                    usuario.setFucionalidadesArrayList(new FuncionalidadDAO().getListaFuncioUsuarioAl(usuario));
                }
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
        return usuario;
    }

    public Usuario getUsuarioId(Long id) {
        Connection connection = null;
        usuario = null;
        try {
            connection = super.getConexionBBDD();
            sql = "SELECT  *  FROM usuarios WHERE id=" + id;
            logger.debug(sql);
            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            if (resulSet.next()) {
                usuario = getUsuarioResulset(resulSet);
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
        return usuario;
    }

    /**
     * Gets the usuario resulset.
     *
     * @param resulSet the resul set
     * @return the usuario resulset
     */
    public Usuario getUsuarioResulset(ResultSet resulSet) {
        Usuario usuario = null;
        try {
            usuario = new Usuario();
            usuario.setId(resulSet.getLong("id"));
            usuario.setDni(resulSet.getString("dni"));
            usuario.setApellido1(resulSet.getString("apellido1"));
            usuario.setApellido2(resulSet.getString("apellido2"));
            usuario.setNombre(resulSet.getString("nombre"));
            usuario.setMail(resulSet.getString("mail"));
            usuario.setTelefono(resulSet.getString("telefono"));
            usuario.setEstado(resulSet.getInt("estado"));
        } catch (SQLException e) {
            logger.error(ConexionDAO.ERROR_BBDD_SQL, e);
        }
        return usuario;
    }

    public boolean grabaDatos(Usuario usuario) {
        boolean actualizado = false;

        if (usuario.getId().equals(new Long(0))) {
            if (!this.insertaUsuario(usuario).equals(new Long(0))) {
                actualizado = true;
            }

        } else {
            actualizado = this.actualizaDatos(usuario);
            actualizado = doActualizaFuncionalidad(usuario);
        }
        return actualizado;
    }

    public boolean doActualizaFuncionalidad(Usuario usuario) {
        Connection connection = null;
        Boolean insertadoBoolean = false;
        try {
            Usuario usuarioa = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
            connection = super.getConexionBBDD();

            sql = " UPDATE   us_funcionalidad SET usucambio=" + usuarioa.getId() + ",  fechacambio="
                    + Utilidades.getFechayyymmdd(LocalDate.now()) + ",estado=" + Constantes.BBDD_ACTIVONO
                    + " WHERE idusuario=" + usuario.getId();
            Statement statement = connection.createStatement();
            logger.debug(sql);
            insertadoBoolean = statement.execute(sql);

            Iterator<String> iterator = usuario.getFuncionalidadStrings().iterator();
            while (iterator.hasNext()) {
                String fun = iterator.next();
                Funcionalidad funcionalidad = new FuncionalidadDAO().getPorDescripcion(fun);
                Long id = new UtilidadesDAO().getSiguienteId("us_funcionalidad");
                sql = " INSERT INTO us_funcionalidad "
                        + " (id,idusuario,idfuncionalidad,permitida,estado,fechacambio,usucambio) " + " VALUES (" + id
                        + "," + usuario.getId() + "," + funcionalidad.getId() + ",1," + Constantes.BBDD_ACTIVOSI + ","
                        + +Utilidades.getFechayyymmdd(LocalDate.now()) + "," + usuario.getId() + ")";
                insertadoBoolean = statement.execute(sql);
                logger.debug(sql);
            }

            insertadoBoolean = true;
            statement.close();

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

    public boolean actualizaDatos(Usuario usuario) {
        Connection connection = null;
        Boolean insertadoBoolean = false;
        try {
            Usuario usuarioa = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
            connection = super.getConexionBBDD();

            sql = " UPDATE usuarios SET dni='" + usuario.getDni() + "',apellido1='" + usuario.getApellido1()
                    + "',apellido2='" + usuario.getApellido2() + "',nombre='" + usuario.getNombre() + "',mail='"
                    + usuario.getMail() + "', telefono='" + usuario.getTelefono() + "',   usucambio='"
                    + usuarioa.getId() + "',  fechacambio=" + Utilidades.getFechayyymmdd(LocalDate.now()) + " WHERE id="
                    + usuario.getId();
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

    public boolean actualizaMailTf(Usuario usuario) {
        Connection connection = null;
        Boolean actualizado = false;
        if (!usuario.getMail().isEmpty() && !usuario.getTelefono().isEmpty()) {
            try {

                connection = super.getConexionBBDD();

                sql = " UPDATE usuarios SET mail='" + usuario.getMail() + "', telefono='" + usuario.getTelefono()
                        + "',   fechacambio=" + Utilidades.getFechayyymmdd(LocalDate.now()) + " WHERE id="
                        + usuario.getId();
                Statement statement = connection.createStatement();
                actualizado = statement.execute(sql);
                actualizado = true;
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
        }

        return actualizado;
    }

    public Long insertaUsuario(Usuario usuario) {
        Connection connection = null;
        boolean insertado = false;
        Long id = null;
        try {
            connection = super.getConexionBBDD();
            id = new UtilidadesDAO().getSiguienteId("usuarios");
            usuario.setId(id);
            usuario.setEstado(Usuario.USUARIO_ACTIVO);
            Usuario usuCambio = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);

            sql = " INSERT INTO usuarios (id,dni,apellido1,apellido2,nombre, mail,telefono,usucambio,fechacambio,estado) "
                    + " VALUES (?,?,?,?,?,?,?,?,?,?)  ";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setLong(1, usuario.getId());
            statement.setString(2, usuario.getDni());
            statement.setString(3, usuario.getApellido1());
            statement.setString(4, usuario.getApellido2());
            statement.setString(5, usuario.getNombre());
            statement.setString(6, usuario.getMail());
            statement.setString(7, usuario.getTelefono());
            if (usuCambio == null) {
                statement.setLong(8, usuario.getId());
            } else {
                statement.setLong(8, usuCambio.getId());
            }

            statement.setLong(9, Utilidades.getFechayyymmdd(LocalDate.now()));
            statement.setInt(10, usuario.getEstado());
            insertado = statement.executeUpdate() > 0;
            statement.close();
            logger.debug(" INSERT INTO usuarios (id,dni,apellido1,apellido2,nombre, estado, mail) VALUES ('"
                    + usuario.getId() + "," + usuario.getDni() + "','" + usuario.getApellido1() + "','"
                    + usuario.getApellido2() + "','" + usuario.getNombre() + "'," + usuario.getEstado() + ",'"
                    + usuario.getMail() + "')  ");
        } catch (SQLException e) {
            logger.error(" INSERT INTO usuarios (id,dni,apellido1,apellido2,nombre, estado, mail) VALUES ('"
                    + usuario.getId() + "," + usuario.getDni() + "','" + usuario.getApellido1() + "','"
                    + usuario.getApellido2() + "','" + usuario.getNombre() + "'," + usuario.getEstado() + ",'"
                    + usuario.getMail() + "')");
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
        return id;
    }

    public boolean borraDatos(Usuario usuario) {
        Connection connection = null;
        Boolean insertadoBoolean = false;
        try {
            Usuario usuarioa = (Usuario) VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME);
            connection = super.getConexionBBDD();

            sql = " UPDATE usuarios SET   usucambio='" + usuarioa.getDni() + "',  fechacambio="
                    + Utilidades.getFechayyymmdd(LocalDate.now()) + ",estado=" + Constantes.BBDD_ACTIVONO + " WHERE id="
                    + usuarioa.getId();
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

    public ArrayList<Usuario> getListaUsuario() {
        Connection connection = null;
        ArrayList<Usuario> lista = new ArrayList<>();

        try {
            connection = super.getConexionBBDD();

            sql = " SELECT * FROM  usuarios WHERE estado = " + Constantes.BBDD_ACTIVOSI
                    + " ORDER BY apellido1,apellido2,nombre	";

            Statement statement = connection.createStatement();
            ResultSet resulSet = statement.executeQuery(sql);
            while (resulSet.next()) {
                Usuario usuario = getUsuarioResulset(resulSet);
                lista.add(usuario);
            }
            statement.close();
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
        return lista;
    }

    public static Usuario getUsuairoResulSetJimena(ResultSet resulSet, Usuario usuarioParam) {
        Usuario usuario = null;
        if (usuarioParam != null) {
            usuario = usuarioParam;
        } else {
            try {
                usuario = new Usuario();
                usuario.setDni(resulSet.getString("usuuserid"));
                usuario.setApellido1(resulSet.getString("usuapellido1"));
                usuario.setApellido2(resulSet.getString("usuapellido2"));
                usuario.setNombre(resulSet.getString("usunombre"));
                usuario.setEstado(resulSet.getInt("usuestado"));
            } catch (SQLException e) {
                logger.error(Notificaciones.SQLERRORRESULSET, e);
            }
        }
        return usuario;
    }
}
