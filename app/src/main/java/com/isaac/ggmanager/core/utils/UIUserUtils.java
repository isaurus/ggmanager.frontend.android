package com.isaac.ggmanager.core.utils;

import android.content.Context;

import com.isaac.ggmanager.databinding.ActivityEditUserProfileBinding;
import com.isaac.ggmanager.databinding.ActivityUserProfileBinding;
import com.isaac.ggmanager.domain.model.UserModel;

/**
 * Clase de utilidad para poblar vistas de perfil de usuario con datos del modelo {@link UserModel}.
 * <p>
 * Su objetivo es abstraer la lógica de UI relacionada con el usuario,
 * facilitando la reutilización del código y manteniendo los fragments/activities más limpios.
 * </p>
 *
 * Utiliza ViewBinding para interactuar con la interfaz gráfica de forma segura.
 *
 * @author Isaac
 */
public class UIUserUtils {

    /**
     * Llena las vistas de un perfil de usuario con los datos proporcionados en un {@link UserModel}.
     *
     * @param binding    ViewBinding de la actividad de perfil de usuario.
     * @param userModel  Objeto que contiene la información del usuario.
     * @param context    Contexto necesario para acceder a los recursos (como el avatar).
     */
    public static void fillUserProfileUI(ActivityUserProfileBinding binding, UserModel userModel, Context context) {
        // Obtiene el ID del recurso del avatar a partir del nombre en el modelo
        int resId = context.getResources().getIdentifier(userModel.getAvatar(), "drawable", context.getPackageName());

        // Asigna los valores del modelo a las vistas
        binding.imgProfilePic.setImageResource(resId);
        binding.tvUserName.setText(userModel.getName());
        binding.tvEmail.setText(userModel.getEmail());
        binding.tvUserBirthdate.setText(DateFormatUtils.dateToString(userModel.getBirthdate()));
        binding.tvCountry.setText(userModel.getCountry());
    }
}
