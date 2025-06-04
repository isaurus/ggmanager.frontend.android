package com.isaac.ggmanager.core.utils;

import android.content.Context;

import com.isaac.ggmanager.R;

import java.util.Arrays;
import java.util.List;

/**
 * Clase de utilidad para proporcionar la lista de países disponibles en la aplicación.
 * <p>
 * Utiliza los recursos definidos en el archivo XML de strings (countries_array) para obtener
 * los nombres de los países y convertirlos en una lista.
 * </p>
 *
 * Esta clase es útil para llenar elementos de la interfaz como {@code Spinner} o listas desplegables
 * cuando se necesita seleccionar un país.
 *
 * @author Isaac
 */
public class CountryProviderUtils {

    /**
     * Obtiene una lista de países a partir del recurso definido en {@code R.array.countries_array}.
     *
     * @param context El contexto de la aplicación necesario para acceder a los recursos.
     * @return Una lista de nombres de países definidos en el recurso XML.
     */
    public static List<String> getCountries(Context context) {
        return Arrays.asList(context.getResources().getStringArray(R.array.countries_array));
    }
}
