package com.isaac.ggmanager.ui.home.team.member;

import android.util.Log;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.team.AddUserToTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MemberViewModel extends ViewModel {

    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final AddUserToTeamUseCase addUserToTeamUseCase;
    private final UpdateUserTeamUseCase updateUserTeamUseCase;

    private final MediatorLiveData<MemberViewState> memberViewState = new MediatorLiveData<>();

    @Inject
    public MemberViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                           AddUserToTeamUseCase addUserToTeamUseCase,
                           UpdateUserTeamUseCase updateUserTeamUseCase){
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.addUserToTeamUseCase = addUserToTeamUseCase;
        this.updateUserTeamUseCase = updateUserTeamUseCase;
    }

    public LiveData<MemberViewState> getMemberViewState() { return memberViewState; }

    public void getTeamId(String email){
        LiveData<Resource<UserModel>> getCurrentUserResult = getCurrentUserUseCase.execute();
        memberViewState.setValue(MemberViewState.loading());

        memberViewState.addSource(getCurrentUserResult, userModelResource -> {
            if (userModelResource == null) return;

            switch (userModelResource.getStatus()){
                case SUCCESS:
                    String teamId = userModelResource.getData().getTeamId();
                    addUserToTeam(teamId, email);
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

    public void addUserToTeam(String teamId, String email){
        LiveData<Resource<Boolean>> addUserToTeamResult = addUserToTeamUseCase.execute(teamId, email);

        memberViewState.addSource(addUserToTeamResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    addTeamToUser(teamId, email);
                    memberViewState.removeSource(addUserToTeamResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(booleanResource.getMessage()));
            }
        });
    }

    public void addTeamToUser(String teamId, String userEmail){
        LiveData<Resource<Boolean>> addTeamToUserResult = updateUserTeamUseCase.execute(teamId, userEmail);

        memberViewState.addSource(addTeamToUserResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    memberViewState.setValue(MemberViewState.success());
                    memberViewState.removeSource(addTeamToUserResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(booleanResource.getMessage()));
                    break;
            }
        });
    }

    public void validateEmail(String email){
        boolean isEmailValid = isValidEmail(email);

        memberViewState.setValue(MemberViewState.validating(isEmailValid));

        if (isEmailValid){
            memberViewState.setValue(MemberViewState.loading());

            getTeamId(email);
        }
    }

    private boolean isValidEmail(String email){
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
