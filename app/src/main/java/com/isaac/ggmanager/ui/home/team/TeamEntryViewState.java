package com.isaac.ggmanager.ui.home.team;

import com.isaac.ggmanager.core.Resource;

public class TeamEntryViewState {

    private final Resource<?> resource;
    private final boolean hasTeam;

    public TeamEntryViewState(Resource<?> resource, boolean hasTeam){
        this.resource = resource;
        this.hasTeam = hasTeam;
    }

    public static <T> TeamEntryViewState success(T data){
        return new TeamEntryViewState(Resource.success(data), true);
    }

    public static TeamEntryViewState loading(){
        return new TeamEntryViewState(Resource.loading(), true);
    }

    public static TeamEntryViewState error(String message){
        return new TeamEntryViewState(Resource.error(message), true);
    }

    public Resource.Status getStatus(){ return resource.getStatus(); }

    public <T> T getData(){ return (T) resource.getData(); }

    public String getMessage() { return resource.getMessage(); }
}
