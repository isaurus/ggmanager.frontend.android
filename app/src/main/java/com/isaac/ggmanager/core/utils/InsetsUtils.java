package com.isaac.ggmanager.core.utils;

import android.view.View;

import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

/**
 * Clase de utilidad para gestionar los insets del sistema en una vista,
 * ajustando automáticamente el padding superior e inferior según la barra de estado y la barra de navegación.
 * <p>
 * Es útil para asegurar que el contenido de la interfaz no quede oculto debajo de los elementos del sistema,
 * especialmente en dispositivos con pantallas completas (edge-to-edge).
 * </p>
 *
 * Utiliza la API de {@link WindowInsetsCompat} para garantizar compatibilidad con distintas versiones de Android.
 *
 * @author Isaac
 */
public class InsetsUtils {

    /**
     * Aplica padding dinámico a una vista raíz para evitar que el contenido quede debajo
     * de las barras del sistema (barra de estado y barra de navegación).
     *
     * @param rootView Vista raíz sobre la que se debe aplicar el padding de sistema.
     */
    public static void applySystemWindowInsetsPadding(View rootView) {
        ViewCompat.setOnApplyWindowInsetsListener(rootView, (view, windowInsets) -> {
            Insets insets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
            view.setPadding(
                    view.getPaddingLeft(),  // Mantiene el padding izquierdo actual
                    insets.top,             // Ajusta el padding superior según la barra de estado
                    view.getPaddingRight(), // Mantiene el padding derecho actual
                    insets.bottom           // Ajusta el padding inferior según la barra de navegación
            );
            return WindowInsetsCompat.CONSUMED;
        });
    }
}
