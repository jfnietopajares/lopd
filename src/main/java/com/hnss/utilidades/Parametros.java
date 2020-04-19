package com.hnss.utilidades;

/**
 *
 * Si el fichero no exite, lo crea con los valores por defecto
 * 
 *  @author: Juan Nieto
 *  @param :pr Objeto tipo properties
 *  @return void. Carga en el objeto properties los valores del fichero 
 *  @version: 26/02/2019 Version 1.0

*/
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.hnss.entidades.ParametProperties;

// TODO: Auto-generated Javadoc
/**
 * The Class ParametrosGenerales.
 *
 * @author JuanNieto
 * @version 26/02/2019 Gestión de parámetros generales
 * @return Properties getParametros() Clase ParametrosGenerales Si existe el
 *         fichero Constantes.NOMBREFICHEROPROPERTEIS en el directorio de
 *         trabajo recupera los valores si no existe crea el fichero con los
 *         valores por defecto puestos a puñetazos en el método
 */

public class Parametros {

	public Properties myProperties = new Properties();

	public static final Logger logger = LogManager.getLogger(Parametros.class);

	public static final String PROPERTIESDESCRIPCIONFICHERO = " Parámetro de configuración del programa";

	public static final String PROPERTIESMENSAJECREACION = " Fichero de configuracion creado";

	public static final String PROPERTIESMENSAJELECTURA = " Fichero de configuracion leido";

	public static final String PROPERTIESMENSAJEERROR = " Error creando fichero de configuracion ";

	/*
	 * De login
	 */
	public final static String KEY_INTENTOS_LOGIN = "INTENTOS_LOGIN";
	public final static String VALUE_INTENTOS_LOGIN = "5";

	public final static String KEY_MAXIMO_NUMERO_HISTORIA = "MAXIMO_NUMERO_HISTORIA";
	public final static String VALUE_MAXIMO_NUMERO_HISTORIA = "1000000";

	/*
	 * De envio de mail
	 * 
	 * “mail.smtp.ssl.trust”, “*”); .
	 */
	public final static String KEY_MIALHOST = "MIALHOST";
	public final static String VALUE_MIALHOST = "outlook.office365.com";
//	public final static String VALUE_MIALHOST = "smtp.gmail.com";

	public final static String KEY_MIALSTARTTLS = "MIALSTARTTLS";
	public final static String VALUE_MIALSTARTTLS = "true";

	public final static String KEY_MIALPORT = "MIALPORT";
	public final static String VALUE_MIALPORT = "25";
	// public final static String VALUE_MIALPORT = "587";

	public final static String KEY_MAILEMISOR = "MAILEMISOR";
	public final static String VALUE_MAILEMISOR = "avisos.hnss@saludcastillayleon.es";
	// public final static String VALUE_MAILEMISOR = "jfnpjfnp@gmail.com";

	public final static String KEY_MAILUSER = "MAILUSER";
//	public final static String VALUE_MAILUSER = "06551256M@saludcastillayleon.es";
	public final static String VALUE_MAILUSER = "06384936K@saludcastillayleon.es";
//	public final static String VALUE_MAILUSER = "jfnpjfnp@gmail.com";

	public final static String KEY_MAILPASSW = "MAILPASSW";
	public final static String VALUE_MAILPASSW = "12345678";

	public final static String KEY_MAILAUTH = "MAILAUTH";
	public final static String VALUE_MAILAUTH = "true";

	public final static String KEY_MAILDESTINATARIOS = "MAILDESTINATARIOS";
	public final static String VALUE_MAILDESTINATARIOS = "jfnietopajares@gmail.com";

	public final static String KEY_PERSISTENCIA = "PERSISTENCIA";
	public final static String VALUE_PERSISTENCIA = "ORACLE";

	/*
	 * Conexion ldap
	 * 
	 */

	public final static String KEY_LDAP = "LDAP";
	public final static String VALUE_LDAP = "NO";

