package com.isaac.ggmanager.ui.home.user;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.isaac.ggmanager.R;
import com.isaac.ggmanager.core.utils.CountryProviderUtils;
import com.isaac.ggmanager.core.utils.DatePickerUtils;
import com.isaac.ggmanager.core.utils.InsetsUtils;
import com.isaac.ggmanager.core.utils.TextWatcherUtils;
import com.isaac.ggmanager.databinding.ActivityEditUserProfileBinding;
import com.isaac.ggmanager.domain.model.Avatar;
import com.isaac.ggmanager.ui.home.HomeActivity;

import java.util.Calendar;

import dagger.hilt.android.AndroidEntryPoint;

/**
 * Actividad que permite al usuario editar su perfil, incluyendo la selección de avatar,
 * nombre, fecha de nacimiento y país. Utiliza MVVM con ViewModel para gestionar
 * la lógica de validación y persistencia de datos.
 */
@AndroidEntryPoint
public class EditUserProfileActivity extends AppCompatActivity {

    private ActivityEditUserProfileBinding binding;
    private EditUserProfileViewModel editUserProfileViewModel;

    private Calendar calendar;

    /**
     * Método llamado al crear la actividad.
     * Configura la vista, inicializa el ViewModel, prepara el selector de fecha,
     * el dropdown de países, y establece los observadores y listeners.
     *
     * @param savedInstanceState Bundle con estado previo si existe.
     */
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
        setUpCountryDropdown();

        setUpListeners();
        observeViewModel();
    }

    /**
     * Configura los listeners para los distintos elementos de la interfaz.
     * Incluye selección de avatar, botón de guardar, botón de retroceso,
     * y habilita el botón guardar en función de los cambios en los campos de texto.
     */
    private void setUpListeners() {
        binding.imgProfilePic.setOnClickListener(v -> launchAvatarPickDialog());

        binding.btnSaveProfile.setOnClickListener(v -> {
            String avatar = editUserProfileViewModel.getSelectedAvatar();
            String name = binding.etName.getText().toString().trim();
            String birthdate = binding.etBirthdate.getText().toString().trim();
            String country = binding.atvCountry.getText().toString().trim();

            editUserProfileViewModel.validateEditUserForm(avatar, name, birthdate, country);
        });

        binding.actionBack.setOnClickListener(v -> finish());

        enableButtonOnTextChange();
    }

    /**
     * Observa los cambios en el estado de la vista proporcionados por el ViewModel.
     * Actualiza la UI en función del estado de validación, carga, éxito o error.
     */
    private void observeViewModel() {
        editUserProfileViewModel.getEditUserProfileViewState().observe(this, editUserProfileViewState ->  {

            switch (editUserProfileViewState.getValidationState()){
                case VALIDATING:
                    binding.tilName.setError(editUserProfileViewState.isNameValid() ? null : "Nombre no permitido");
                    binding.tilBirthdate.setError(editUserProfileViewState.isBirthdateValid() ? null : "Fecha errónea");
                    binding.tilCountry.setError(editUserProfileViewState.isCountryValid() ? null : "País erróneo");
                    break;
                case IDLE:
                    binding.tilName.setError(null);
                    binding.tilBirthdate.setError(null);
                    binding.tilCountry.setError(null);
                    break;
            }
            switch (editUserProfileViewState.getStatus()) {
                case LOADING:
                    binding.btnSaveProfile.setEnabled(false);
                    break;
                case SUCCESS:
                    this.startActivity(new Intent(this, HomeActivity.class));
                    this.finish();
                    break;
                case ERROR:
                    Toast.makeText(this, editUserProfileViewState.getMessage(), Toast.LENGTH_LONG).show();
                    break;
            }
        });
    }

    /**
     * Inicializa y configura el selector de fecha para el campo de fecha de nacimiento.
     */
    private void setUpDatePicker() {
        DatePickerUtils.setupDatePicker(
                this,
                binding.etBirthdate,
                binding.etBirthdate
        );
    }

    /**
     * Configura el dropdown con la lista de países disponibles para el campo país.
     */
    private void setUpCountryDropdown() {
        AutoCompleteTextView countryInput = binding.atvCountry;

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                CountryProviderUtils.getCountries(this)
        );

        countryInput.setAdapter(adapter);
    }

    /**
     * Habilita el botón guardar solo cuando alguno de los campos de texto tiene contenido válido.
     */
    private void enableButtonOnTextChange(){
        TextWatcherUtils.enableViewOnTextChange(binding.etName, binding.btnSaveProfile,binding.tilName);
        TextWatcherUtils.enableViewOnTextChange(binding.etBirthdate, binding.btnSaveProfile,binding.tilBirthdate);
        TextWatcherUtils.enableViewOnTextChange(binding.atvCountry, binding.btnSaveProfile,binding.tilCountry);
    }

    /**
     * Muestra un diálogo para que el usuario seleccione un avatar de entre los disponibles.
     * Actualiza el avatar seleccionado en el ViewModel y en la UI.
     */
    private void launchAvatarPickDialog() {
        Avatar[] avatars = Avatar.values();

        Integer[] avatarImages = new Integer[avatars.length];
        for (int i = 0; i < avatars.length; i++) {
            avatarImages[i] = avatars[i].getDrawableResId();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this, R.layout.avatar_grid_item, avatarImages) {
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(EditUserProfileActivity.this)
                            .inflate(R.layout.avatar_grid_item, parent, false);
                }

                ImageView imageView = convertView.findViewById(R.id.avatar_image);
                imageView.setImageResource(avatarImages[position]);

                imageView.setPadding(3, 3, 3, 3);

                return convertView;
            }
        };

        GridView gridView = new GridView(this);
        gridView.setNumColumns(3);
        gridView.setAdapter(adapter);

        builder.setView(gridView);

        final AlertDialog dialog = builder.create();

        gridView.setOnItemClickListener((parent, view, position, id) -> {
            Avatar selected = avatars[position];
            editUserProfileViewModel.setSelectedAvatar(selected.getKey());
            binding.imgProfilePic.setImageResource(selected.getDrawableResId());
            dialog.dismiss();
        });

        dialog.show();
    }
}
