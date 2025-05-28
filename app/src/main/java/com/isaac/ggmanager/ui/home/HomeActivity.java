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

@AndroidEntryPoint
public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;
    private NavController navController;
    private HomeViewModel homeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);

        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
            NavigationUI.setupWithNavController(binding.bottomNavigation, navController);
        }

        observeViewModel();
        setUpToolbar();
    }

    private void observeViewModel() {
        homeViewModel.checkUserHasTeam();

        homeViewModel.getHomeViewstate().observe(this, homeViewState -> {
            switch (homeViewState.getStatus()) {
                case SUCCESS:
                    if (navController != null && navController.getCurrentDestination() != null
                            && navController.getCurrentDestination().getId() != R.id.teamContainerFragment) {
                        navController.navigate(R.id.teamContainerFragment);
                    }
                    break;
                case ERROR:
                    Toast.makeText(this, homeViewState.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void setUpToolbar() {
        binding.actionUserProfile.setOnClickListener(v -> {
            startActivity(new Intent(this, UserProfileActivity.class));
        });

        binding.actionLogout.setOnClickListener(v -> signOut());
    }

    private void signOut() {
        homeViewModel.signOut();

        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp() || super.onSupportNavigateUp();
    }
}
