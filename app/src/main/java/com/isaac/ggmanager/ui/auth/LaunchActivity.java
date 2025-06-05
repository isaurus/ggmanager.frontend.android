package com.isaac.ggmanager.ui.auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.isaac.ggmanager.databinding.ActivityLaunchBinding;
import com.isaac.ggmanager.ui.home.user.EditUserProfileActivity;
import com.isaac.ggmanager.ui.login.LoginActivity;
import com.isaac.ggmanager.ui.home.HomeActivity;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Activity inicial que verifica el estado de autenticación del usuario.
 *
 * <p>Si el usuario no está autenticado, redirige a la pantalla de login.
 * Si el usuario está autenticado, comprueba si el perfil está completo:
 * - Si el perfil está completo, redirige al HomeActivity.
 * - Si no, redirige a la pantalla para completar el perfil del usuario.</p>
 */
@AndroidEntryPoint
public class LaunchActivity extends AppCompatActivity {

    private ActivityLaunchBinding binding;
    private LaunchViewModel launchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Habilita el modo Edge-to-Edge para la UI
        EdgeToEdge.enable(this);

        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtiene la instancia del ViewModel con inyección de dependencias
        launchViewModel = new ViewModelProvider(this).get(LaunchViewModel.class);

        observarEstadoAutenticacion();
    }

    /**
     * Observa el estado de autenticación y perfil del usuario para determinar la navegación adecuada.
     * <p>
     * - Si no está autenticado, navega a LoginActivity.
     * - Si está autenticado, verifica si el perfil está completo y redirige en consecuencia.
     * - Muestra mensajes de error en caso de fallo.
     * </p>
     */
    private void observarEstadoAutenticacion() {
        if (!launchViewModel.isUserAuthenticated()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            launchViewModel.fetchUserProfile();
            launchViewModel.getLaunchViewState().observe(this, launchViewState -> {
                switch (launchViewState.getStatus()) {
                    case SUCCESS:
                        if (launchViewState.isUserHasProfile()) {
                            startActivity(new Intent(this, HomeActivity.class));
                        } else {
                            startActivity(new Intent(this, EditUserProfileActivity.class));
                        }
                        finish();
                        break;
                    case ERROR:
                        Toast.makeText(this, launchViewState.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        }
    }
}
