package com.isaac.ggmanager.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.isaac.ggmanager.R;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.databinding.ActivityHomeBinding;
import com.isaac.ggmanager.ui.home.user.UserProfileActivity;
import com.isaac.ggmanager.ui.login.LoginActivity;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Actividad principal de la aplicación que actúa como el contenedor del flujo principal
 * después de la autenticación del usuario.
 * <p>
 * Esta actividad configura la navegación mediante un NavController con un NavHostFragment,
 * gestiona la barra inferior de navegación, observa los estados de la vista desde el ViewModel,
 * y provee accesos a funcionalidades como ver el perfil de usuario o cerrar sesión.
 * </p>
 */
@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavController navController;
    private HomeViewModel homeViewModel;

    /**
     * Método llamado al crear la actividad.
     * Inicializa el layout, la navegación, la configuración de UI y observa el estado del ViewModel.
     *
     * @param savedInstanceState estado previamente guardado de la actividad (si existe).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Aplica los márgenes para evitar solapamiento con las zonas del sistema
        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        // Inicializa el ViewModel para esta actividad
        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Obtiene el NavHostFragment para controlar la navegación dentro de esta actividad
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            // Configura la barra inferior para que funcione con el NavController
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        }

        observeViewModel();
        setUpToolbar();

        // Solicita cargar el equipo del usuario autenticado
        homeViewModel.getUserTeam();
    }

    /**
     * Observa los cambios en el estado de la vista (ViewState) desde el ViewModel
     * y reacciona a ellos actualizando la UI o mostrando mensajes.
     */
    private void observeViewModel() {
        homeViewModel.getHomeViewState().observe(this, homeViewState -> {
            switch (homeViewState.getStatus()){
                case SUCCESS:
                    // Si la navegación actual no está en el fragmento de equipo, navega hacia él
                    if (navController != null && navController.getCurrentDestination() != null
                            && navController.getCurrentDestination().getId() != R.id.teamContainerFragment) {
                        navController.navigate(R.id.teamContainerFragment);
                    }
                    break;
                case ERROR:
                    // Muestra un mensaje en caso de error
                    Toast.makeText(this, homeViewState.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    /**
     * Configura la toolbar (barra superior) con los listeners para las acciones del perfil de usuario y cierre de sesión.
     */
    private void setUpToolbar() {
        binding.actionUserProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, UserProfileActivity.class));
        });

        binding.actionLogout.setOnClickListener(v -> signOut());
    }

    /**
     * Ejecuta el cierre de sesión del usuario, lanza la actividad de login y finaliza esta actividad.
     */
    private void signOut() {
        homeViewModel.signOut();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    /**
     * Limpia las referencias al binding para evitar fugas de memoria.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    /**
     * Maneja la navegación hacia atrás en la pila de navegación.
     *
     * @return true si se pudo navegar hacia arriba en el stack, false si no.
     */
    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
