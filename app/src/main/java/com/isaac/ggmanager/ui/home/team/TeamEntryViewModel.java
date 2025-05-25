package com.isaac.ggmanager.ui.home.team;

import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.usecase.home.CheckUserHasTeamUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TeamEntryViewModel extends ViewModel {

    private final CheckUserHasTeamUseCase checkUserHasTeamUseCase;

    @Inject
    public TeamEntryViewModel(CheckUserHasTeamUseCase checkUserHasTeamUseCase){
        this.checkUserHasTeamUseCase = checkUserHasTeamUseCase;
    }

}
