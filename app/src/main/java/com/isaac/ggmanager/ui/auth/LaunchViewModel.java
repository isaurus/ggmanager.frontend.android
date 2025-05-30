package com.isaac.ggmanager.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByIdUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LaunchViewModel extends ViewModel {

    private final CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;  // Para lanzar a login o directamente a la app
    private final GetCurrentUserUseCase getCurrentUserUseCase;  // Para lanzar al Home o al Edit

    public final MediatorLiveData<LaunchViewState> launchViewState = new MediatorLiveData<>();

    @Inject
    public LaunchViewModel(CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase,
                           GetCurrentUserUseCase getCurrentUserUseCase){
        this.checkAuthenticatedUserUseCase = checkAuthenticatedUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<LaunchViewState> getLaunchViewState() { return launchViewState; }

    public void fetchUserProfile(){
        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute();

        launchViewState.addSource(userResult, resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    launchViewState.removeSource(userResult);
                    UserModel user = resource.getData();
                    if (user != null) {
                        launchViewState.setValue(LaunchViewState.userHasProfile());
                    } else {
                        launchViewState.setValue(LaunchViewState.userHasNoProfile());
                    }
            }
        });
    }

    public boolean isUserAuthenticated() { return checkAuthenticatedUserUseCase.execute(); }



}
