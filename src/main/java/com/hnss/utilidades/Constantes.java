package com.hnss.utilidades;

import java.util.ArrayList;

public abstract class Constantes {

    public static final String COOKIE_NAME = "sesion-usuario";

    public static final String SESSION_USERNAME = "usuario";

    public static final String PROPERTIESNOMBREFICHERO = System.getProperty("catalina.home")
            + System.getProperty("file.separator") + "conf" + System.getProperty("file.separator") + "lopd.properties";

    public static final String DIRECTORIOREPORTS = System.getProperty("catalina.home")
            + System.getProperty("file.separator") + "webapps" + System.getProperty("file.separator") + "reports"
            + System.getProperty("file.separator");

    public static final String URLREPORTS = System.getProperty("file.separator") + "webapps"
            + System.getProperty("file.separator") + "reports" + System.getProperty("file.separator");

    public final static String SEPARADOR_FECHA = "/";

    public final static String APLICACION_VERSION = "Lopd  1.0 de 1 de octubre de 2019";

    public final static String APLICACION_NOMBRE_VENTANA = "LOPD";

    public final static String APLICACION_TITULO_PROGRAMA = "<h2><b>LOPD</b></h2>";

    public final static String APLICACION_PIE = "© Servicio de Informática (2019) ";

    public final static String MYSQL_STRING = "MYSQL";

    public final static String ORACLE_STRING = "ORACLE";

    public final static String CONFIRMACION_TITULO = "Confirmación de acción:";

    public final static String CONFIRMACION_BORRADO_MENSAJE = "¿Seguro que quieres borrar estos datos ?";

    public final static String CONFIRMACION_SALIR_MENSAJE = "¿Seguro que quieres salir del programa ?";

    public final static String CONFIRMACION_BOTONSI = "Si";

    public final static String CONFIRMACION_BOTONNO = "No";

    public static int BBDD_ACTIVOSI = 1;
    public static int BBDD_ACTIVONO = 0;

    public final static ArrayList<String> CENTROS = new ArrayList<String>() {
        {
            add("Sonsoles");
            add("Provincial");
            add("CEP Avila");
            add("CEP Arenas");
        }
    };

    public final static String HTMHEADER = "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
            + "<html xmlns=\"http://www.w3.org/1999/xhtml\">\n"
            + "<head>\n"
            + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\" />\n"
            + "<title>Documento informatitivo</title>\n"
            + "</head>\n"
            + "\n"
            + "<body>";

    public final static String HTMLFIN = "</body>\n"
            + "</html>";
}
