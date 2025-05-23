package com.isaac.ggmanager.ui.auth;

import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LaunchViewModel extends ViewModel {

    private final CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;

    @Inject
    public LaunchViewModel(CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase){
        this.checkAuthenticatedUserUseCase = checkAuthenticatedUserUseCase;
    }

    public boolean isUserAuthenticated() { return checkAuthenticatedUserUseCase.execute(); }

}
