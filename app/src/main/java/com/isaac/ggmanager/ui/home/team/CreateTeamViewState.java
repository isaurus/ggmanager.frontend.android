package com.isaac.ggmanager.ui.home.team;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

public class CreateTeamViewState {

    public enum ValidationState {
        IDLE,        // No está validando, estado inicial
        VALIDATING
    }

    private final Resource<?> resource;
    private final ValidationState validationState;
    private final boolean isTeamNameValid;
    private final boolean isTeamDescriptionValid;

    public CreateTeamViewState(Resource<?> resource,
                               ValidationState validationState,
                               boolean isTeamNameValid,
                               boolean isTeamDescriptionValid){
        this.resource = resource;
        this.validationState = validationState;
        this.isTeamNameValid = isTeamNameValid;
        this.isTeamDescriptionValid = isTeamDescriptionValid;
    }

    public static CreateTeamViewState success(){
        return new CreateTeamViewState(
                Resource.success(null),
                ValidationState.IDLE,
                true,
                true);
    }

    public static CreateTeamViewState loading(){
        return new CreateTeamViewState(
                Resource.loading(),
                ValidationState.IDLE,
                false,
                false);
    }

    public static CreateTeamViewState error(String message){
        return new CreateTeamViewState(
                Resource.error(message),
                ValidationState.IDLE,
                false,
                false);
    }

    public static CreateTeamViewState validating(boolean isTeamNameValid,
                                                 boolean isTeamDescriptionValid){
        return new CreateTeamViewState(
                Resource.loading(),
                ValidationState.VALIDATING,
                isTeamNameValid,
                isTeamDescriptionValid);
    }

    public Resource.Status getStatus() { return resource != null ? resource.getStatus() : null; }

    public String getMessage() { return resource != null ? resource.getMessage() : "Error desconocido"; }

    public ValidationState getValidationState() {
        return validationState;
    }

    public boolean isTeamNameValid() { return isTeamNameValid; }

    public boolean isTeamDescriptionValid() { return isTeamDescriptionValid; }
}
