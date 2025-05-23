package com.isaac.ggmanager.ui.home;

import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final SignOutUseCase signoutUseCase;

    @Inject
    public HomeViewModel(SignOutUseCase signoutUseCase) {
        this.signoutUseCase = signoutUseCase;
    }

    public void signOut(){
        signoutUseCase.execute();
    }
}
