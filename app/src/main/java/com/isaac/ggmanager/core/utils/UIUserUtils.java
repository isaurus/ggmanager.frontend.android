package com.isaac.ggmanager.core.utils;

import android.content.Context;

import com.isaac.ggmanager.databinding.ActivityEditUserProfileBinding;
import com.isaac.ggmanager.databinding.ActivityUserProfileBinding;
import com.isaac.ggmanager.domain.model.UserModel;

public class UIUserUtils {

    public static void fillUserProfileUI(ActivityUserProfileBinding binding, UserModel userModel, Context context) {
        int resId = context.getResources().getIdentifier(userModel.getAvatar(), "drawable", context.getPackageName());
        binding.imgProfilePic.setImageResource(resId);
        binding.tvUserName.setText(userModel.getName());
        binding.tvEmail.setText(userModel.getEmail());
        binding.tvUserBirthdate.setText(DateFormatUtils.dateToString(userModel.getBirthdate()));
        binding.tvCountry.setText(userModel.getCountry());
    }

    public static void fillEditUserProfileUI(com.isaac.ggmanager.databinding.ActivityEditUserProfileBinding binding, UserModel userModel, Context context) {
        int resId = context.getResources().getIdentifier(userModel.getAvatar(), "drawable", context.getPackageName());
        binding.imgProfilePic.setImageResource(resId);
        binding.etName.setText(userModel.getName());
        binding.etBirthdate.setText(DateFormatUtils.dateToString(userModel.getBirthdate()));
        binding.atvCountry.setText(userModel.getCountry());
    }
}
