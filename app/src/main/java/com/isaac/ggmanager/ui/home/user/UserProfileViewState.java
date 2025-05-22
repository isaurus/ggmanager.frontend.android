package com.isaac.ggmanager.ui.home.user;

import com.isaac.ggmanager.core.Resource;

public class UserProfileViewState {

    private final Resource<?> resource;

    public UserProfileViewState(Resource<?> resource){
        this.resource = resource;
    }

    public static <T> UserProfileViewState success(T data){
        return new UserProfileViewState(Resource.success(data));
    }

    public static <T> UserProfileViewState loading(){
        return new UserProfileViewState(Resource.loading());
    }

    public static <T> UserProfileViewState error(String message){
        return new UserProfileViewState(Resource.error(message));
    }

    public Resource.Status getStatus(){
        return resource.getStatus();
    }

    public <T> T getData() {
        return (T) resource.getData();
    }

    public String getMessage(){
        return resource.getMessage();
    }
}
