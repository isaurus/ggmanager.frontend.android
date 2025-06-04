package com.isaac.ggmanager.ui.home.user;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;

public class UserProfileViewState {

    private final Resource<?> resource;
    private final UserModel user;

    public UserProfileViewState(Resource<?> resource, UserModel user){
        this.resource = resource;
        this.user = user;
    }

    public static UserProfileViewState success(UserModel user){
        return new UserProfileViewState(Resource.success(user), user);
    }

    public static UserProfileViewState loading(){
        return new UserProfileViewState(Resource.loading(), null);
    }

    public static UserProfileViewState error(String message){
        return new UserProfileViewState(Resource.error(message), null);
    }

    public UserModel getUser() { return user;}

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
