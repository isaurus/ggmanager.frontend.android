package com.isaac.ggmanager.ui.home.achievement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.isaac.ggmanager.R;

/**
 * Fragmento que representa la pantalla o sección de logros (achievements) del usuario.
 * Muestra la interfaz definida en el layout {@code fragment_achievement.xml}.
 */
public class AchievementFragment extends Fragment {

    /**
     * Infla y devuelve la vista asociada a este fragmento.
     *
     * @param inflater           objeto para inflar el layout XML
     * @param container          contenedor padre al que se añadirá la vista (puede ser null)
     * @param savedInstanceState estado previo guardado del fragmento (puede ser null)
     * @return la vista raíz inflada para este fragmento
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate el layout para este fragmento
        return inflater.inflate(R.layout.fragment_achievement, container, false);
    }
}
