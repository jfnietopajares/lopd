package com.hnss.utilidades;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.logging.log4j.LogManager;

import com.vaadin.server.Page;
import com.vaadin.server.WebBrowser;

/**
 * The Class Utilidades.
 */
public class Utilidades {

    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Utilidades.class);

    /**
     * Validar NIF.
     *
     * @param nif the nif
     * @return true, if successful
     */
    public static boolean validarNIF(String nif) {

        boolean correcto = false;

        Pattern pattern = Pattern.compile("(\\d{1,8})([TRWAGMYFPDXBNJZSQVHLCKEtrwagmyfpdxbnjzsqvhlcke])");

        Matcher matcher = pattern.matcher(nif);

        if (matcher.matches()) {

            String letra = matcher.group(2);

            String letras = "TRWAGMYFPDXBNJZSQVHLCKE";

            int index = Integer.parseInt(matcher.group(1));

            index = index % 23;

            String reference = letras.substring(index, index + 1);

            if (reference.equalsIgnoreCase(letra)) {

                correcto = true;

            } else {

                correcto = false;

            }

        } else {

            correcto = false;

        }

        return correcto;

    }

    /**
     * Validar telefono fijo.
     *
     * @param telefono the telefono
     * @return true, if successful
     */
    public static boolean validarTelefonoFijo(String telefono) {
        if (telefono.matches("9[0-9]{1,2} [0-9]{7}/")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean validarTele(String telefono) {
        if (telefono.matches("(?:\\d{3}|\\(\\d{3}\\))([-\\/\\.])\\d{3}\\1\\d{4}")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Validar telefono.
     *
     * @param cadena the cadena
     * @return true, if successful
     */
    public static boolean validarTelefono(String cadena) {
        if (cadena.matches("[0-9]{9}")) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Convertir.
     *
     * @param numero the numero
     * @return the int
     */
    public static int convertir(String numero) {
        numero = numero.replaceAll("[^0-9]", "");
        if (numero.equals("")) {
            numero = "0";
        }

        return Integer.parseInt(numero);
    }

    /*
	 * public static String getTraceException(Exception ex) { String mensaje = null;
	 * for (StackTraceElement elemnt : ex.getStackTrace()) { mensaje +=
	 * elemnt.toString() + "\n"; } return mensaje; }
     */
    /**
     * Validar email.
     *
     * @param email the email
     * @return true, if successful
     */
    public static boolean validarEmail(String email) {

        if (email == null) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+(?:\\.[a-zA-Z0-9_!#$%&'*+/=?`{|}~^-]+)*@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

    /**
     * Gets the sexo valor.
     *
     * @param sexonombre the sexonombre
     * @return the sexo valor
     */
    public static Integer getSexoValor(String sexonombre) {
        Integer sexovalor = new Integer(0);
        switch (sexonombre) {
            case "Hombre":
                sexovalor = 1;
                break;
            case "Mujer":
                sexovalor = 2;
                break;
        }
        return sexovalor;
    }

    /**
     * Gets the fech formatoaddmmyyyy.
     *
     * @param fecha the fecha
     * @param separador the separador
     * @return the fech formatoaddmmyyyy
     */
    public static String getFechFormatoaddmmyyyy(Long fecha, String separador) {
        String unaFecha = "";
        String cadena = Long.toString(fecha);

        unaFecha = cadena.substring(6, 8).concat(separador).concat(cadena.substring(4, 6)).concat(separador)
                .concat(cadena.substring(0, 4));

        return unaFecha;
    }

    public static Long getFechaYYYYMMDD(LocalDateTime fechaHora) {
        DateTimeFormatter fechaYYYYMMDD = DateTimeFormatter.ofPattern("YYYYMMdd");
        Long feLong = Long.parseLong(fechaYYYYMMDD.format(fechaHora));
        return feLong;
    }

    public static Integer getHoraHHMM(LocalDateTime fechaHora) {
        DateTimeFormatter horaHHMM = DateTimeFormatter.ofPattern("HHmm");
        Integer hoInteger = Integer.parseInt(horaHHMM.format(fechaHora));
        return hoInteger;
    }

    public static LocalDateTime getFechaHora(Long fecha, Integer hora) {
        if (fecha == null || hora == null) {
            return null;
        }

        int y, m, d, h = 0, mi = 0;
        String fechaString = Long.toString(fecha);
        String horaString = Long.toString(hora);
        LocalDateTime fechaHoraDateTime = null;
        if (fechaString.length() != 8) {
            return null;
        }
        try {
            y = Integer.parseInt(fechaString.substring(0, 4));
            m = Integer.parseInt(fechaString.substring(4, 6));
            d = Integer.parseInt(fechaString.substring(6, 8));
            if (horaString.length() == 4) {
                h = Integer.parseInt(horaString.substring(0, 2));
                mi = Integer.parseInt(horaString.substring(2, 4));
            } else if (horaString.length() == 3) {
                h = Integer.parseInt(horaString.substring(0, 1));
                mi = Integer.parseInt(horaString.substring(1, 3));
            } else if (horaString.length() <= 2) {
                h = 0;
                mi = Integer.parseInt(horaString);
            }
            fechaHoraDateTime = LocalDateTime.of(y, m, d, h, mi);
        } catch (Exception e) {
            logger.error("Error convirtiendo en fecha hora " + fecha + " " + hora);
        }
        return fechaHoraDateTime;
    }

    /**
     * Gets the fech formatoayyyymmdd.
     *
     * @param fecha the fecha
     * @param separador the separador
     * @return the fech formatoayyyymmdd
     */
    public static String getFechFormatoayyyymmdd(long fecha, String separador) {
        String unaFecha = "";
        String cadena = Long.toString(fecha);

        unaFecha = cadena.substring(0, 4).concat(separador).concat(cadena.substring(4, 6)).concat(separador)
                .concat(cadena.substring(6, 8));

        return unaFecha;
    }

    /**
     * Gets the fecha numeroyyymmdd defecha.
     *
     * @param fecha the fecha
     * @return the fecha numeroyyymmdd defecha
     */
    public static Long getFechayyymmdd(LocalDate fecha) {
        long unaFecha = 0;
        int dd, mm, yyyy;
        if (fecha != null) {
            dd = fecha.getDayOfMonth();
            // if (dd<10) dd=dd*10;
            mm = fecha.getMonthValue();
            // if (mm<10) mm=mm*10;
            yyyy = fecha.getYear();

            unaFecha = yyyy * 10000 + mm * 100 + dd;
        }

        return unaFecha;

    }

    /**
     * Gets the fecha numeroyyymmdd.
     *
     * @param fecha the fecha
     * @return the fecha numeroyyymmdd
     */
    public static Long getFechaNumeroyyymmdd(String fecha) {
        long unaFecha = 0;
        String dd, mm, yyyy;
        dd = fecha.substring(0, 2);
        mm = fecha.substring(3, 5);
        yyyy = fecha.substring(6, 10);

        unaFecha = Integer.parseInt(yyyy) * 10000 + Integer.parseInt(mm) * 100 + Integer.parseInt(dd);

        return unaFecha;
    }

    /**
     * Gets the date formatdd mm yyyy.
     *
     * @return the date formatdd mm yyyy
     */
    public static DateTimeFormatter getDateFormatdd_mm_yyyy() {
        return DateTimeFormatter.ofPattern("dd/mm/yyyy");
    }

    /**
     * Gets the fecha local date.
     *
     * @param fecha the fecha
     * @return the fecha local date
     */
    public static LocalDateTime getFechaLocalDateTime(Long fecha) {
        LocalDateTime date = null;
        if (fecha != null && fecha != 0) {

            String cadena = Long.toString(fecha);
            /* Para 14 caracteres
                20200417101344
                yyyymmddmmssdd
                para 12 caracteres
                202004151415
                yyyymmddhhss
             */
            if (cadena.length() == 14 || cadena.length()==12) {
                int year = Integer.parseInt(cadena.substring(0, 4));
                int month = Integer.parseInt(cadena.substring(4, 6));
                int day = Integer.parseInt(cadena.substring(6, 8));
                int hour = Integer.parseInt(cadena.substring(8, 10));
                int min = Integer.parseInt(cadena.substring(10, 12));
                date = LocalDateTime.of(year, month, day,hour,min);
            } else if (cadena.length()==8){
                /*
                20110412
                yyyymmdd
                */
                int year = Integer.parseInt(cadena.substring(0, 4));
                int month = Integer.parseInt(cadena.substring(4, 6));
                int day = Integer.parseInt(cadena.substring(6, 8));
                date = LocalDateTime.of(year, month, day,0,0);
            }

        }
        return date;
    }

      public static LocalDate getFechaLocalDate(Long fecha) {
        LocalDate date = null;
        if (fecha != null && fecha != 0) {

            String cadena = Long.toString(fecha);
           if (cadena.length()==8){
                /*
                20110412
                yyyymmdd
                */
                int year = Integer.parseInt(cadena.substring(0, 4));
                int month = Integer.parseInt(cadena.substring(4, 6));
                int day = Integer.parseInt(cadena.substring(6, 8));
                date = LocalDate.of(year, month,day);
            }

        }
        return date;
    }
    public static LocalDate getFechaLocalDate(String cadena) {
        LocalDate date = null;
        if (cadena != null) {
            if (cadena.length() == 8) {
                int year = Integer.parseInt(cadena.substring(0, 4));
                int month = Integer.parseInt(cadena.substring(4, 6));
                int day = Integer.parseInt(cadena.substring(6, 8));
                date = LocalDate.of(year, month, day);
                // return date;
            }
        }
        return date;
    }

    /**
     * Gets the hora numero acual.
     *
     * @return the hora numero acual
     */
    public static Long getHoraNumeroAcual() {

        LocalTime time = LocalTime.now();
        int hh = time.getHour();
        int mm = time.getMinute();

        return new Long(hh * 100 + mm);

    }

    public static int getHoraNumeroAcualInteger() {

        LocalTime time = LocalTime.now();
        int hh = time.getHour();
        int mm = time.getMinute();

        return new Integer(hh * 100 + mm);

    }

    public static String getHoraAcualString() {

        LocalTime time = LocalTime.now();

        int hh = time.getHour();
        int mm = time.getMinute();
        String hhString = Integer.toString(hh);
        String mmString = Integer.toString(mm);
        if (hh < 10) {
            hhString = "0" + hhString;
        }
        if (mm < 10) {
            mmString = "0" + mmString;
        }
        return hhString + ":" + mmString;

    }

    public static String getHHAcualString() {

        LocalTime time = LocalTime.now();

        int hh = time.getHour();

        String hhString = Integer.toString(hh);

        if (hh < 10) {
            hhString = "0" + hhString;
        }

        return hhString;

    }

    public static String getMMAcualString() {

        LocalTime time = LocalTime.now();

        int mm = time.getMinute();

        String mmString = Integer.toString(mm);
        if (mm < 10) {
            mmString = "0" + mmString;
        }
        return mmString;
    }

    /**
     * Gets the hora.
     *
     * @param unahora the unahora
     * @return the hora
     */
    public static int getHora(String unahora) {
        int valor;
        String cadena1;
        cadena1 = unahora.substring(0, 2);
        valor = Integer.parseInt(cadena1);
        return valor;
    }

    public static int getHoraint(String hora) {
        int valor = 0;
        String cadena1;
        if (hora.length() > 2) {
            cadena1 = hora.substring(0, 2);
            if (Utilidades.isNumeric(cadena1) == true) {
                valor = Integer.parseInt(cadena1);
            }
        }
        return valor;
    }

    /**
     * Gets the minuto.
     *
     * @param unahora the unahora
     * @return the minuo
     */
    public static int getMinutoint(String hora) {
        int valor = 0;
        String cadena1;
        if (hora.length() == 5) {
            cadena1 = hora.substring(3, 5);
            if (Utilidades.isNumeric(cadena1)) {
                valor = Integer.parseInt(cadena1);
            }
        }
        return valor;
    }

    public static int getHoraHHMM(String hora) {
        return getHoraint(hora) * 100 + getMinutoint(hora);
    }

    public static String getHoraHH_MM(Long hora) {
        String hhmmString = null;
        String hh = null;
        String mm = null;
        hhmmString = Long.toString(hora);
        if (hhmmString.length() == 4) {
            hh = hhmmString.substring(0, 2);
            mm = hhmmString.substring(2, 4);
        } else if (hhmmString.length() == 3) {
            hh = "0" + hhmmString.substring(0, 1);
            mm = hhmmString.substring(1, 3);
        } else if (hhmmString.length() == 2) {
            hh = "00";
            mm = hhmmString.substring(1, 1);
        } else if (hhmmString.length() == 1) {
            hh = "00";
            mm = "0" + hhmmString;
        }
        return hh + ":" + mm;
    }

    /**
     * Checks if is numeric.
     *
     * @param cadena the cadena
     * @return true, if is numeric
     */
    public static boolean isNumeric(String cadena) {
        try {
            Integer.parseInt(cadena);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Checks if is numero.
     *
     * @param cadena the cadena
     * @return true, if is numero
     */
    public static boolean isNumero(String cadena) {
        if (cadena == null || cadena.isEmpty()) {
            return false;
        } else {
            int i = 0;
            for (i = 0; i < cadena.length(); i++) {
                if (!Character.isDigit(cadena.charAt(i))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Gets the alto grid.
     *
     * @param nfilas the nfilas
     * @return the alto grid
     */
    public static String getAltoGrid(int nfilas) {
        int alto = nfilas * 72;
        String altopx;
        altopx = Integer.toString(alto);
        altopx = altopx.concat("px");
        return altopx;
    }

    /**
     * Gets the informacion cliente.
     *
     * @return the informacion cliente
     */
    public static String getInformacionCliente() {
        final WebBrowser navegador = Page.getCurrent().getWebBrowser();

        String cadenaString = "<hr> <b>Información del puesto de trabajo</b><br><b>Ip:</b>" + navegador.getAddress()
                + "<br>";

        cadenaString = cadenaString.concat("<b>Navegador:</b>" + navegador.getBrowserApplication() + "<br>");
        cadenaString = cadenaString.concat("<b>Versión:</b>" + navegador.getBrowserVersion() + "<br>");
        cadenaString = cadenaString
                .concat("<b>Ventana:</b>" + navegador.getScreenHeight() + "x" + navegador.getScreenWidth() + "<br>");

        cadenaString = cadenaString.concat("<b>Locale:</b>" + navegador.getLocale().getDisplayName() + "<br>");
        cadenaString = cadenaString.concat("<b>Fecha:</b>" + navegador.getCurrentDate() + "<br>");

        return cadenaString;
    }

}
