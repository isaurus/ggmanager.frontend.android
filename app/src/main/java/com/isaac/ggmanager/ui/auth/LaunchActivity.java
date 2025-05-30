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
 * Esta clase lanza al usuario al corazón de la aplicación si está logeado y, en caso contrario,
 * lo direcciona a la fase de registro/login.
 */
@AndroidEntryPoint
public class LaunchActivity extends AppCompatActivity {

    private ActivityLaunchBinding binding;
    private LaunchViewModel launchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        // Inflamos el layout con el binding y lo seteamos
        binding = ActivityLaunchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Iniciamos el AuthViewModel
        launchViewModel = new ViewModelProvider(this).get(LaunchViewModel.class);

        observeViewModel();
    }

    private void observeViewModel(){

        if (!launchViewModel.isUserAuthenticated()){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        } else {
            launchViewModel.isUserPersisted();

            launchViewModel.getLaunchViewState().observe(this, launchViewState -> {
                switch (launchViewState.getStatus()){
                    case SUCCESS:
                        if (launchViewState.getData() != null){
                            startActivity(new Intent(this, HomeActivity.class));
                        } else {
                            startActivity(new Intent(this, EditUserProfileActivity.class));
                        }
                        break;
                    case ERROR:
                        Toast.makeText(this, launchViewState.getMessage(), Toast.LENGTH_SHORT).show();
                        break;
                }
            });
        }
    }
}