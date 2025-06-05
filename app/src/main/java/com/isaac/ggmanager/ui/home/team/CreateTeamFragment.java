package com.isaac.ggmanager.ui.home.team;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.TextWatcherUtils;
import com.isaac.ggmanager.databinding.FragmentCreateTeamBinding;
import com.isaac.ggmanager.ui.home.HomeActivity;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Fragmento que permite al usuario crear un nuevo equipo.
 * <p>
 * Contiene un formulario con campos para el nombre y descripción del equipo.
 * Valida la entrada del usuario y, tras crear el equipo exitosamente,
 * navega hacia la pantalla principal {@link HomeActivity}.
 * </p>
 */
@AndroidEntryPoint
public class CreateTeamFragment extends Fragment {

    private FragmentCreateTeamBinding binding;
    private CreateTeamViewModel createTeamViewModel;

    /**
     * Infla el layout del fragmento y configura las ventanas para respetar los insets del sistema.
     *
     * @param inflater           Inflador de layouts.
     * @param container          Contenedor padre.
     * @param savedInstanceState Estado guardado previo.
     * @return Vista raíz inflada del fragmento.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTeamBinding.inflate(inflater, container, false);

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        createTeamViewModel = new ViewModelProvider(this).get(CreateTeamViewModel.class);

        return binding.getRoot();
    }

    /**
     * Método llamado una vez creada la vista. Configura los listeners y la observación del ViewModel.
     *
     * @param view               Vista creada.
     * @param savedInstanceState Estado guardado previo.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();
        observeViewModel();
    }

    /**
     * Configura los listeners de los componentes de la interfaz, especialmente el botón de creación.
     * Al pulsar el botón, valida los datos del formulario a través del ViewModel.
     */
    private void setUpListeners() {
        binding.btnCreateTeam.setOnClickListener(v -> {
            String teamName = binding.etTeamName.getText().toString().trim();
            String teamDescription = binding.etTeamDescription.getText().toString().trim();

            createTeamViewModel.validateCreateTeamForm(teamName, teamDescription);
        });

        enableButtonOnTextChange();
    }

    /**
     * Observa el estado del ViewModel para actualizar la UI según la validación del formulario,
     * el estado de carga y resultados de la creación del equipo.
     */
    private void observeViewModel() {
        createTeamViewModel.getCreateTeamViewState().observe(getViewLifecycleOwner(), createTeamViewState -> {

            // Validación de campos
            switch (createTeamViewState.getValidationState()) {
                case VALIDATING:
                    binding.tilTeamName.setError(createTeamViewState.isTeamNameValid() ? null : "Nombre no permitido");
                    binding.tilTeamDescription.setError(createTeamViewState.isTeamDescriptionValid() ? null : "Descripción no permitida");
                    break;
                case IDLE:
                    binding.tilTeamName.setError(null);
                    binding.tilTeamDescription.setError(null);
                    break;
            }

            // Estados de creación
            switch (createTeamViewState.getStatus()) {
                case SUCCESS:
                    Intent intent = new Intent(requireContext(), HomeActivity.class);
                    startActivity(intent);
                    requireActivity().finish();
                    break;
                case LOADING:
                    binding.btnCreateTeam.setEnabled(false);
                    break;
                case ERROR:
                    Toast.makeText(getContext(), createTeamViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    /**
     * Habilita o deshabilita el botón de crear equipo en función de la entrada de texto en
     * los campos de nombre y descripción, utilizando utilidades para observación de texto.
     */
    private void enableButtonOnTextChange() {
        TextWatcherUtils.enableViewOnTextChange(binding.etTeamName, binding.btnCreateTeam, binding.tilTeamName);
        TextWatcherUtils.enableViewOnTextChange(binding.etTeamDescription, binding.btnCreateTeam, binding.tilTeamDescription);
    }
}
