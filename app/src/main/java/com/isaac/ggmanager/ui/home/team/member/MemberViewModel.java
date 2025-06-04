package com.isaac.ggmanager.ui.home.team.member;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.AddUserToTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByEmailUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUsersByTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MemberViewModel extends ViewModel {

    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final AddUserToTeamUseCase addUserToTeamUseCase;
    private final UpdateUserTeamUseCase updateUserTeamUseCase;
    private final GetUsersByTeamUseCase getUsersByTeamUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    private final MediatorLiveData<MemberViewState> memberViewState = new MediatorLiveData<>();

    private String teamId;
    private String userToAddId;

    @Inject
    public MemberViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                           GetUserByEmailUseCase getUserByEmailUseCase,
                           AddUserToTeamUseCase addUserToTeamUseCase,
                           UpdateUserTeamUseCase updateUserTeamUseCase,
                           GetUsersByTeamUseCase getUsersByTeamUseCase,
                           GetAuthenticatedUserUseCase getAuthenticatedUserUseCase){
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.addUserToTeamUseCase = addUserToTeamUseCase;
        this.updateUserTeamUseCase = updateUserTeamUseCase;
        this.getUsersByTeamUseCase = getUsersByTeamUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public LiveData<MemberViewState> getMemberViewState() { return memberViewState; }

    public void loadMembers(){
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> getCurrentUserResult = getCurrentUserUseCase.execute(currentUserId);
        memberViewState.setValue(MemberViewState.loading());

        memberViewState.addSource(getCurrentUserResult, userModelResource -> {
            if (userModelResource == null) return;

            switch (userModelResource.getStatus()){
                case SUCCESS:
                    teamId = userModelResource.getData().getTeamId();
                    getTeamMembers();
                    memberViewState.removeSource(getCurrentUserResult);
                    break;
                case LOADING:
                    memberViewState.setValue(MemberViewState.loading());
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(userModelResource.getMessage()));
                    break;
            }
        });
    }

    public void getUserByEmail(String email){
        LiveData<Resource<UserModel>> getUserResult = getUserByEmailUseCase.execute(email);
        memberViewState.setValue(MemberViewState.loading());

        memberViewState.addSource(getUserResult, userModelResource -> {
            if (userModelResource == null) return;
            switch (userModelResource.getStatus()){
                case SUCCESS:
                    userToAddId = userModelResource.getData().getFirebaseUid();
                    addUserToTeam(userToAddId);
                    memberViewState.removeSource(getUserResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(userModelResource.getMessage()));
                    memberViewState.removeSource(getUserResult);
                    break;
            }
        });
    }

    public void addUserToTeam(String userToAddId){
        LiveData<Resource<Boolean>> addUserToTeamResult = addUserToTeamUseCase.execute(teamId, userToAddId);
        memberViewState.addSource(addUserToTeamResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    addTeamToUser();
                    memberViewState.removeSource(addUserToTeamResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(booleanResource.getMessage()));
                    break;
            }
        });
    }

    public void addTeamToUser(){
        LiveData<Resource<Boolean>> addTeamToUserResult = updateUserTeamUseCase.execute(userToAddId, teamId);

        memberViewState.addSource(addTeamToUserResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    updateMembers(teamId);
                    memberViewState.removeSource(addTeamToUserResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(booleanResource.getMessage()));
                    break;
            }
        });
    }

    private void updateMembers(String teamId){
        LiveData<Resource<List<UserModel>>> getMembersResult = getUsersByTeamUseCase.execute(teamId);

        memberViewState.addSource(getMembersResult, listResource -> {
            if (listResource == null) return;
            switch (listResource.getStatus()){
                case SUCCESS:
                    memberViewState.setValue(MemberViewState.success(listResource.getData()));
                    memberViewState.removeSource(getMembersResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(listResource.getMessage()));
                    memberViewState.removeSource(getMembersResult);
                    break;
            }
        });
    }

    public void getTeamMembers() {
        LiveData<Resource<List<UserModel>>> getMembersResult = getUsersByTeamUseCase.execute(teamId);
        memberViewState.addSource(getMembersResult, listResource -> {
            if (listResource == null) return;
            switch (listResource.getStatus()) {
                case SUCCESS:
                    memberViewState.setValue(MemberViewState.success(listResource.getData()));
                    memberViewState.removeSource(getMembersResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(listResource.getMessage()));
            }
        });
    }

    public void validateEmail(String email){
        boolean isEmailValid = isValidEmail(email);
        memberViewState.setValue(MemberViewState.validating(isEmailValid));

        if (isEmailValid){
            memberViewState.setValue(MemberViewState.loading());
            getUserByEmail(email);
        }
    }

    private boolean isValidEmail(String email){
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
