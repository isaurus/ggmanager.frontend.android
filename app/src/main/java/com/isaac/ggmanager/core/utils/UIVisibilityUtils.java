package com.isaac.ggmanager.core.utils;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.navigation.NavController;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Clase de utilidad para gestionar dinámicamente la visibilidad de elementos de la interfaz
 * en función del fragmento activo dentro de la navegación con {@link NavController}.
 * <p>
 * Permite ocultar o mostrar automáticamente vistas comunes (como barras de navegación, botones, etc.)
 * cuando se navega a determinados fragmentos, evitando lógica repetida en cada uno de ellos.
 * </p>
 *
 * Ejemplo de uso:
 * <pre>
 *     UIVisibilityUtils.setupVisibilityListener(
 *         navController,
 *         new Integer[]{R.id.loginFragment, R.id.registerFragment},
 *         bottomNavView, toolbar
 *     );
 * </pre>
 *
 * Esto ocultará la barra de navegación y la toolbar en los fragments de login y registro.
 *
 * @author Isaac
 */
public class UIVisibilityUtils {

    /**
     * Asocia un listener al {@link NavController} que controla la visibilidad de las vistas pasadas,
     * dependiendo del fragmento actual.
     *
     * @param navController       Controlador de navegación que gestiona el flujo de pantallas.
     * @param hiddenInFragments   Array con los IDs de los fragments donde se deben ocultar las vistas.
     * @param views               Vistas que serán ocultadas o mostradas en función del destino actual.
     */
    public static void setupVisibilityListener(NavController navController,
                                               @IdRes Integer[] hiddenInFragments,
                                               View... views) {
        final Set<Integer> fragmentIdSet = new HashSet<>(Arrays.asList(hiddenInFragments));

        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int destinationId = destination.getId();
            boolean shouldHide = fragmentIdSet.contains(destinationId);

            for (View view : views) {
                view.setVisibility(shouldHide ? View.GONE : View.VISIBLE);
            }
        });
    }
}
