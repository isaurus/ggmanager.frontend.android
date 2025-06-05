package com.isaac.ggmanager.ui.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.CreateUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserUseCase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel encargado de gestionar la lógica y estado de la edición del perfil de usuario.
 * Realiza validaciones de formulario, interacción con casos de uso para crear y actualizar usuarios,
 * y mantiene el estado observable para la UI.
 */
@HiltViewModel
public class EditUserProfileViewModel extends ViewModel {

    private static final int MAX_NAME_LENGTH = 20;

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final CreateUserUseCase createUserUseCase;
    private final UpdateUserUseCase updateUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    /**
     * Estado observable que contiene el estado actual de la vista de edición de perfil.
     */
    public final MediatorLiveData<EditUserProfileViewState> editUserProfileViewState = new MediatorLiveData<>();

    private String selectedAvatar;

    /**
     * Constructor inyectado con los casos de uso necesarios.
     *
     * @param getAuthenticatedUserUseCase Caso de uso para obtener usuario autenticado actual.
     * @param createUserUseCase Caso de uso para crear un nuevo usuario.
     * @param updateUserUseCase Caso de uso para actualizar usuario existente.
     * @param getCurrentUserUseCase Caso de uso para obtener el usuario actual.
     */
    @Inject
    public EditUserProfileViewModel(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                                    CreateUserUseCase createUserUseCase,
                                    UpdateUserUseCase updateUserUseCase,
                                    GetCurrentUserUseCase getCurrentUserUseCase){
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.createUserUseCase = createUserUseCase;
        this.updateUserUseCase = updateUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    /**
     * Obtiene el estado observable de la vista de edición de perfil.
     *
     * @return LiveData con el estado de la vista.
     */
    public LiveData<EditUserProfileViewState> getEditUserProfileViewState() {
        return editUserProfileViewState;
    }

    /**
     * Ejecuta la creación del perfil de usuario usando el caso de uso correspondiente.
     * Actualiza el estado observable según el resultado.
     *
     * @param user Modelo de usuario a crear.
     */
    public void createUserProfile(UserModel user){
        LiveData<Resource<Boolean>> editUserProfileResult = createUserUseCase.execute(user);
        editUserProfileViewState.setValue(EditUserProfileViewState.loading());

        editUserProfileViewState.addSource(editUserProfileResult, resource -> {
            if (resource == null) return;
            switch (resource.getStatus()){
                case SUCCESS:
                    editUserProfileViewState.setValue(EditUserProfileViewState.success());
                    editUserProfileViewState.removeSource(editUserProfileResult);
                    break;
                case ERROR:
                    editUserProfileViewState.setValue(EditUserProfileViewState.error(resource.getMessage()));
                    break;
            }
        });
    }

    /**
     * Método placeholder para la actualización del perfil de usuario.
     * Actualmente sin implementación.
     */
    public void updateUserProfile(){}

    /**
     * Valida los datos del formulario de edición de perfil.
     * Si la validación es correcta, procede a crear el perfil del usuario.
     *
     * @param avatar Clave del avatar seleccionado.
     * @param name Nombre introducido por el usuario.
     * @param birthdate Fecha de nacimiento en formato "dd/MM/yyyy".
     * @param country País seleccionado.
     */
    public void validateEditUserForm(String avatar, String name, String birthdate, String country) {
        boolean isNameValid = isValidName(name);
        boolean isBirthdateValid = isValidBirthdate(birthdate);
        boolean isCountryValid = isValidCountry(country);

        if (!isValidAvatar(avatar)) {
            avatar = "ic_avatar_avocado"; // Valor por defecto en caso de avatar inválido
        }

        editUserProfileViewState.setValue(EditUserProfileViewState.validating(
                true,
                isNameValid,
                isBirthdateValid,
                isCountryValid
        ));

        if (isNameValid && isBirthdateValid && isCountryValid) {
            editUserProfileViewState.setValue(EditUserProfileViewState.loading());
            String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
            String currentUserEmail = getAuthenticatedUserUseCase.execute().getEmail();
            UserModel user = new UserModel(currentUserId, currentUserEmail, avatar, name, formatDate(birthdate), country, null, null, null);
            createUserProfile(user);
        }
    }

    /**
     * Valida si la clave del avatar es válida.
     *
     * @param avatar Clave del avatar.
     * @return true si el avatar no es nulo, false en caso contrario.
     */
    private boolean isValidAvatar(String avatar){
        return avatar != null;
    }

    /**
     * Valida el nombre.
     * Debe ser no vacío y no superar la longitud máxima permitida.
     *
     * @param name Nombre a validar.
     * @return true si es válido, false si es vacío o demasiado largo.
     */
    private boolean isValidName(String name){
        return !name.isEmpty() && !(name.length() > MAX_NAME_LENGTH);
    }

    /**
     * Valida el país.
     * Debe ser no vacío.
     *
     * @param country País a validar.
     * @return true si es válido, false si está vacío.
     */
    private boolean isValidCountry(String country){
        return !country.isEmpty();
    }

    /**
     * Valida la fecha de nacimiento.
     * Debe ser no vacía.
     *
     * @param birthdate Fecha a validar en formato texto.
     * @return true si es válido, false si está vacío.
     */
    private boolean isValidBirthdate(String birthdate) {
        return !birthdate.isEmpty();
    }

    /**
     * Convierte un String con fecha en formato "dd/MM/yyyy" a un objeto Date.
     *
     * @param birthdate Fecha en formato String.
     * @return Objeto Date o null si no se pudo parsear.
     */
    private Date formatDate(String birthdate){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try{
            return simpleDateFormat.parse(birthdate);
        } catch (ParseException e){
            e.getMessage();
        }
        return null;
    }

    /**
     * Establece el avatar seleccionado por clave.
     *
     * @param avatarKey Clave del avatar seleccionado.
     */
    public void setSelectedAvatar(String avatarKey) {
        this.selectedAvatar = avatarKey;
    }

    /**
     * Obtiene la clave del avatar seleccionado actualmente.
     *
     * @return Clave del avatar seleccionado.
     */
    public String getSelectedAvatar() {
        return selectedAvatar;
    }
}
