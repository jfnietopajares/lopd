package com.hnss.utilidades;

import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hnss.dao.UsuarioDAO;
import com.hnss.entidades.Usuario;
import com.hnss.excepciones.LoginException;
import com.hnss.excepciones.PasswordException;
import com.hnss.excepciones.UsuarioBajaException;
import com.hnss.ui.Notificaciones;

/**
 */
/*
 * Clase para autentificar usuario a travÃ©s del servidor LDAP
 * 
 */
public class LDAP {

	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(LDAP.class);

	/**
	 * Login.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return the usuario
	 * @throws LoginException       the login exception
	 * @throws UsuarioBajaException
	 * @throws PasswordException
	 */
	public static Usuario login(String user, String password)
			throws LoginException, PasswordException, UsuarioBajaException {

		Usuario usuario = null;

		try {
			usuario = loginActiveDirectory(user, password);
			new Notificaciones("Conectado DA");
		} catch (Exception e) {
			try {
				usuario = loginLDAP(user, password);
				new Notificaciones("Conectado ldap");
			} catch (Exception e1) {
				// usuario = LoginBBDD(user, password);
				new Notificaciones("Sin conexion DA LDAP");
			}
		}
		return usuario;
	}

	/**
	 * Login BBDD.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return the usuario
	 * @throws LoginException       the login exception
	 * @throws PasswordException
	 * @throws UsuarioBajaException
	 */
	static Usuario LoginBBDD(String user, String password)
			throws LoginException, PasswordException, UsuarioBajaException {
		Usuario usuario = new UsuarioDAO().getUsuarioLogin(user, true);

		/*
		 * if (usuario.getEstado() == 0) { throw new
		 * UsuarioBajaException(PantallaLogin.LOGIN_USUARIO_NOACTIVO); }
		 */
		/**
		 * else
		 * 
		 * if
		 * (!DigestUtils.sha1Hex(password.trim()).equals(usuario.getPasswordhash().trim()))
		 * { throw new PasswordException(PantallaLogin.LOGIN_CONTRASEÑAINCORRECTA); }
		 */
		return usuario;

	}

	/**
	 * Login active directory.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return the usuario
	 * @throws LoginException the login exception
	 */
	static Usuario loginActiveDirectory(String user, String password) throws LoginException {

		Usuario usuario = new Usuario();
		try {
			Hashtable<String, String> envInicial = new Hashtable<String, String>();

			String fileSeparator = System.getProperty("file.separator");
			String javaHome = System.getProperty("java.home");
			String keystore = null;
			if (javaHome.indexOf("jre") == -1) {
				keystore = javaHome + fileSeparator + "jre" + fileSeparator + "lib" + fileSeparator + "security"
						+ fileSeparator + "ldap";
			} else {
				keystore = javaHome + fileSeparator + "lib" + fileSeparator + "security" + fileSeparator + "ldap";
			}
			System.setProperty("javax.net.ssl.trustStore", keystore);
			System.setProperty("javax.net.ssl.trustStorePassword", "ldap123");

			envInicial.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");

			envInicial.put(Context.PROVIDER_URL, "ldaps://hnssntdc0001.grs.root");
			envInicial.put(Context.SECURITY_AUTHENTICATION, "simple");
			envInicial.put(Context.SECURITY_PRINCIPAL, user + "@GRS.ROOT");
			envInicial.put(Context.SECURITY_CREDENTIALS, password);
			envInicial.put(Context.REFERRAL, "follow");
			envInicial.put("com.sun.jndi.ldap.read.timeout", "3000");

			DirContext ctx = new InitialDirContext(envInicial);

			String filter = "(&(objectClass=user)(samaccountname=" + user + "))";
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(new String[] { "sn", "givenName", "uid" });
			NamingEnumeration answer = ctx.search("dc=grs,dc=root", filter, ctls);
			while (answer.hasMoreElements()) {
				SearchResult searchResult = (SearchResult) answer.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attUid = attributes.get("uid");
				Attribute attNombre = attributes.get("givenName");
				Attribute attApellidos = attributes.get("sn");
				if (attUid != null) {
					usuario.setDni(String.valueOf(attUid.get()));
					usuario.setDni(user);
				}
				if (attNombre != null) {
					usuario.setApellidosNombre(String.valueOf(attNombre.get()));
				}
				if (attApellidos != null) {
					// usuario.setApellidos(String.valueOf(attApellidos.get()));
					usuario.setApellidosNombre(String.valueOf(attApellidos.get()));
				}
			}

			ctx.close();
		} catch (AuthenticationException e) {
			logger.error("Error de autentificacion", e);
			new Notificaciones("ldap.loginActiveDirectory : Error de autentificacion", true);
			throw new LoginException("Error de autentificacion", e);
		} catch (NamingException e) {
			logger.error("LDAP no funciona NamingException", e);
			// new NotificacionInfo("ldap.loginActiveDirectory : . Error de NamingException
			// ", true);
			throw new LoginException("Active Directory no funciona", e);
		} catch (Exception e) {
			logger.error("Error desconocido", e);
			// new NotificacionInfo("ldap.loginActiveDirectory : . Error de NamingException
			// ", true);
			throw new LoginException("Active Directory no funciona", e);
		}
		return usuario;

	}

