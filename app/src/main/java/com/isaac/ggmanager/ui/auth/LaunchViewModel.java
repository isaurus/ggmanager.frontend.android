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

    private final CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;  // Para lanzar a login o directamente a la app
    private final GetCurrentUserUseCase getCurrentUserUseCase;  // Para lanzar al Home o al Edit

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
            switch (resource.getStatus()){
                case SUCCESS:
                    launchViewState.setValue(LaunchViewState.success(resource.getData()));
                    break;
                case ERROR:
                    launchViewState.setValue(LaunchViewState.error(resource.getMessage()));
                    break;
            }

        });
    }


    public boolean isUserAuthenticated() { return checkAuthenticatedUserUseCase.execute(); }



}
