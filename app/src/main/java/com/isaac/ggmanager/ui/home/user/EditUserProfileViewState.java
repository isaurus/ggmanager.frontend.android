package com.isaac.ggmanager.ui.home.user;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

public class EditUserProfileViewState {

    public enum ValidationState {
        IDLE,        // No está validando, estado inicial
        VALIDATING
    }

    @Nullable private final Resource<?> resource;
    private final ValidationState validationState;

    private final boolean isAvatarValid;
    private final boolean isNameValid;
    private final boolean isBirthdateValid;
    private final boolean isCountryValid;

    public EditUserProfileViewState(@Nullable Resource<?> resource,
                                    ValidationState validationState,
                                    boolean isAvatarValid,
                                    boolean isNameValid,
                                    boolean isBirthdateValid,
                                    boolean isCountryValid) {
        this.resource = resource;
        this.validationState = validationState;
        this.isAvatarValid = isAvatarValid;
        this.isNameValid = isNameValid;
        this.isBirthdateValid = isBirthdateValid;
        this.isCountryValid = isCountryValid;
    }

    public static EditUserProfileViewState success(){   // TODO ¿ME INTERESA RECOGER LOS DATOS PARA IMPRIMIR LOS QUE YA TIENE, NO?
        return new EditUserProfileViewState(
               null,
                ValidationState.IDLE,
                true,
                true,
                true,
                true
        );
    }

    public static <T> EditUserProfileViewState loading(){
        return new EditUserProfileViewState(
                Resource.loading(),
                ValidationState.IDLE,
                false,
                false,
                false,
                false
        );
    }

    public static <T> EditUserProfileViewState error(String message){
        return new EditUserProfileViewState(
                Resource.error(message),
                ValidationState.IDLE,
                false,
                false,
                false,
                false
        );
    }

    public static <T> EditUserProfileViewState validating(boolean isAvatarValid,
                                                          boolean isNameValid,
                                                          boolean isBirthdateValid,
                                                          boolean isCountryValid) {
        return new EditUserProfileViewState(
                null,
                ValidationState.VALIDATING,
                isAvatarValid,
                isNameValid,
                isBirthdateValid,
                isCountryValid
        );
    }

    public Resource.Status getStatus() { return resource != null ? resource.getStatus() : null; }

    public ValidationState getValidationState() {
        return validationState;
    }

    public <T> T getData() {
        return (T) resource.getData();
    }

    public String getMessage() { return resource != null ? resource.getMessage() : "Error desconocido"; }

    public boolean isAvatarValid(){
        return isAvatarValid;
    }

    public boolean isNameValid(){
        return isNameValid;
    }

    public boolean isBirthdateValid(){
        return isBirthdateValid;
    }

    public boolean isCountryValid(){
        return isCountryValid;
    }
}
