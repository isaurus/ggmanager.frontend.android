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

@AndroidEntryPoint
public class InitCreateTeamFragment extends Fragment {

    private FragmentInitCreateTeamBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentInitCreateTeamBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setUpListeners();
    }

    private void setUpListeners(){
        binding.btnCreateTeam.setOnClickListener(v -> {
            Navigation.findNavController(v)
                    .navigate(R.id.action_initCreateTeamFragment_to_infoCreateTeamFragment);
        });
    }
}