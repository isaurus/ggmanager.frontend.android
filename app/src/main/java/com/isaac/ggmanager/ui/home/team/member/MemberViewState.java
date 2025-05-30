package com.isaac.ggmanager.ui.home.team.member;

import com.isaac.ggmanager.core.Resource;

public class MemberViewState {
    private final Resource<?> resource;
    private final boolean isEmailValid;

    public MemberViewState(Resource<?> resource,
                           boolean isEmailValid){
        this.resource = resource;
        this.isEmailValid = isEmailValid;
    }

    public static MemberViewState success(){
        return new MemberViewState(Resource.success(true), true);
    }

    public static MemberViewState loading(){
        return new MemberViewState(Resource.loading(), true);
    }

    public static MemberViewState error(String message){
        return new MemberViewState(Resource.error(message), false);
    }

    /*
    public static MemberViewState validating(boolean isEmailValid){
        return new MemberViewState(Resource.validating(), isEmailValid);
    }

     */

    public Resource.Status getStatus() { return resource.getStatus(); }

    public String getMessage() { return resource.getMessage(); }

    public boolean isEmailValid() { return isEmailValid; }
}
