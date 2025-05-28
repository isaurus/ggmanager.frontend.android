package com.isaac.ggmanager.ui.home.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.FragmentCreateTeamContainerBinding;

/**
 * Contenedor para gestionar el flujo de los fragments contenidos en caso de que el usuario no
 * pertenezca a ningún equipo.
 */
public class CreateTeamContainerFragment extends Fragment {

    private FragmentCreateTeamContainerBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCreateTeamContainerBinding.inflate(inflater, container, false);

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
