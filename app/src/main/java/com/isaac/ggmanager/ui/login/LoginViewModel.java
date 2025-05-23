package com.isaac.ggmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.CreateUserUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginWithGoogleUseCase loginWithGoogleUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;      // LO UTILIZO PARA OBTENER UID DE FIREBASE AUTH DEL USER
    private final CreateUserUseCase createUserUseCase;

    public final MutableLiveData<LoginViewState> loginViewState = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginWithGoogleUseCase loginWithGoogleUseCase,
                          CreateUserUseCase createUserUseCase,
                          GetAuthenticatedUserUseCase getAuthenticatedUserUseCase){
        this.loginWithGoogleUseCase = loginWithGoogleUseCase;
        this.createUserUseCase = createUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public LiveData<LoginViewState> getLoginViewState(){
        return loginViewState;
    }


    public void loginWithGoogle(String tokenId) {
        loginViewState.setValue(LoginViewState.loading());
        loginWithGoogleUseCase.execute(tokenId).observeForever(resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    createUserUseCase.execute(getAuthenticatedUserUseCase.execute());
                    loginViewState.setValue(LoginViewState.success());
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
}
