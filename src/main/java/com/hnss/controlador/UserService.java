package com.hnss.controlador;

import java.util.HashMap;
import java.util.Map;

import com.hnss.dao.UsuarioDAO;
import com.hnss.entidades.Usuario;
import com.hnss.excepciones.LoginException;
import com.hnss.excepciones.PasswordException;
import com.hnss.excepciones.UsuarioBajaException;
import com.hnss.lopdcaa.MyUI;
import com.hnss.utilidades.Constantes;
import com.hnss.utilidades.LDAP;
import com.hnss.utilidades.Parametros;
import com.vaadin.server.VaadinSession;

/**
 * The Class UserService. Modificado de la original
 *
 * @author Alejandro Duarte.
 * 
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class UserService {

	/** The remembered users. */
	private static Map<String, String> rememberedUsers = new HashMap<>();

	/** The usuario. */
	private static Usuario usuario = null;

	/**
	 * Checks if is authentic user.
	 *
	 * @param username the username
	 * @param password the password
	 * @return the usuario
	 * @throws LoginException    the login exception
	 * @throws PasswordException
	 */
	public static Usuario isAuthenticUser(String username, String password)
			throws LoginException, UsuarioBajaException, PasswordException {

		String valorString = (String) MyUI.objParametros.get(Parametros.KEY_LDAP);

		if (valorString.trim().equals(new String("SI"))) {
			Usuario usDA = LDAP.login(username, password);
			if (usDA != null) {
				usuario = new UsuarioDAO().getUsuarioDni(username, true);

			}
		} else {
			usuario = new UsuarioDAO().getUsuarioLogin(username, true);
		}
		VaadinSession.getCurrent().setAttribute(Constantes.SESSION_USERNAME, usuario);
		return usuario;
	}

	/**
	 * Gets the usuario.
	 *
	 * @return the usuario
	 */
	public static Usuario getUsuario() {
		return usuario;
	}

	/**
	 * Gets the remembered user.
	 *
	 * @param id the id
	 * @return the remembered user
	 */
	public static String getRememberedUser(String id) {
		return rememberedUsers.get(id);
	}

	/**
	 * Removes the remembered user.
	 *
	 * @param id the id
	 */
	public static void removeRememberedUser(String id) {
		rememberedUsers.remove(id);
	}

}