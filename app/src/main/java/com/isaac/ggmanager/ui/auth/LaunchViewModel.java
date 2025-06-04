package com.isaac.ggmanager.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByIdUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LaunchViewModel extends ViewModel {

    private final CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public final MediatorLiveData<LaunchViewState> launchViewState = new MediatorLiveData<>();

    @Inject
    public LaunchViewModel(CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase,
                           GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                           GetCurrentUserUseCase getCurrentUserUseCase){
        this.checkAuthenticatedUserUseCase = checkAuthenticatedUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<LaunchViewState> getLaunchViewState() { return launchViewState; }

    public void fetchUserProfile(){
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute(currentUserId);

        launchViewState.addSource(userResult, userModelResource -> {
            if (userModelResource == null) return;
            switch (userModelResource.getStatus()){
                case SUCCESS:
                    UserModel user = userModelResource.getData();
                    if (user != null) {
                        launchViewState.setValue(LaunchViewState.userHasProfile());
                    } else {
                        launchViewState.setValue(LaunchViewState.userHasNoProfile());
                    }
                    launchViewState.removeSource(userResult);
                    break;
                case ERROR:
                    launchViewState.setValue(LaunchViewState.error(userModelResource.getMessage()));
                    launchViewState.removeSource(userResult);
                    break;
            }
        });
    }

    public boolean isUserAuthenticated() { return checkAuthenticatedUserUseCase.execute(); }



}
