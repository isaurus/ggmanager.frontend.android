package com.isaac.ggmanager.ui.home.team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.usecase.home.team.member.CreateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateAdminTeamUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateTeamViewModel extends ViewModel {


    private final CreateTeamUseCase createTeamUseCase;
    private final UpdateAdminTeamUseCase updateAdminTeamUseCase;



    private final MediatorLiveData<CreateTeamViewState> createTeamViewState = new MediatorLiveData<>();

    @Inject
    public CreateTeamViewModel(CreateTeamUseCase createTeamUseCase,
                               UpdateAdminTeamUseCase updateAdminTeamUseCase){
        this.createTeamUseCase = createTeamUseCase;
        this.updateAdminTeamUseCase = updateAdminTeamUseCase;
    }

    public LiveData<CreateTeamViewState> getCreateTeamViewState() { return createTeamViewState; }

    public void createTeam(String teamName, String teamDescription){
        TeamModel team = createTeamModel(teamName, teamDescription);

        createTeamViewState.setValue(CreateTeamViewState.loading());

        LiveData<Resource<String>> createTeamResult = createTeamUseCase.execute(team);

        createTeamViewState.addSource(createTeamResult, stringResource -> {
            if (stringResource == null) return;

            switch (stringResource.getStatus()){
                case SUCCESS:
                    String teamId = stringResource.getData();
                    updateUserTeam(teamId);
                    createTeamViewState.removeSource(createTeamResult);
                    break;
                case LOADING:
                    createTeamViewState.setValue(CreateTeamViewState.loading());
                    break;
                case ERROR:
                    createTeamViewState.setValue(CreateTeamViewState.error(stringResource.getMessage()));
                    break;
            }
        });
    }

    public void updateUserTeam(String teamId){
        LiveData<Resource<Boolean>> updateUserTeamResult = updateAdminTeamUseCase.execute(teamId);

        createTeamViewState.addSource(updateUserTeamResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    createTeamViewState.setValue(CreateTeamViewState.success());
                    createTeamViewState.removeSource(updateUserTeamResult);
                    break;
                case ERROR:
                    createTeamViewState.setValue(CreateTeamViewState.error(booleanResource.getMessage()));
                    createTeamViewState.removeSource(updateUserTeamResult);
                    break;
            }
        });
    }

    public void validateCreateTeamForm(String teamName, String teamDescription){
        boolean isTeamNameValid = isValidTeamName(teamName);
        boolean isTeamDescriptionValid = isValidTeamDescription(teamDescription);

        createTeamViewState.setValue(CreateTeamViewState.validating(isTeamNameValid, isTeamDescriptionValid));

        if (isTeamNameValid && isTeamDescriptionValid){
            createTeamViewState.setValue(CreateTeamViewState.loading());

            createTeam(teamName, teamDescription);
        }
    }

    private TeamModel createTeamModel(String teamName, String teamDescription){
        return new TeamModel(teamName, teamDescription);
    }

    private boolean isValidTeamName(String teamName){
        return teamName != null && !teamName.isEmpty();
    }

    private boolean isValidTeamDescription(String teamDescription){
        return teamDescription != null && !teamDescription.isEmpty();
    }
}
