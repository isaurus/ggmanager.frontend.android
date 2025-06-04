package com.isaac.ggmanager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final SignOutUseCase signoutUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;


    private final MediatorLiveData<HomeViewState> homeViewState = new MediatorLiveData<>();

    @Inject
    public HomeViewModel(SignOutUseCase signoutUseCase,
                         GetCurrentUserUseCase getCurrentUserUseCase,
                         GetAuthenticatedUserUseCase getAuthenticatedUserUseCase) {
        this.signoutUseCase = signoutUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    public LiveData<HomeViewState> getHomeViewState() { return homeViewState; }

    public void getUserTeam(){
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute(currentUserId);
        homeViewState.setValue(HomeViewState.loading());

        homeViewState.addSource(userResult, resource -> {
            if (resource == null) return;
            switch (resource.getStatus()){
                case SUCCESS:
                    UserModel user = resource.getData();
                    if (user.getTeamId() != null && !user.getTeamId().isEmpty()) {
                        homeViewState.setValue(HomeViewState.userHasTeam());
                    } else {
                        homeViewState.setValue(HomeViewState.userHasNoTeam());
                    }
                    homeViewState.removeSource(userResult);
                    break;
                case ERROR:
                    homeViewState.setValue(HomeViewState.error(resource.getMessage()));
                    homeViewState.removeSource(userResult);
                    break;
                case LOADING:
                    homeViewState.setValue(HomeViewState.loading());
                    break;
            }
        });
    }

    public void signOut(){
        signoutUseCase.execute();
    }
}