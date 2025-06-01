package com.isaac.ggmanager.ui.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.user.CreateUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class EditUserProfileViewModel extends ViewModel {

    private static final int MAX_NAME_LENGTH = 20;

    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public final MediatorLiveData<EditUserProfileViewState> editUserProfileViewState = new MediatorLiveData<>();

    private String selectedAvatar;

    @Inject
    public EditUserProfileViewModel(CreateUserUseCase createUserUseCase,
                                    UpdateUserUseCase updateUserUseCase,
                                    GetCurrentUserUseCase getCurrentUserUseCase){
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<EditUserProfileViewState> getEditUserProfileViewState() { return editUserProfileViewState; }

    public void createUserProfile(String avatar, String name, String birthdate, String country){
        UserModel user = createUserModel(avatar, name, birthdate, country);

        editUserProfileViewState.setValue(EditUserProfileViewState.loading());

        LiveData<Resource<Boolean>> editUserProfileResult = createUserUseCase.execute(user);

        editUserProfileViewState.addSource(editUserProfileResult, resource -> {
            if (resource == null) return;

            switch (resource.getStatus()){
                case SUCCESS:
                    editUserProfileViewState.setValue(EditUserProfileViewState.success());
                    editUserProfileViewState.removeSource(editUserProfileResult);
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

    public void updateUserProfile(){}   // TODO ¿CÓMO LO GESTIONO?


    public void validateEditUserForm(String avatar, String name, String birthdate, String country) {
        boolean isNameValid = isValidName(name);
        boolean isBirthdateValid = isValidBirthdate(birthdate);
        boolean isCountryValid = isValidCountry(country);

        if (!isValidAvatar(avatar)) {   // ESTA CONDICIÓN PROPORCIONA UN AVATAR PREDETERMINADO SI EL USUARIO NO SELECCIONA UNO, EVITANDO EL NULL
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
            createUserProfile(avatar, name, birthdate, country);
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

    public void setSelectedAvatar(String avatarKey) {
        this.selectedAvatar = avatarKey;
    }

    public String getSelectedAvatar() {
        return selectedAvatar;
    }
}