	public final static String KEY_PROVIDER_URL = "LDAP_PROVIDER_URL";
	public final static String VALUE_PROVIDER_URL = "ldaps://hnssntdc0001.grs.root";

	public final static String KEY_SECURITY_AUTHENTICATION = "LDAP_SECURITY_AUTHENTICATION";
	public final static String VALUE_SECURITY_AUTHENTICATION = "simple";

	public final static String KEY_SECURITY_PRINCIPAL = "LDAP_SECURITY_PRINCIPAL";
	public final static String VALUE_SECURITY_PRINCIPAL = "@GRS.ROOT";

	public final static String KEY_REFERRAL = "LDAP_REFERRAL";
	public final static String VALUE_REFERRAL = "follow";

	public final static String KEY_LDAP_TIMEOUT = "LDAP_TIMEOUT";
	public final static String VALUE_LDAP_TIMEOUT = "3000";

	public final static String KEY_LDAP_KEYSTOREPASSWD = "LDAP_KEYSTOREPASSWD";
	public final static String VALUE_LDAP_KEYSTOREPASSWD = "ldap123";

	public final static String KEY_NUMERO_REGISTROS_PAGINACION = "REGISTROS_PAGINACION_CORTA";
	public final static String VALUE_NUMERO_REGISTROS_PAGINACION = "5";

	public final static String KEY_NUMERO_REGISTROS_PAGINACION_PACIENTES = "REGISTROS_PAGINACION_LARGA";
	public final static String VALUE_NUMERO_REGISTROS_PAGINACION_PACIENTES = "10";

	public final static String KEY_AREA_SALUD = "AREA_SALUD";
	public final static String VALUE_AREA_SALU_STRING = "1";

	/**
	 * Constructor:.
	 * 
	 * @throws Exception
	 */
	public Parametros() throws Exception {
		FileInputStream is = null;
		File fconfig = new File(Constantes.PROPERTIESNOMBREFICHERO);
		try {
			if (fconfig.exists() && fconfig.isFile()) {
				is = new FileInputStream(fconfig);
				myProperties.load(is);
				logger.info(Parametros.PROPERTIESMENSAJELECTURA + Constantes.PROPERTIESNOMBREFICHERO);
			} else {
				creaFichero();
			}
		} catch (IOException e) {
			throw e;
		} catch (Exception e1) {
			throw e1;
		}

		finally {
			if (is != null)
				try {
					is.close();
				} catch (IOException e) {
					logger.error(Parametros.PROPERTIESMENSAJEERROR + Constantes.PROPERTIESNOMBREFICHERO);
				}
		}
	}

	/**
	 * Gets the parametros.
	 *
	 * @return el objeto properties con los valores
	 */
	public Properties getParametros() {
		return myProperties;
	}

