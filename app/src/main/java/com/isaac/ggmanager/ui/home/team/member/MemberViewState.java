package com.isaac.ggmanager.ui.home.team.member;

import com.isaac.ggmanager.core.Resource;

public class MemberViewState {

    public enum ValidationState {
        IDLE,
        VALIDATING
    }

    private final Resource<?> resource;
    private final ValidationState validationState;
    private final boolean isEmailValid;

    public MemberViewState(Resource<?> resource,
                           ValidationState validationState,
                           boolean isEmailValid){
        this.resource = resource;
        this.validationState = validationState;
        this.isEmailValid = isEmailValid;
    }

    public static MemberViewState success(){
        return new MemberViewState(
                Resource.success(true),
                ValidationState.IDLE,
                true);
    }

    public static MemberViewState loading(){
        return new MemberViewState(
                Resource.loading(),
                ValidationState.IDLE,
                true);
    }

    public static MemberViewState error(String message){
        return new MemberViewState(
                Resource.error(message),
                ValidationState.IDLE,
                false);
    }

    public static MemberViewState validating(boolean isEmailValid){
        return new MemberViewState(
                Resource.loading(),
                ValidationState.VALIDATING,
                isEmailValid);
    }

    public ValidationState getValidationState() { return validationState; }

    public Resource.Status getStatus() { return resource != null ? resource.getStatus() : null; }

    public String getMessage() { return resource != null ? resource.getMessage() : "Error desconocido"; }

    public boolean isEmailValid() { return isEmailValid; }
}
