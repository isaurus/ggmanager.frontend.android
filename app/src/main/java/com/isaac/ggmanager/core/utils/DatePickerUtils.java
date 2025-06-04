package com.isaac.ggmanager.core.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Clase de utilidad para configurar y mostrar un selector de fecha personalizado (DatePickerDialog).
 * <p>
 * Permite al usuario seleccionar una fecha desde una interfaz gráfica y muestra la fecha seleccionada
 * en un {@link TextView} con formato {@code dd/MM/yyyy}. Al inicializar, también muestra la fecha actual
 * por defecto.
 * </p>
 *
 * Uso típico en formularios donde se requiere entrada de fechas como fecha de nacimiento, eventos, etc.
 *
 * @author Isaac
 */
public class DatePickerUtils {

    /** Formato de fecha usado por defecto: día/mes/año. */
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    /**
     * Configura un {@link DatePickerDialog} que se muestra al hacer clic en una vista específica,
     * y establece la fecha seleccionada en el {@link TextView} de salida.
     *
     * @param context     Contexto necesario para mostrar el diálogo.
     * @param triggerView Vista que actuará como disparador del diálogo al hacer clic.
     * @param outputView  TextView donde se mostrará la fecha seleccionada.
     */
    public static void setupDatePicker(final Context context, final View triggerView, final TextView outputView) {
        final Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());

        // Establecer la fecha actual como predeterminada en el TextView
        outputView.setText(sdf.format(calendar.getTime()));

        // Mostrar el DatePickerDialog al hacer clic en la vista
        triggerView.setOnClickListener(v -> {
            new DatePickerDialog(
                    context,
                    (view, year, month, dayOfMonth) -> {
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        outputView.setText(sdf.format(calendar.getTime()));
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
    }

    /**
     * Devuelve un formateador de fecha con el formato {@code dd/MM/yyyy}.
     * <p>
     * Este método no se utiliza en la clase actualmente, pero puede ser útil para reutilizar
     * el formato de fecha en otros contextos.
     * </p>
     *
     * @param date Fecha que se desea formatear (parámetro no usado).
     * @return Instancia de {@link SimpleDateFormat} con el formato predeterminado.
     */
    private SimpleDateFormat formatDate(Date date) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
    }
}
