package com.isaac.ggmanager.ui.login;

import com.isaac.ggmanager.core.Resource;

public class LoginViewState {

    private final Resource<?> resource;
    private final boolean userHasProfile;


    public LoginViewState(Resource<?> resource, boolean userHasProfile) {
        this.resource = resource;
        this.userHasProfile = userHasProfile;
    }

    public static LoginViewState userHasProfile(){
        return new LoginViewState(Resource.success(true), true);
    }

    public static LoginViewState userHasNoProfile(){
        return new LoginViewState(Resource.success(false), false);
    }

    public static LoginViewState validating(boolean userHasProfile){
        return new LoginViewState(Resource.validating(), userHasProfile);
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


    public Resource<?> getResource() {
        return resource;
    }

    public Resource.Status getStatus() {
        return resource.getStatus();
    }

    public String getMessage() {
        return resource.getMessage();
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