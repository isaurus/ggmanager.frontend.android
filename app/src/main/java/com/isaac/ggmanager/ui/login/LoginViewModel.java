package com.isaac.ggmanager.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByIdUseCase;
import com.isaac.ggmanager.domain.usecase.login.LoginWithGoogleUseCase; // Caso de uso para obtener user de Firestore

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {

    private final LoginWithGoogleUseCase loginWithGoogleUseCase;
    private final GetUserByIdUseCase getUserByIdUseCase;

    public final MutableLiveData<LoginViewState> loginViewState = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginWithGoogleUseCase loginWithGoogleUseCase,
                          GetUserByIdUseCase getUserByIdUseCase) {
        this.loginWithGoogleUseCase = loginWithGoogleUseCase;
        this.getUserByIdUseCase = getUserByIdUseCase;
    }

    public LiveData<LoginViewState> getLoginViewState() {
        return loginViewState;
    }

    public void loginWithGoogle(String tokenId) {
        loginViewState.setValue(LoginViewState.loading());
        loginWithGoogleUseCase.execute(tokenId).observeForever(resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case SUCCESS:
                    fetchUserProfile();
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

    private void fetchUserProfile(){
        getUserByIdUseCase.execute().observeForever(resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    UserModel user = resource.getData();
                    if (user != null){
                        loginViewState.setValue(LoginViewState.userHasProfile());
                    } else {
                        loginViewState.setValue(LoginViewState.userHasNoProfile());
                    }
                    break;
                case LOADING:
                    loginViewState.setValue(LoginViewState.loading());
                    break;
                case ERROR:
                    loginViewState.setValue(LoginViewState.error(resource.getMessage()));
            }
        });
    }
}
