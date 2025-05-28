package com.isaac.ggmanager.ui.home.team;

import com.isaac.ggmanager.core.Resource;

public class CreateTeamViewState {
    private final Resource<?> resource;
    private final boolean isTeamNameValid;
    private final boolean isTeamDescriptionValid;

    public CreateTeamViewState(Resource<?> resource,
                               boolean isTeamNameValid,
                               boolean isTeamDescriptionValid){
        this.resource = resource;
        this.isTeamNameValid = isTeamNameValid;
        this.isTeamDescriptionValid = isTeamDescriptionValid;
    }

    public static CreateTeamViewState success(){
        return new CreateTeamViewState(
                Resource.success(null),
                true,
                true);
    }

    public static CreateTeamViewState loading(){
        return new CreateTeamViewState(
                Resource.loading(),
                true,
                true);
    }

    public static CreateTeamViewState error(String message){
        return new CreateTeamViewState(
                Resource.error(message),
                true,
                true);
    }

    public static CreateTeamViewState validating(boolean isTeamNameValid,
                                                 boolean isTeamDescriptionValid){
        return new CreateTeamViewState(
                Resource.validating(),
                isTeamNameValid,
                isTeamDescriptionValid);
    }

    public Resource.Status getStatus() { return resource.getStatus(); }

    public String getMessage() { return resource.getMessage(); }

    public boolean isTeamNameValid() { return isTeamNameValid; }

    public boolean isTeamDescriptionValid() { return isTeamDescriptionValid; }
}
