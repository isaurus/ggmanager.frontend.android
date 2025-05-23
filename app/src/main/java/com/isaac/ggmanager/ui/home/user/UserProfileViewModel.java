package com.isaac.ggmanager.ui.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserProfileViewModel extends ViewModel {

    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public final MutableLiveData<UserProfileViewState> userProfileViewState = new MutableLiveData<>();

    @Inject
    public UserProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase){
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    public LiveData<UserProfileViewState> getUserProfileViewState() { return userProfileViewState; }

    public void getUserProfile(){
        getCurrentUserUseCase.execute().observeForever(resource -> {
            switch (resource.getStatus()) {
                case SUCCESS:
                    userProfileViewState.setValue(UserProfileViewState.success(resource.getData()));
                    break;
                case LOADING:
                    userProfileViewState.setValue(UserProfileViewState.loading());
                    break;
                case ERROR:
                    userProfileViewState.setValue(UserProfileViewState.error(resource.getMessage()));
                    break;
            }
        });
    }
}
