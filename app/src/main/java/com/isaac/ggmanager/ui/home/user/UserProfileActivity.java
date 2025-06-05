package com.isaac.ggmanager.ui.home.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.isaac.ggmanager.databinding.ActivityUserProfileBinding;
import com.isaac.ggmanager.ui.home.HomeActivity;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.UIUserUtils;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Actividad que muestra el perfil del usuario actual.
 * <p>
 * Esta actividad observa el estado del perfil del usuario a través de un ViewModel,
 * actualizando la interfaz de usuario con los datos recibidos.
 * Permite navegar hacia la edición del perfil.
 * </p>
 */
@AndroidEntryPoint
public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    private UserProfileViewModel userProfileViewModel;

    /**
     * Método llamado al crear la actividad.
     * Configura el layout, el sistema EdgeToEdge, aplica los márgenes de sistema
     * y comienza la observación del ViewModel para cargar el perfil del usuario.
     *
     * @param savedInstanceState Estado guardado previamente (puede ser null).
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityUserProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        userProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);

        setUpListeners();
        observeViewModel();
        userProfileViewModel.getUserProfile();
    }

    /**
     * Configura los listeners de la interfaz.
     * - Botón de volver atrás finaliza la actividad.
     * - Botón para editar el perfil finaliza la actividad y lanza la actividad de edición.
     */
    private void setUpListeners() {
        binding.actionBack.setOnClickListener(v -> finish());

        binding.btnEditProfile.setOnClickListener(v -> {
            finish();
            launchEditProfileActivity();
        });
    }

    /**
     * Observa el estado del ViewModel para actualizar la UI según el estado del perfil de usuario.
     * - En éxito, rellena la UI con los datos del usuario.
     * - En carga, podría mostrarse un indicador de progreso.
     * - En error, muestra un Toast con el mensaje de error.
     */
    private void observeViewModel() {
        userProfileViewModel.getUserProfileViewState().observe(this, userProfileViewState -> {
            switch (userProfileViewState.getStatus()) {
                case SUCCESS:
                    UIUserUtils.fillUserProfileUI(binding, userProfileViewState.getUser(), this);
                    break;
                case LOADING:
                    // Aquí se puede mostrar un ProgressBar si se desea
                    break;
                case ERROR:
                    Toast.makeText(this, userProfileViewState.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    /**
     * Lanza la actividad para editar el perfil de usuario.
     */
    private void launchEditProfileActivity(){
        startActivity(new Intent(this, EditUserProfileActivity.class));
    }
}
