package com.isaac.ggmanager.ui.home;

import com.isaac.ggmanager.core.Resource;

public class HomeViewState {
    private final Resource<?> resource;
    private final boolean userHasTeam;

    public HomeViewState(Resource<?> resource, boolean userHasTeam) {
        this.resource = resource;
        this.userHasTeam = userHasTeam;
    }

    public static HomeViewState userHasTeam(){
        return new HomeViewState(Resource.success(true), true);
    }

    public static HomeViewState userHasNoTeam(){
        return new HomeViewState(Resource.success(false), false);
    }

    public static HomeViewState loading() {
        return new HomeViewState(Resource.loading(), false); // false por defecto
    }

    public static <T> HomeViewState success(T data, boolean userHasTeam) {
        return new HomeViewState(Resource.success(data), userHasTeam);
    }

    public static HomeViewState error(String message) {
        return new HomeViewState(Resource.error(message), false);
    }

    public static HomeViewState validating(boolean userHasTeam) {
        return new HomeViewState(Resource.validating(), userHasTeam);
    }

    // Getters
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
