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
import com.isaac.ggmanager.databinding.FragmentInfoCreateTeamBinding;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragmento que muestra la información inicial para la creación de un equipo.
 * <p>
 * Esta pantalla ofrece al usuario una introducción o explicación antes
 * de proceder a la creación del equipo propiamente dicha.
 * </p>
 *
 * Utiliza ViewBinding para acceder a las vistas definidas en el layout
 * {@link com.isaac.ggmanager.databinding.FragmentInfoCreateTeamBinding}.
 *
 * La navegación hacia el siguiente fragmento de creación se realiza
 * mediante un botón que lleva al {@link CreateTeamFragment}.
 */
@AndroidEntryPoint
public class InfoCreateTeamFragment extends Fragment {

    private FragmentInfoCreateTeamBinding binding;

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
        binding = FragmentInfoCreateTeamBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado tras la creación de la vista, se utiliza para configurar
     * los listeners de la interfaz de usuario.
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
     * Configura los listeners de los elementos de la interfaz, principalmente
     * el botón para navegar hacia el fragmento de creación del equipo.
     */
    private void setUpListeners() {
        binding.btnCreateTeam.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_infoCreateTeamFragment_to_createTeamFragment);
        });
    }
}