	/**
	 * Crea fichero.
	 *
	 * @throws IOException Carga los valores por defecto en el objeto properties y
	 *                     lo almacena en el fichero
	 *                     Constantes.CONFIGURACIONNOMBREFICHERO)
	 */
	public void creaFichero() throws IOException, Exception {
		FileOutputStream salida = null;
		ArrayList<ParametProperties> listaParametros = new ArrayList<>();
		try {

			listaParametros.add(new ParametProperties(KEY_INTENTOS_LOGIN, VALUE_INTENTOS_LOGIN));
			listaParametros.add(new ParametProperties(KEY_MAXIMO_NUMERO_HISTORIA, VALUE_MAXIMO_NUMERO_HISTORIA));

			listaParametros.add(new ParametProperties(KEY_MIALHOST, VALUE_MIALHOST));
			listaParametros.add(new ParametProperties(KEY_MIALSTARTTLS, VALUE_MIALSTARTTLS));
			listaParametros.add(new ParametProperties(KEY_MIALPORT, VALUE_MIALPORT));
			listaParametros.add(new ParametProperties(KEY_MAILEMISOR, VALUE_MAILEMISOR));
			listaParametros.add(new ParametProperties(KEY_MAILUSER, VALUE_MAILUSER));

			listaParametros.add(new ParametProperties(KEY_MAILPASSW, VALUE_MAILPASSW));
			listaParametros.add(new ParametProperties(KEY_MAILAUTH, VALUE_MAILAUTH));
			listaParametros.add(new ParametProperties(KEY_MAILDESTINATARIOS, VALUE_MAILDESTINATARIOS));

			listaParametros.add(new ParametProperties(KEY_PERSISTENCIA, VALUE_PERSISTENCIA));

			listaParametros.add(new ParametProperties(KEY_LDAP, VALUE_LDAP));
			listaParametros.add(new ParametProperties(KEY_PROVIDER_URL, VALUE_PROVIDER_URL));
			listaParametros.add(new ParametProperties(KEY_SECURITY_AUTHENTICATION, VALUE_SECURITY_AUTHENTICATION));
			listaParametros.add(new ParametProperties(KEY_SECURITY_PRINCIPAL, VALUE_SECURITY_PRINCIPAL));
			listaParametros.add(new ParametProperties(KEY_REFERRAL, VALUE_REFERRAL));
			listaParametros.add(new ParametProperties(KEY_LDAP_TIMEOUT, VALUE_LDAP_TIMEOUT));
			listaParametros.add(new ParametProperties(KEY_LDAP_KEYSTOREPASSWD, VALUE_LDAP_KEYSTOREPASSWD));

			listaParametros
					.add(new ParametProperties(KEY_NUMERO_REGISTROS_PAGINACION, VALUE_NUMERO_REGISTROS_PAGINACION));

			listaParametros.add(new ParametProperties(KEY_NUMERO_REGISTROS_PAGINACION_PACIENTES,
					VALUE_NUMERO_REGISTROS_PAGINACION_PACIENTES));

			listaParametros.add(new ParametProperties(KEY_AREA_SALUD, VALUE_AREA_SALU_STRING));

			/*
			 * Iterator<ParametProperties> it = listaParametros.iterator(); while
			 * (it.hasNext()) { ParametProperties parametro = new ParametProperties();
			 * parametro = it.next(); myProperties.setProperty(parametro.getNombre(),
			 * parametro.getValor()); }
			 */
			for (ParametProperties parametro : listaParametros) {
				myProperties.setProperty(parametro.getNombre(), parametro.getValor());
			}
			salida = new FileOutputStream(Constantes.PROPERTIESNOMBREFICHERO);
			myProperties.store(salida, Parametros.PROPERTIESDESCRIPCIONFICHERO);
			logger.info(Parametros.PROPERTIESMENSAJECREACION + " " + Constantes.PROPERTIESNOMBREFICHERO);

		} catch (IOException e) {
			logger.error(Parametros.PROPERTIESMENSAJEERROR + " " + Constantes.PROPERTIESNOMBREFICHERO, e);
			throw e;
		} catch (Exception e1) {
			logger.error(Parametros.PROPERTIESMENSAJEERROR + " " + Constantes.PROPERTIESNOMBREFICHERO, e1);
			throw e1;
		} finally {
			if (salida != null) {
				salida.close();
			}
		}
		logger.info(Parametros.PROPERTIESMENSAJECREACION + Constantes.PROPERTIESNOMBREFICHERO);
	}

	/**
	 * Este método muestra los valores de los parámetros. Sirve de control del
	 * programa para ver el funcionamiento correcto.
	 *
	 * @return the string
	 */
	public static String verParametros(Properties myProperties) {

		Enumeration<Object> keys = myProperties.keys();
		String textoHtml = "<b>Nombre del fichero:</b>" + Constantes.PROPERTIESNOMBREFICHERO + "<br>";
		while (keys.hasMoreElements()) {
			Object key = keys.nextElement();
			textoHtml = textoHtml.concat(key + "=" + myProperties.get(key) + "<br>");
		}
		return textoHtml;
	}

}
