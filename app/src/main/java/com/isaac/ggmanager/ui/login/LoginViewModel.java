package com.isaac.ggmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginWithGoogleUseCase loginWithGoogleUseCase;     // LO UTILIZO PARA OBTENER UID DE FIREBASE AUTH DEL USER
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public final MutableLiveData<LoginViewState> loginViewState = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginWithGoogleUseCase loginWithGoogleUseCase,
                          GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                          GetCurrentUserUseCase getCurrentUserUseCase){
        this.loginWithGoogleUseCase = loginWithGoogleUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<LoginViewState> getLoginViewState(){
        return loginViewState;
    }


    public void loginWithGoogle(String tokenId) {
        loginViewState.setValue(LoginViewState.loading());
        loginWithGoogleUseCase.execute(tokenId).observeForever(resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    isUserPersisted();
                    break;
                case ERROR:
                    loginViewState.setValue(LoginViewState.error(resource.getMessage()));
                    break;
                case LOADING:
                    loginViewState.setValue(LoginViewState.loading());
                    break;
            }
        });
    }

    private void isUserPersisted(){
        getCurrentUserUseCase.execute().observeForever(resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    loginViewState.setValue(LoginViewState.success(resource.getData()));
                    break;
                case LOADING:
                    loginViewState.setValue(LoginViewState.loading());
                    break;
                case ERROR:
                    loginViewState.setValue(LoginViewState.error(resource.getMessage()));
                    break;
            }
        });
    }
}
