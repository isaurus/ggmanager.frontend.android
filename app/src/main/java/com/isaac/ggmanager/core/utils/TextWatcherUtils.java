package com.isaac.ggmanager.core.utils;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputLayout;

/**
 * Clase de utilidad para gestionar eventos de cambio de texto en campos de entrada {@link EditText}.
 * <p>
 * Facilita la habilitación dinámica de vistas (por ejemplo, botones) cuando el usuario comienza a escribir,
 * y opcionalmente desactiva mensajes de error en un {@link TextInputLayout}.
 * </p>
 *
 * Es especialmente útil en formularios donde se desea habilitar el botón de envío
 * al introducir datos válidos, o eliminar errores en tiempo real.
 *
 * @author Isaac
 */
public class TextWatcherUtils {

    /**
     * Habilita una vista específica (por ejemplo, un botón) cuando el contenido del {@link EditText} cambia.
     * Además, si se proporciona un {@link TextInputLayout}, desactiva el estado de error al escribir.
     *
     * @param et            Campo de texto sobre el que se detectan los cambios.
     * @param viewToEnable  Vista que se habilitará cuando el usuario introduzca texto (usualmente un botón).
     * @param inputLayout   (Opcional) Layout de entrada que puede estar mostrando un error. Si no es nulo, se desactiva el error al escribir.
     */
    public static void enableViewOnTextChange(EditText et, final View viewToEnable, @Nullable final TextInputLayout inputLayout) {
        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                // No se necesita implementar
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                viewToEnable.setEnabled(true);
                if (inputLayout != null) {
                    inputLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // No se necesita implementar
            }
        });
    }
}
