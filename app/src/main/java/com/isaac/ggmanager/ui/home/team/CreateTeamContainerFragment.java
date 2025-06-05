package com.isaac.ggmanager.ui.home.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.FragmentCreateTeamContainerBinding;

/**
 * Fragmento contenedor que gestiona el flujo de fragments internos cuando
 * el usuario no pertenece a ningún equipo.
 * <p>
 * Este fragmento sirve como contenedor principal para la creación o unión a equipos,
 * facilitando la navegación y la gestión visual en esos casos.
 * </p>
 */
public class CreateTeamContainerFragment extends Fragment {

    private FragmentCreateTeamContainerBinding binding;

    /**
     * Infla la vista del fragmento y aplica los márgenes necesarios para respetar los
     * insets del sistema (como la barra de estado o navegación).
     *
     * @param inflater           Inflador de layouts.
     * @param container          Contenedor padre del fragmento.
     * @param savedInstanceState Estado previo guardado.
     * @return La vista raíz del fragmento.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTeamContainerBinding.inflate(inflater, container, false);

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        return binding.getRoot();
    }

    /**
     * Limpia la referencia al binding para evitar fugas de memoria cuando la vista se destruye.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