	/**
	 * Login LDAP.
	 *
	 * @param user     the user
	 * @param password the password
	 * @return the usuario
	 * @throws LoginException the login exception
	 */
	static Usuario loginLDAP(String user, String password) throws LoginException {

		Usuario usuario = new Usuario();

		String url = "ldaps://ldap.hnss.sacyl.es:636/dc=sacyl,dc=es";
		String LDAP_ADMIN_PASSWORD = "ObCejSoafOw0";
		String LDAP_ADMIN_DN = "cn=Application Manager,cn=config";
		String LDAP_FILTRO = "(&(ou:dn:=People)(objectClass=posixAccount)(uid=%s))";

		String userDN = null;

		/*
		 * Asignamos las propiedades del sistema para poder conectar con el LDAP
		 * mediante SSL
		 */

		String fileSeparator = System.getProperty("file.separator");
		String javaHome = System.getProperty("java.home");
		String keystore = null;
		if (javaHome.indexOf("jre") == -1) {
			keystore = javaHome + fileSeparator + "jre" + fileSeparator + "lib" + fileSeparator + "security"
					+ fileSeparator + "ldap";
		} else {
			keystore = javaHome + fileSeparator + "lib" + fileSeparator + "security" + fileSeparator + "ldap";
		}
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", "ldap123");

		try {

			/*
			 * Conectamos con LDAP - busqueda inicial para encontrar el DN del usuario
			 */

			Hashtable<String, String> envInicial = new Hashtable<String, String>();

			envInicial.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			envInicial.put(Context.PROVIDER_URL, url);
			envInicial.put(Context.SECURITY_AUTHENTICATION, "simple");
			envInicial.put(Context.SECURITY_PRINCIPAL, LDAP_ADMIN_DN);
			envInicial.put(Context.SECURITY_CREDENTIALS, LDAP_ADMIN_PASSWORD);

			logger.debug("Conectando con url: " + url);
			logger.debug("Conectando con cadena: " + "cn=Application Manager,cn=config");
			DirContext ctxInicial = new InitialDirContext(envInicial);

			// Comenzamos a buscar
			SearchControls ctlsInicial = new SearchControls();
			ctlsInicial.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctlsInicial.setReturningAttributes(new String[] {});
			String filterInicial = String.format(LDAP_FILTRO, new Object[] { user });
			NamingEnumeration answerInicial = ctxInicial.search("", filterInicial, ctlsInicial);
			while (answerInicial.hasMore()) {

				SearchResult searchResult = (SearchResult) answerInicial.next();
				userDN = searchResult.getNameInNamespace();

			}

			ctxInicial.close();

			if (userDN == null) {
				logger.warn("Error de autentificacion");
				new Notificaciones("ldap.loginLDAP : . Error de autentificacion", true);
				throw new LoginException("Error de autentificacion");
			}

			/*
			 * Conectamos con LDAP - busqueda para autentificar al usuario
			 */

			Hashtable<String, String> envUser = new Hashtable<String, String>();

			envUser.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			envUser.put(Context.PROVIDER_URL, url);
			envUser.put(Context.SECURITY_AUTHENTICATION, "simple");
			envUser.put(Context.SECURITY_PRINCIPAL, userDN);
			envUser.put(Context.SECURITY_CREDENTIALS, password);
			logger.debug("Conectando con url: " + url);
			logger.debug("Conectando con cadena: " + userDN);
			DirContext ctxUsuario = new InitialDirContext(envUser);

			int counter = 0;

			// Comenzamos a buscar
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(new String[] { "uid", "givenName", "sn", "objectClass", "departmentNumber",
					"title", "sacylPlaceCode", "hurhsiclex003Perfil", "sacylAdministrativeCode" });
			String filter = String.format(LDAP_FILTRO, new Object[] { user });
			NamingEnumeration answer = ctxUsuario.search("", filter, ctls);
			while (answer.hasMore()) {
				counter++;
				SearchResult searchResult = (SearchResult) answer.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attNombre = attributes.get("givenName");
				Attribute attApellidos = attributes.get("sn");
				usuario.setDni(user);
				usuario.setApellidosNombre(attNombre.get().toString());
				usuario.setApellidosNombre(attApellidos.get().toString());

			}

			ctxUsuario.close();

			if (counter == 0) {
				logger.warn("No se han encontrado usuarios LDAP [user = " + user + "]");
				new Notificaciones("ldap.loginLDAP : No se han encontrado usuarios LDAP [user = " + user + "]");
			} else if (counter > 1) {
				logger.warn("Se ha encontrado mÃ¡s de un usuarios LDAP [user = " + user + "]");
				new Notificaciones("ldap.loginLDAP : Se ha encontrado mÃ¡s de un usuarios LDAP [user =  " + user + "]");
			}

			return usuario;

		} catch (AuthenticationException e) {
			logger.error("Error de autentificacion", e);
			new Notificaciones("ldap.loginLDAP :  AuthenticationException  error de autentificacion ");
			throw new LoginException("Error de autentificacion", e);
		} catch (NamingException e) {
			logger.error("LDAP no funciona", e);
			new Notificaciones("ldap.loginLDAP :  NamingException  error de conexion ");
			throw new LoginException("LDAP no funciona", e);
		} catch (Exception e) {
			logger.error("Error desconocido", e);
			new Notificaciones("ldap.loginLDAP :   error desconocido  ");
			throw new LoginException("LDAP no funciona", e);
		}

	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		String url = "ldaps://ldap.hnss.sacyl.es:636/dc=sacyl,dc=es";
		String LDAP_ADMIN_PASSWORD = "ObCejSoafOw0";
		String LDAP_ADMIN_DN = "cn=Application Manager,cn=config";
		String LDAP_FILTRO = "(&(ou:dn:=People)(objectClass=posixAccount)(uid=%s))";

		String userDN = null;

		String user = "13152398D";
		String password = "eerror!";

		/*
		 * Asignamos las propiedades del sistema para poder conectar con el LDAP
		 * mediante SSL
		 */

		String fileSeparator = System.getProperty("file.separator");
		String javaHome = System.getProperty("java.home");
		String keystore = null;
		if (javaHome.indexOf("jre") == -1) {
			keystore = javaHome + fileSeparator + "jre" + fileSeparator + "lib" + fileSeparator + "security"
					+ fileSeparator + "ldap";
		} else {
			keystore = javaHome + fileSeparator + "lib" + fileSeparator + "security" + fileSeparator + "ldap";
		}
		System.setProperty("javax.net.ssl.trustStore", keystore);
		System.setProperty("javax.net.ssl.trustStorePassword", "ldap123");

		try {

			/*
			 * Conectamos con LDAP - busqueda inicial para encontrar el DN del usuario
			 */

			Hashtable<String, String> envInicial = new Hashtable<String, String>();

			envInicial.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			envInicial.put(Context.PROVIDER_URL, url);
			envInicial.put(Context.SECURITY_AUTHENTICATION, "simple");
			envInicial.put(Context.SECURITY_PRINCIPAL, LDAP_ADMIN_DN);
			envInicial.put(Context.SECURITY_CREDENTIALS, LDAP_ADMIN_PASSWORD);

			logger.debug("Conectando con url: " + url);
			logger.debug("Conectando con cadena: " + "cn=Application Manager,cn=config");

			DirContext ctxInicial = new InitialDirContext(envInicial);

			// Comenzamos a buscar
			SearchControls ctlsInicial = new SearchControls();
			ctlsInicial.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctlsInicial.setReturningAttributes(new String[] {});
			String filterInicial = String.format(LDAP_FILTRO, new Object[] { user });
			NamingEnumeration answerInicial = ctxInicial.search("", filterInicial, ctlsInicial);
			while (answerInicial.hasMore()) {

				SearchResult searchResult = (SearchResult) answerInicial.next();
				userDN = searchResult.getNameInNamespace();

			}

			ctxInicial.close();

			if (userDN == null) {
				logger.error("No se encuentra el usuario");
			}

			/*
			 * Conectamos con LDAP - busqueda para autentificar al usuario
			 */

			Hashtable<String, String> envUser = new Hashtable<String, String>();

			envUser.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			envUser.put(Context.PROVIDER_URL, url);
			envUser.put(Context.SECURITY_AUTHENTICATION, "simple");
			envUser.put(Context.SECURITY_PRINCIPAL, userDN);
			envUser.put(Context.SECURITY_CREDENTIALS, password);

			logger.debug("Conectando con url: " + url);
			logger.debug("Conectando con cadena: " + userDN);

			DirContext ctxUsuario = new InitialDirContext(envUser);

			int counter = 0;

			// Comenzamos a buscar
			SearchControls ctls = new SearchControls();
			ctls.setSearchScope(SearchControls.SUBTREE_SCOPE);
			ctls.setReturningAttributes(new String[] { "uid", "givenName", "sn", "objectClass", "departmentNumber",
					"title", "sacylPlaceCode", "hurhsiclex003Perfil", "sacylAdministrativeCode" });
			String filter = String.format(LDAP_FILTRO, new Object[] { user });
			NamingEnumeration answer = ctxUsuario.search("", filter, ctls);
			while (answer.hasMore()) {
				counter++;
				SearchResult searchResult = (SearchResult) answer.next();
				Attributes attributes = searchResult.getAttributes();
				Attribute attNombre = attributes.get("givenName");
				Attribute attApellidos = attributes.get("sn");
				logger.info(
						"Login correcto usuario " + attNombre.get().toString() + " " + attApellidos.get().toString());
			}

			ctxUsuario.close();

			if (counter == 0) {
				logger.warn("No se han encontrado usuarios LDAP [user = " + user + "]");
			} else if (counter > 1) {
				logger.warn("Se ha encontrado mÃ¡s de un usuarios LDAP [user = " + user + "]");
			}

		} catch (AuthenticationException e) {
			// Error con la contraseÃ±a
			logger.warn("Error de autentificaciÃ³n");
		} catch (NamingException e) {
			logger.error("Problem getting attribute:" + e.toString());
		}

	}

}