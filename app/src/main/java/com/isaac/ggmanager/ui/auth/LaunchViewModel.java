package com.isaac.ggmanager.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LaunchViewModel extends ViewModel {

    private final CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public final MutableLiveData<LaunchViewState> launchViewState = new MutableLiveData<>();

    @Inject
    public LaunchViewModel(CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase,
                           GetCurrentUserUseCase getCurrentUserUseCase){
        this.checkAuthenticatedUserUseCase = checkAuthenticatedUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<LaunchViewState> getLaunchViewState() { return launchViewState; }

    public void isUserPersisted(){
        getCurrentUserUseCase.execute().observeForever(resource -> {
            launchViewState.setValue(LaunchViewState.success(resource.getData()));
        });
    }


    public boolean isUserAuthenticated() { return checkAuthenticatedUserUseCase.execute(); }



}
