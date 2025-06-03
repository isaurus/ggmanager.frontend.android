package com.isaac.ggmanager.ui.home.team.member;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;

import java.util.List;

public class MemberViewState {

    public enum ValidationState {
        IDLE,
        VALIDATING
    }

    private final Resource<?> resource;
    private final ValidationState validationState;
    private final boolean isEmailValid;
    private final List<UserModel> memberList;

    public MemberViewState(Resource<?> resource,
                           ValidationState validationState,
                           boolean isEmailValid,
                           List<UserModel> memberList) {
        this.resource = resource;
        this.validationState = validationState;
        this.isEmailValid = isEmailValid;
        this.memberList = memberList;
    }

    public static MemberViewState loading() {
        return new MemberViewState(Resource.loading(), ValidationState.IDLE, true, null);
    }

    public static MemberViewState success(List<UserModel> members) {
        return new MemberViewState(Resource.success(members), ValidationState.IDLE, true, members);
    }

    public static MemberViewState error(String message) {
        return new MemberViewState(Resource.error(message), ValidationState.IDLE, false, null);
    }

    public static MemberViewState validating(boolean isEmailValid) {
        return new MemberViewState(Resource.loading(), ValidationState.VALIDATING, isEmailValid, null);
    }

    public ValidationState getValidationState() {
        return validationState;
    }

    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    public boolean isEmailValid() {
        return isEmailValid;
    }

    public List<UserModel> getMemberList() {
        return memberList;
    }

    public boolean isEmpty() {
        return memberList == null || memberList.isEmpty();
    }
}