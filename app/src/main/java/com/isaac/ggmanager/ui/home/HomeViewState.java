package com.isaac.ggmanager.ui.home;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

public class HomeViewState {

    @Nullable private final Resource<?> resource;
    private final boolean userHasTeam;

    public HomeViewState(@Nullable Resource<?> resource, boolean userHasTeam) {
        this.resource = resource;
        this.userHasTeam = userHasTeam;
    }

    public static HomeViewState userHasTeam(){
        return new HomeViewState(null, true);
    }

    public static HomeViewState userHasNoTeam(){
        return new HomeViewState(null, false);
    }

    public static <T> HomeViewState success(T data, boolean userHasTeam) {
        return new HomeViewState(
                Resource.success(data),
                userHasTeam);
    }

    public static HomeViewState loading() {
        return new HomeViewState(
                Resource.loading(),
                false);
    }


    public static HomeViewState error(String message) {
        return new HomeViewState(
                Resource.error(message),
                false);
    }

    public Resource<?> getResource() {
        return resource;
    }

    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    public String getMessage() { return resource != null ? resource.getMessage() : "Error desconocido"; }

    public <T> T getData() {
        return resource != null ? (T) resource.getData() : null;
    }


    public boolean isUserHasTeam() {
        return userHasTeam;
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
