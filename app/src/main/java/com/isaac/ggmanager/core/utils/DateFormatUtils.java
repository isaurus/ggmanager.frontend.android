package com.isaac.ggmanager.core.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateFormatUtils {

    // Puedes cambiar el formato según lo necesites
    private static final String DATE_FORMAT = "dd/MM/yyyy";

    public static String dateToString(Date date) {
        if (date == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
        return sdf.format(date);
    }

}
