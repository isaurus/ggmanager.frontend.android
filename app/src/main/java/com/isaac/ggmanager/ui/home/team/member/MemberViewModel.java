package com.isaac.ggmanager.ui.home.team.member;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MemberViewModel extends ViewModel {

    private final InviteUserToTeamUseCase inviteUserToTeamUseCase;

    private final MutableLiveData<MemberViewState> memberViewState = new MutableLiveData<>();

    @Inject
    public MemberViewModel(InviteUserToTeamUseCase inviteUserToTeamUseCase){
        this.inviteUserToTeamUseCase = inviteUserToTeamUseCase;
    }

    public LiveData<MemberViewState> getMemberViewState() { return memberViewState; }

    public void inviteUserToTeam(String email){
        /*
        inviteUserToTeamUseCase.execute().observeForever(resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    break;
                case LOADING:
                    break;
                case VALIDATING:
                    break;
                case ERROR:
                    break;
            }
        });

         */
    }

    public void validateEmail(String email){
        boolean isEmailValid = isValidEmail(email);

        memberViewState.setValue(MemberViewState.validating(isEmailValid));

        if (isEmailValid){
            memberViewState.setValue(MemberViewState.loading());

            inviteUserToTeam(email);
        }
    }

    private boolean isValidEmail(String email){
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
