package com.isaac.ggmanager.ui.login;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

public class LoginViewState {

    @Nullable private final Resource<?> resource;
    private final boolean userHasProfile;


    public LoginViewState(@Nullable Resource<?> resource, boolean userHasProfile) {
        this.resource = resource;
        this.userHasProfile = userHasProfile;
    }

    public static LoginViewState userHasProfile(){
        return new LoginViewState(Resource.success(null), true);
    }

    public static LoginViewState userHasNoProfile(){
        return new LoginViewState(Resource.success(null), false);
    }


    public static LoginViewState loading(){
        return new LoginViewState(Resource.loading(), false);
    }

    public static <T> LoginViewState success(T data){
        return new LoginViewState(Resource.success(data), true);
    }


    public static LoginViewState error(String message){
        return new LoginViewState(Resource.error(message), false);
    }

    public boolean isUserHasProfile(){
        return userHasProfile;
    }


    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }


    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    public <T> T getData() {
        return (T) resource.getData();
    }


    public boolean isLoading() {
        return resource.getStatus() == Resource.Status.LOADING;
    }


    public boolean isSuccess() {
        return resource.getStatus() == Resource.Status.SUCCESS;
    }


    public boolean isError() {
        return resource.getStatus() == Resource.Status.ERROR;
    }
}