package com.isaac.ggmanager.core.utils;

import android.app.DatePickerDialog;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DatePickerUtils {

    private final static String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";

    public static void setupDatePicker(final Context context, final View triggerView, final TextView outputView) {
        // Crear calendario con la fecha actual
        final Calendar calendar = Calendar.getInstance();

        // Mostrar la fecha actual al iniciar
        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT, Locale.getDefault());
        outputView.setText(sdf.format(calendar.getTime()));

        // MI LISTENER
        triggerView.setOnClickListener(v -> {
            new DatePickerDialog(context,
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


    private SimpleDateFormat formatDate(Date date){
        SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        return dateFormatter;
    }
}