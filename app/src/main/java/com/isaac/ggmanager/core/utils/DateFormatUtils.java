package com.isaac.ggmanager.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad para formatear fechas en una representación de cadena legible.
 * <p>
 * Aplica el formato {@code dd/MM/yyyy} para representar fechas de forma consistente en la interfaz
 * de usuario o para almacenamiento textual. Está pensada para su uso en componentes como TextView,
 * formularios, etc.
 * </p>
 *
 * Ejemplo de formato de salida: {@code "04/06/2025"}.
 *
 * @author Isaac
 */
public class DateFormatUtils {

    /** Formato de fecha usado para la conversión: día/mes/año. */
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Convierte un objeto {@link Date} en una cadena con el formato definido.
     *
     * @param date Objeto de tipo {@link Date} que se desea convertir.
     * @return Cadena que representa la fecha en formato {@code dd/MM/yyyy}.
     *         Si el parámetro es {@code null}, se devuelve una cadena vacía.
     */
    public static String dateToString(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }
}
