package com.isaac.ggmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;
import com.isaac.ggmanager.core.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginWithGoogleUseCase loginWithGoogleUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    private final MediatorLiveData<LoginViewState> loginViewState = new MediatorLiveData<>();

    @Inject
    public LoginViewModel(LoginWithGoogleUseCase loginWithGoogleUseCase,
                          GetCurrentUserUseCase getCurrentUserUseCase) {
        this.loginWithGoogleUseCase = loginWithGoogleUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<LoginViewState> getLoginViewState() {
        return loginViewState;
    }

    public void loginWithGoogle(String tokenId) {
        loginViewState.setValue(LoginViewState.loading());

        LiveData<Resource<Boolean>> loginResult = loginWithGoogleUseCase.execute(tokenId);

        loginViewState.addSource(loginResult, resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case SUCCESS:
                    loginViewState.removeSource(loginResult);
                    fetchUserProfile();
                    break;
                case ERROR:
                    loginViewState.setValue(LoginViewState.error(resource.getMessage()));
                    loginViewState.removeSource(loginResult);
                    break;
                case LOADING:
                    loginViewState.setValue(LoginViewState.loading());
                    break;
            }
        });
    }

    private void fetchUserProfile() {

        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute();

        loginViewState.addSource(userResult, resource -> {
            if (resource == null) return;

            switch (resource.getStatus()) {
                case SUCCESS:
                    UserModel user = resource.getData();
                    if (user != null) {
                        loginViewState.setValue(LoginViewState.userHasProfile());
                    } else {
                        loginViewState.setValue(LoginViewState.userHasNoProfile());
                    }
                    loginViewState.removeSource(userResult);
                    break;
                case ERROR:
                    loginViewState.setValue(LoginViewState.error(resource.getMessage()));
                    loginViewState.removeSource(userResult);
                    break;
                case LOADING:
                    loginViewState.setValue(LoginViewState.loading());
                    break;
            }
        });
    }
}
