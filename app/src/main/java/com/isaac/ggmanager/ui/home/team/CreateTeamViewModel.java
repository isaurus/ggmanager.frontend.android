package com.isaac.ggmanager.ui.home.team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateTeamViewModel extends ViewModel {

    private final CreateTeamUseCase createTeamUseCase;

    private final MutableLiveData<CreateTeamViewState> createTeamViewState = new MutableLiveData<>();

    @Inject
    public CreateTeamViewModel(CreateTeamUseCase createTeamUseCase){
        this.createTeamUseCase = createTeamUseCase;
    }

    public LiveData<CreateTeamViewState> getCreateTeamViewState() { return createTeamViewState; }

    public void createTeam(String teamName, String teamDescription){
        TeamModel teamModel = new TeamModel(teamName, teamDescription);
    }

    public void validateCreateTeamForm(String teamName, String teamDescription){
        boolean isTeamNameValid = isValidTeamName(teamName);
        boolean isTeamDescriptionValid = isValidTeamDescription(teamDescription);

        createTeamViewState.setValue(CreateTeamViewState.validating(isTeamNameValid, isTeamDescriptionValid));

        if (isTeamNameValid && isTeamDescriptionValid){
            createTeamViewState.setValue(CreateTeamViewState.loading());

        }
    }

    private boolean isValidTeamName(String teamName){
        return teamName != null && !teamName.isEmpty();
    }

    private boolean isValidTeamDescription(String teamDescription){
        return teamDescription != null && !teamDescription.isEmpty();
    }
}
