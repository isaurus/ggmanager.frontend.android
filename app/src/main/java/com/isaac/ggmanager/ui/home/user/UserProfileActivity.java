package com.isaac.ggmanager.ui.home.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.isaac.ggmanager.R;
import com.isaac.ggmanager.core.utils.DateFormatUtils;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.UIUserUtils;
import com.isaac.ggmanager.databinding.ActivityUserProfileBinding;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.ui.home.HomeActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserProfileActivity extends AppCompatActivity {

    private ActivityUserProfileBinding binding;
    private UserProfileViewModel userProfileViewModel;

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
    }

    private void setUpListeners() {
        binding.actionBack.setOnClickListener(v -> finish());

        binding.btnEditProfile.setOnClickListener(v -> {
            finish();
            launchEditProfileActivity();
        });
    }

    private void observeViewModel() {
        userProfileViewModel.getUserProfile();

        userProfileViewModel.getUserProfileViewState().observe(this, userProfileViewState -> {
            switch (userProfileViewState.getStatus()) {
                case SUCCESS:
                    UIUserUtils.fillUserProfileUI(binding, userProfileViewState.getData(), this);
                    break;
                case LOADING:
                    // PROGRESS BAR
                    break;
                case ERROR:
                    Toast.makeText(this, userProfileViewState.getMessage(), Toast.LENGTH_SHORT).show();
                    break;
            }
        });
    }

    private void launchEditProfileActivity(){
        //Intent intent = new Intent(this, EditUserProfileActivity.class);
        //intent.putExtra("user", userModel);
        //startActivity(intent);
        startActivity(new Intent(this, EditUserProfileActivity.class));

    }

    private void launchHomeActivity(){
        startActivity(new Intent(this, HomeActivity.class));
    }
}