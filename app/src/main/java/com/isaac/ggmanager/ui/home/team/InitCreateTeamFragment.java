package com.isaac.ggmanager.ui.home.team;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.isaac.ggmanager.R;
import com.isaac.ggmanager.databinding.FragmentInitCreateTeamBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragmento inicial del flujo para la creación de un equipo.
 * <p>
 * Presenta una pantalla con un botón que inicia el proceso de creación
 * de un equipo, navegando al fragmento que muestra información previa
 * antes de la creación definitiva.
 * </p>
 *
 * Utiliza ViewBinding para gestionar las vistas definidas en el layout
 * {@link com.isaac.ggmanager.databinding.FragmentInitCreateTeamBinding}.
 */
@AndroidEntryPoint
public class InitCreateTeamFragment extends Fragment {

    private FragmentInitCreateTeamBinding binding;

    /**
     * Infla el layout correspondiente y configura el binding para acceder a las vistas.
     *
     * @param inflater           Inflador de layouts
     * @param container          Contenedor padre de la vista
     * @param savedInstanceState Estado previo guardado
     * @return La raíz de la vista inflada
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInitCreateTeamBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado tras la creación de la vista, donde se configuran
     * los listeners para los elementos interactivos.
     *
     * @param view               Vista creada
     * @param savedInstanceState Estado previo guardado
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();
    }

    /**
     * Configura los listeners de los elementos de la interfaz,
     * especialmente el botón que navega hacia el fragmento
     * {@link InfoCreateTeamFragment}.
     */
    private void setUpListeners() {
        binding.btnCreateTeam.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_initCreateTeamFragment_to_infoCreateTeamFragment);
        });
    }
}
