package com.isaac.ggmanager.ui.auth;

import com.isaac.ggmanager.core.Resource;

public class LaunchViewState {

    private final Resource<?> resource;

    public LaunchViewState(Resource<?> resource){
        this.resource = resource;
    }

    public static <T> LaunchViewState success(T data){
        return new LaunchViewState(Resource.success(data));
    }

    public static LaunchViewState error(String message){
        return new LaunchViewState(Resource.error(message));
    }

    public <T> T getData() {
        return (T) resource.getData();
    }

    public String getMessage() { return resource.getMessage(); }

    public Resource.Status getStatus(){
        return resource.getStatus();
    }
}
