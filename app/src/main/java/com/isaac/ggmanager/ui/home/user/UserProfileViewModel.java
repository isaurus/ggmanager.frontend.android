package com.isaac.ggmanager.ui.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserProfileViewModel extends ViewModel {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    public final MediatorLiveData<UserProfileViewState> userProfileViewState = new MediatorLiveData<>();

    @Inject
    public UserProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                                GetAuthenticatedUserUseCase getAuthenticatedUserUseCase){
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public LiveData<UserProfileViewState> getUserProfileViewState() { return userProfileViewState; }

    public void getUserProfile(){
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> getUserProfileResult = getCurrentUserUseCase.execute(currentUserId);
        userProfileViewState.setValue(UserProfileViewState.loading());

        userProfileViewState.addSource(getUserProfileResult, userModelResource -> {
            if (userModelResource == null) return;
            switch (userModelResource.getStatus()){
                case SUCCESS:
                    UserModel user = userModelResource.getData();
                    userProfileViewState.setValue(UserProfileViewState.success(user));
                    userProfileViewState.removeSource(getUserProfileResult);
                    break;
                case ERROR:
                    userProfileViewState.setValue(UserProfileViewState.error(userModelResource.getMessage()));
                    break;
            }
        });
    }
}
