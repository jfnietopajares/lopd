package com.hnss.controlador;

import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.Cookie;

import com.hnss.entidades.Usuario;
import com.hnss.excepciones.LoginException;
import com.hnss.excepciones.PasswordException;
import com.hnss.excepciones.UsuarioBajaException;
import com.hnss.utilidades.Constantes;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinService;
import com.vaadin.server.VaadinSession;

/**
 * The Class AuthService. Modificada a partir de la clase de internet.
 *
 * @author Alejandro Duarte.
 * 
 * 
 * @author Juan Nieto
 * @version 23.5.2018
 */
public class AuthService {

	/**
	 * Checks if is authenticated.
	 *
	 * @return true, if is authenticated
	 */
	public static boolean isAuthenticated() {
		return VaadinSession.getCurrent().getAttribute(Constantes.SESSION_USERNAME) != null || loginRememberedUser();
	}

	/**
	 * Login.
	 *
	 * @param username   the username
	 * @param password   the password
	 * @param rememberMe the remember me
	 * @return true, if successful
	 * @throws LoginException       the login exception
	 * @throws UsuarioBajaException
	 * @throws PasswordException
	 */
	public static Usuario login(Usuario usuario, boolean rememberMe)
			throws LoginException, UsuarioBajaException, PasswordException {

		Usuario usuarioLogeado = UserService.isAuthenticUser(usuario.getDni(), usuario.getClave());
		usuarioLogeado.setLlamadaExterna(false);
		VaadinSession.getCurrent().setAttribute(Constantes.SESSION_USERNAME, usuarioLogeado);
		if (rememberMe) {
			rememberUser(usuarioLogeado);
		}
		return usuarioLogeado;
	}

	/**
	 * Log out.
	 */
	public static void logOut() {
		Optional<Cookie> cookie = getRememberMeCookie();
		if (cookie.isPresent()) {
			String id = cookie.get().getValue();
			UserService.removeRememberedUser(id);
			deleteRememberMeCookie();
		}
		VaadinSession.getCurrent().close();
		Page.getCurrent().setLocation("");
	}

	/**
	 * Gets the remember me cookie.
	 *
	 * @return the remember me cookie
	 */
	private static Optional<Cookie> getRememberMeCookie() {
		Cookie[] cookies = VaadinService.getCurrentRequest().getCookies();
		return Arrays.stream(cookies).filter(c -> c.getName().equals(Constantes.SESSION_USERNAME)).findFirst();
	}

	/**
	 * Login remembered user.
	 *
	 * @return true, if successful
	 */
	private static boolean loginRememberedUser() {
		Optional<Cookie> rememberMeCookie = getRememberMeCookie();
		if (rememberMeCookie.isPresent()) {
			String id = rememberMeCookie.get().getValue();
			String username = UserService.getRememberedUser(id);
			if (username != null) {
				VaadinSession.getCurrent().setAttribute(Constantes.SESSION_USERNAME, username);
				return true;
			}
		}
		return false;
	}

	/**
	 * Remember user.
	 *
	 * @param usuario the usuario
	 */
	private static void rememberUser(Usuario usuario) {
		Cookie cookie = new Cookie(Constantes.SESSION_USERNAME, usuario.getDni());
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60 * 24 * 30);
		VaadinService.getCurrentResponse().addCookie(cookie);
	}

	/**
	 * Delete remember me cookie.
	 */
	private static void deleteRememberMeCookie() {
		Cookie cookie = new Cookie(Constantes.SESSION_USERNAME, "");
		cookie.setPath("/");
		cookie.setMaxAge(0);
		VaadinService.getCurrentResponse().addCookie(cookie);
	}

}