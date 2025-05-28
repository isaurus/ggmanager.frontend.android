package com.isaac.ggmanager.ui.home.team;

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

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CreateTeamFragment extends Fragment {

    private FragmentCreateTeamBinding binding;
    private CreateTeamViewModel createTeamViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTeamBinding.inflate(inflater, container, false);

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        createTeamViewModel = new ViewModelProvider(this).get(CreateTeamViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();
        observeViewModel();
    }

    private void setUpListeners() {
        binding.btnCreateTeam.setOnClickListener(v -> {
            String teamName = binding.etTeamName.getText().toString().trim();
            String teamDescription = binding.etTeamDescription.getText().toString().trim();

            createTeamViewModel.validateCreateTeamForm(teamName, teamDescription);
        });

        enableButtonOnTextChange();
    }

    private void observeViewModel() {
        createTeamViewModel.getCreateTeamViewState().observe(getViewLifecycleOwner(), createTeamViewState -> {
            switch (createTeamViewState.getStatus()){
                case VALIDATING:
                    binding.btnCreateTeam.setEnabled(false);
                    binding.tilTeamName.setError(createTeamViewState.isTeamNameValid() ? null : "Nombre no permitido");
                    binding.tilTeamDescription.setError(createTeamViewState.isTeamDescriptionValid() ? null : "Descripción no permitido");
                    break;
                case SUCCESS:
                    // ¿QUÉ HAGO AQUÍ? ¿LANZAR EL HOME ACTIVITY QUE, AL TENER EQUIPO EL USUARIO, MOSTRARÁ EL CONTENEDOR DE TEAMCONTAINER?
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

    private void enableButtonOnTextChange(){
        TextWatcherUtils.enableViewOnTextChange(binding.etTeamName, binding.btnCreateTeam,binding.tilTeamName);
        TextWatcherUtils.enableViewOnTextChange(binding.etTeamDescription, binding.btnCreateTeam,binding.tilTeamDescription);
    }
}