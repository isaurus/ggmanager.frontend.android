package com.isaac.ggmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;
import com.isaac.ggmanager.core.Resource;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginWithGoogleUseCase loginWithGoogleUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    private final MediatorLiveData<LoginViewState> loginViewState = new MediatorLiveData<>();

    @Inject
    public LoginViewModel(LoginWithGoogleUseCase loginWithGoogleUseCase,
                          GetCurrentUserUseCase getCurrentUserUseCase,
                          GetAuthenticatedUserUseCase getAuthenticatedUserUseCase) {
        this.loginWithGoogleUseCase = loginWithGoogleUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public LiveData<LoginViewState> getLoginViewState() {
        return loginViewState;
    }

    public void loginWithGoogle(String tokenId) {
        LiveData<Resource<Boolean>> loginResult = loginWithGoogleUseCase.execute(tokenId);
        loginViewState.setValue(LoginViewState.loading());

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
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute(currentUserId);

        loginViewState.addSource(userResult, userModelResource -> {
            if (userModelResource == null) return;

            switch (userModelResource.getStatus()) {
                case SUCCESS:
                    UserModel user = userModelResource.getData();
                    if (user != null) {
                        loginViewState.setValue(LoginViewState.userHasProfile());
                    } else {
                        loginViewState.setValue(LoginViewState.userHasNoProfile());
                    }
                    loginViewState.removeSource(userResult);
                    break;
                case ERROR:
                    loginViewState.setValue(LoginViewState.error(userModelResource.getMessage()));
                    loginViewState.removeSource(userResult);
                    break;
                case LOADING:
                    loginViewState.setValue(LoginViewState.loading());
                    break;
            }
        });
    }
}
