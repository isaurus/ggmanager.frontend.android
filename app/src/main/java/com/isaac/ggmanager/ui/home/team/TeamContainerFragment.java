package com.isaac.ggmanager.ui.home.team;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.google.android.material.tabs.TabLayoutMediator;
import com.isaac.ggmanager.databinding.FragmentTeamContainerBinding;
import com.isaac.ggmanager.ui.home.HomeViewModel;
import com.isaac.ggmanager.ui.home.team.calendar.CalendarFragment;
import com.isaac.ggmanager.ui.home.team.member.MemberFragment;
import com.isaac.ggmanager.ui.home.team.task.TaskFragment;
import com.isaac.ggmanager.ui.home.team.CreateTeamContainerFragment;

public class TeamContainerFragment extends Fragment {

    private FragmentTeamContainerBinding binding;
    private HomeViewModel homeViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTeamContainerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        // Observar si el usuario tiene equipo
        homeViewModel.getHomeViewstate().observe(getViewLifecycleOwner(), homeViewState -> {
            switch (homeViewState.getStatus()){
                case SUCCESS:
                    if (homeViewState.getData()){
                        showTeamContent();
                    } else {
                        showCreateTeam();
                    }
                    break;
                case ERROR:
                    Toast.makeText(getContext(), homeViewState.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void showTeamContent() {
        // Ocultar contenedor creación equipo
        binding.createTeamContainer.setVisibility(View.GONE);
        // Mostrar ViewPager + Tabs
        binding.teamContentContainer.setVisibility(View.VISIBLE);

        // Configurar ViewPager si no está ya configurado
        if (binding.viewPager.getAdapter() == null) {
            TeamPagerAdapter adapter = new TeamPagerAdapter(this);
            binding.viewPager.setAdapter(adapter);

            new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                    (tab, position) -> {
                        switch (position) {
                            case 0:
                                tab.setText("Calendar");
                                break;
                            case 1:
                                tab.setText("Tasks");
                                break;
                            case 2:
                                tab.setText("Members");
                                break;
                        }
                    }).attach();
        }
    }

    private void showCreateTeam() {
        // Ocultar contenido equipo
        binding.teamContentContainer.setVisibility(View.GONE);
        // Mostrar contenedor creación equipo
        binding.createTeamContainer.setVisibility(View.VISIBLE);

        // Insertar el fragmento de creación solo si no está ya cargado
        Fragment existing = getChildFragmentManager().findFragmentById(binding.createTeamContainer.getId());
        if (!(existing instanceof CreateTeamContainerFragment)) {
            getChildFragmentManager().beginTransaction()
                    .replace(binding.createTeamContainer.getId(), new CreateTeamContainerFragment())
                    .commit();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Adaptador para los fragments del equipo
    private static class TeamPagerAdapter extends FragmentStateAdapter {

        public TeamPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new CalendarFragment();
                case 1:
                    return new TaskFragment();
                case 2:
                    return new MemberFragment();
                default:
                    throw new IllegalArgumentException("Invalid position");
            }
        }

        @Override
        public int getItemCount() {
            return 3;
        }
    }
}
