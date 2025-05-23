package com.isaac.ggmanager.ui.home.user;

import com.isaac.ggmanager.core.Resource;

public class EditUserProfileViewState {
    private final Resource<?> resource;
    private final boolean isAvatarValid;
    private final boolean isNameValid;
    private final boolean isBirthdateValid;
    private final boolean isCountryValid;

    public EditUserProfileViewState(Resource<?> resource,
                                    boolean isAvatarValid,
                                    boolean isNameValid,
                                    boolean isBirthdateValid,
                                    boolean isCountryValid) {
        this.resource = resource;
        this.isAvatarValid = isAvatarValid;
        this.isNameValid = isNameValid;
        this.isBirthdateValid = isBirthdateValid;
        this.isCountryValid = isCountryValid;
    }

    public static <T> EditUserProfileViewState success(){
        return new EditUserProfileViewState(
                Resource.success(null),
                true,
                true,
                true,
                true
        );
    }

    public static <T> EditUserProfileViewState loading(){
        return new EditUserProfileViewState(
                Resource.loading(),
                true,
                true,
                true,
                true
        );
    }

    public static <T> EditUserProfileViewState error(String message){
        return new EditUserProfileViewState(
                Resource.error(message),
                true,
                true,
                true,
                true
        );
    }

    public static <T> EditUserProfileViewState validating(boolean isAvatarValid,
                                                          boolean isNameValid,
                                                          boolean isBirthdateValid,
                                                          boolean isCountryValid) {
        return new EditUserProfileViewState(
                Resource.validating(),
                isAvatarValid,
                isNameValid,
                isBirthdateValid,
                isCountryValid
        );
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

    public boolean isAvatarValid(){
        return isAvatarValid;
    }

    public boolean isNameValid(){
        return isNameValid;
    }

    public boolean isBirthdateValid(){
        return isBirthdateValid;
    }

    public boolean isCountryValid(){
        return isCountryValid;
    }
}
