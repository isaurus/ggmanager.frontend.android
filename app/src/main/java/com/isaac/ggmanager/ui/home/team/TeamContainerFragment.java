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
import com.isaac.ggmanager.ui.home.team.member.MemberFragment;
import com.isaac.ggmanager.ui.home.team.task.TaskFragment;

/**
 * Fragmento contenedor principal para la gestión y visualización del equipo.
 * <p>
 * Dependiendo del estado del usuario, muestra el contenido del equipo
 * (tareas y miembros) usando un ViewPager2 con pestañas o bien la interfaz
 * para crear un nuevo equipo.
 * </p>
 */
public class TeamContainerFragment extends Fragment {

    private FragmentTeamContainerBinding binding;
    private HomeViewModel homeViewModel;

    /**
     * Infla el layout correspondiente y configura el binding para acceder a las vistas.
     *
     * @param inflater           Inflador de layouts
     * @param container          Contenedor padre de la vista
     * @param savedInstanceState Estado previo guardado
     * @return La raíz de la vista inflada
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentTeamContainerBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    /**
     * Método llamado tras la creación de la vista, donde se inicializa el ViewModel
     * y se comienza la observación del estado.
     *
     * @param view               Vista creada
     * @param savedInstanceState Estado previo guardado
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);

        observeViewModel();
    }

    /**
     * Observa los cambios en el estado del HomeViewModel para actualizar la UI
     * mostrando el contenido del equipo o la pantalla de creación, según corresponda.
     */
    private void observeViewModel() {
        homeViewModel.getHomeViewState().observe(getViewLifecycleOwner(), homeViewState -> {
            switch (homeViewState.getStatus()) {
                case SUCCESS:
                    if (homeViewState.isUserHasTeam()) {
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

    /**
     * Muestra la interfaz con el contenido del equipo,
     * incluyendo un ViewPager2 con pestañas para tareas y miembros.
     * Se configura el adaptador y el mediador de pestañas si aún no está hecho.
     */
    private void showTeamContent() {
        binding.createTeamContainer.setVisibility(View.GONE);
        binding.teamContentContainer.setVisibility(View.VISIBLE);

        if (binding.viewPager.getAdapter() == null) {
            TeamPagerAdapter adapter = new TeamPagerAdapter(this);
            binding.viewPager.setAdapter(adapter);

            new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                    (tab, position) -> {
                        switch (position) {
                            case 0:
                                tab.setText("Tasks");
                                break;
                            case 1:
                                tab.setText("Members");
                                break;
                        }
                    }).attach();
        }
    }

    /**
     * Muestra la interfaz para crear un nuevo equipo,
     * reemplazando el fragmento hijo en el contenedor correspondiente si no está ya presente.
     */
    private void showCreateTeam() {
        binding.teamContentContainer.setVisibility(View.GONE);
        binding.createTeamContainer.setVisibility(View.VISIBLE);

        Fragment existing = getChildFragmentManager().findFragmentById(binding.createTeamContainer.getId());
        if (!(existing instanceof CreateTeamContainerFragment)) {
            getChildFragmentManager().beginTransaction()
                    .replace(binding.createTeamContainer.getId(), new CreateTeamContainerFragment())
                    .commit();
        }
    }

    /**
     * Limpia el binding para evitar fugas de memoria cuando se destruye la vista.
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Adaptador para el ViewPager2 que gestiona los fragmentos de las pestañas "Tasks" y "Members".
     */
    private static class TeamPagerAdapter extends FragmentStateAdapter {

        /**
         * Constructor que recibe el fragmento padre para gestionar el ciclo de vida.
         *
         * @param fragment Fragmento padre
         */
        public TeamPagerAdapter(@NonNull Fragment fragment) {
            super(fragment);
        }

        /**
         * Crea el fragmento correspondiente a la posición de la pestaña.
         *
         * @param position Posición de la pestaña
         * @return Fragmento asociado a la pestaña
         */
        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position) {
                case 0:
                    return new TaskFragment();
                case 1:
                    return new MemberFragment();
                default:
                    throw new IllegalArgumentException("Posición inválida: " + position);
            }
        }

        /**
         * Retorna el número total de pestañas.
         *
         * @return Número de pestañas
         */
        @Override
        public int getItemCount() {
            return 2;
        }
    }
}
