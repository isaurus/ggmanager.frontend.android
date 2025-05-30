package com.isaac.ggmanager.ui.auth;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

public class LaunchViewState {

    @Nullable private final Resource<?> resource;
    private final boolean userHasProfile;

    public LaunchViewState(@Nullable Resource<?> resource,
                           boolean userHasProfile){
        this.resource = resource;
        this.userHasProfile = userHasProfile;
    }

    public static LaunchViewState userHasProfile(){
        return new LaunchViewState(null, true);
    }

    public static LaunchViewState userHasNoProfile(){
        return new LaunchViewState(null, false);
    }

    public static <T> LaunchViewState success(T data){  // TODO ¿ME PODRÍA INTERESAR ESA DATA PARA ALGO? AHORA MISMO NO LA USO
        return new LaunchViewState(Resource.success(data), true);
    }


    public static LaunchViewState error(String message){
        return new LaunchViewState(Resource.error(message), false);
    }

    public <T> T getData() {
        return (T) resource.getData();
    }

    public String getMessage() { return resource.getMessage(); }

    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    public boolean isUserHasProfile() {return userHasProfile; }
}
