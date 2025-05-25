package com.isaac.ggmanager.ui.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.user.SaveUserProfileUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditUserProfileViewModel extends ViewModel {

    private static final int MAX_NAME_LENGTH = 20;

    private final SaveUserProfileUseCase saveUserProfileUseCase;

    public final MutableLiveData<EditUserProfileViewState> editUserProfileViewState = new MutableLiveData<>();

    @Inject
    public EditUserProfileViewModel(SaveUserProfileUseCase saveUserProfileUseCase){
        this.saveUserProfileUseCase = saveUserProfileUseCase;
    }

    public LiveData<EditUserProfileViewState> getEditUserProfileViewState() { return editUserProfileViewState; }

    public void saveUserProfile(String avatar, String name, String birthdate, String country){
        UserModel userModel = createUserModel(avatar, name, birthdate, country);

        saveUserProfileUseCase.execute(userModel).observeForever(resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    editUserProfileViewState.setValue(EditUserProfileViewState.success());
                    break;
                case LOADING:
                    editUserProfileViewState.setValue(EditUserProfileViewState.loading());
                    break;
                case ERROR:
                    editUserProfileViewState.setValue(EditUserProfileViewState.error(resource.getMessage()));
                    break;
            }
        });
    }

    public void validateEditUserForm(String avatar, String name, String birthdate, String country) {
        boolean isNameValid = isValidName(name);
        boolean isBirthdateValid = isValidBirthdate(birthdate);
        boolean isCountryValid = isValidCountry(country);

        if (!isValidAvatar(avatar)) {   // ESTA CONDICIÓN GESTIONA PROPORCIONA UN AVATAR PREDETERMINADO SI EL USUARIO NO SELECCIONA UNO, EVITANDO EL NULL
            avatar = "ic_avatar_avocado";
        }

        editUserProfileViewState.setValue(EditUserProfileViewState.validating(
                true,
                isNameValid,
                isBirthdateValid,
                isCountryValid
        ));

        if (isNameValid && isBirthdateValid && isCountryValid) {
            editUserProfileViewState.setValue(EditUserProfileViewState.loading());
            saveUserProfile(avatar, name, birthdate, country);
        }
    }






    private boolean isValidAvatar(String avatar){
        return avatar != null;
    }

    private boolean isValidName(String name){
        return !name.isEmpty() && !(name.length() > MAX_NAME_LENGTH);
    }

    private boolean isValidCountry(String country){
        return !country.isEmpty();
    }

    private boolean isValidBirthdate(String birthdate) {
        return !birthdate.isEmpty();
    }

    private UserModel createUserModel(String avatar, String name, String birthdate, String country){
        return new UserModel(avatar, name, formatDate(birthdate), country, null);
    }

    private Date formatDate(String birthdate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try{
            return simpleDateFormat.parse(birthdate);
        } catch (ParseException e){
            e.getMessage();
        }
        return null;
    }
}
