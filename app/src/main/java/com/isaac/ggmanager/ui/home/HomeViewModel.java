package com.isaac.ggmanager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.home.CheckUserHasTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final SignOutUseCase signoutUseCase;
    private final CheckUserHasTeamUseCase checkUserHasTeamUseCase;

    private final MutableLiveData<HomeViewState> homeViewState = new MutableLiveData<>();

    @Inject
    public HomeViewModel(SignOutUseCase signoutUseCase,
                         CheckUserHasTeamUseCase checkUserHasTeamUseCase) {
        this.signoutUseCase = signoutUseCase;
        this.checkUserHasTeamUseCase = checkUserHasTeamUseCase;
    }

    public LiveData<HomeViewState> getHomeViewstate() { return homeViewState; }

    public void checkUserHasTeam(){
        checkUserHasTeamUseCase.execute().observeForever(resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    if(resource.getData()){
                        homeViewState.setValue(HomeViewState.userHasTeam());
                    } else{
                        homeViewState.setValue(HomeViewState.userHasNoTeam());
                    }
                    break;
                case LOADING:
                    homeViewState.setValue(HomeViewState.loading());
                    break;
                case ERROR:
                    homeViewState.setValue(HomeViewState.error(resource.getMessage()));
                    break;
            }
        });
    }

    public void signOut(){
        signoutUseCase.execute();
    }
}
