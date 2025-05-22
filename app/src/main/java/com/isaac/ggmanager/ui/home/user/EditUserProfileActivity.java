package com.isaac.ggmanager.ui.home.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.isaac.ggmanager.core.utils.DatePickerUtils;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.TextWatcherUtils;
import com.isaac.ggmanager.databinding.ActivityEditUserProfileBinding;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EditUserProfileActivity extends AppCompatActivity {

    private ActivityEditUserProfileBinding binding;
    private EditUserProfileViewModel editUserProfileViewModel;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityEditUserProfileBinding.inflate(getLayoutInflater());

        InsetsUtils.applySystemWindowInsetsPadding(binding.getRoot());

        setContentView(binding.getRoot());

        editUserProfileViewModel = new ViewModelProvider(this).get(EditUserProfileViewModel.class);

        calendar = Calendar.getInstance();
        setUpDatePicker();

        setUpListeners();
        observeViewModel();
    }

    private void setUpListeners() {
        binding.btnPickAvatar.setOnClickListener(v -> {
            launchAvatarPickDialog();
        });

        binding.btnSaveProfile.setOnClickListener(v -> {
            String avatar = avatarSelected;
            String name = binding.etName.getText().toString().trim();
            String birthdate = binding.etBirthdate.getText().toString().trim();
            String country = binding.etCountry.getText().toString().trim();

            editUserProfileViewModel.validateEditUserForm(avatar, name, birthdate, country);
        });

        binding.actionBack.setOnClickListener(v -> finish());

        enableButtonOnTextChange();
    }

    private void observeViewModel() {
        editUserProfileViewModel.getEditUserProfileViewState().observe(this, editUserProfileViewState ->  {
            switch (editUserProfileViewState.getStatus()) {
                case VALIDATING:
                    binding.btnSaveProfile.setEnabled(false);

                    // ¿AVATAR?
                    binding.tilName.setError(editUserProfileViewState.isNameValid() ? null : "Nombre no permitido");
                    binding.tilBirthdate.setError(editUserProfileViewState.isBirthdateValid() ? null : "Fecha errónea");
                    binding.tilCountry.setError(editUserProfileViewState.isCountryValid() ? null : "País erróneo");
                case LOADING:
                    binding.btnSaveProfile.setEnabled(false);
                    break;
                case SUCCESS:
                    finish();
                    break;
                case ERROR:
                    Toast.makeText(this, editUserProfileViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    private void launchUserProfileActivity(){
        startActivity(new Intent(this, UserProfileActivity.class));
    }

    private void setUpDatePicker() {
        DatePickerUtils.setupDatePicker(
                this,
                binding.etBirthdate,
                binding.etBirthdate
        );
    }

    private void enableButtonOnTextChange(){
        TextWatcherUtils.enableViewOnTextChange(binding.etName, binding.btnSaveProfile,binding.tilName);
        TextWatcherUtils.enableViewOnTextChange(binding.etBirthdate, binding.btnSaveProfile,binding.tilBirthdate);
        TextWatcherUtils.enableViewOnTextChange(binding.etCountry, binding.btnSaveProfile,binding.tilCountry);
    }

    // TODO AÑADIR DIALOG DE "¿SEGUR QUE QUIERES VOLVER ATRÁS? TUS CAMBIOS NO SE APLICARÁN"

    // TODO ECHAR UN OJO A ESTE MÉTODO PARA CAMBIAR EL AVATAR
    private void launchAvatarPickDialog(){
        String[] avatarNames = {"ic_avatar_avocado", "ic_avatar_batman", "ic_avatar_cactus", "ic_avatar_sheep", "ic_avatar_sloth", "ic_avatar_zombie"};

        new AlertDialog.Builder(this)
                .setTitle("Selecciona avatar")
                .setItems(avatarNames, (dialog, which) -> {
                    String selectedAvatar = avatarNames[which];

                    avatarSelected = selectedAvatar;

                    int resId = getResources().getIdentifier(selectedAvatar,"drawable", getPackageName());
                    binding.imgProfilePic.setImageResource(resId);
                })
                .show();
    }

    public String avatarSelected;
}