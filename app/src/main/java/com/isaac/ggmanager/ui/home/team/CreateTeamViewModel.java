package com.isaac.ggmanager.ui.home.team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateAdminTeamUseCase;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateTeamViewModel extends ViewModel {


    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final CreateTeamUseCase createTeamUseCase;
    private final UpdateAdminTeamUseCase updateAdminTeamUseCase;

    private final MediatorLiveData<CreateTeamViewState> createTeamViewState = new MediatorLiveData<>();

    @Inject
    public CreateTeamViewModel(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                               CreateTeamUseCase createTeamUseCase,
                               UpdateAdminTeamUseCase updateAdminTeamUseCase){
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.createTeamUseCase = createTeamUseCase;
        this.updateAdminTeamUseCase = updateAdminTeamUseCase;
    }

    public LiveData<CreateTeamViewState> getCreateTeamViewState() { return createTeamViewState; }

    public void createTeam(TeamModel team){
        createTeamViewState.setValue(CreateTeamViewState.loading());
        LiveData<Resource<String>> createTeamResult = createTeamUseCase.execute(team);

        createTeamViewState.addSource(createTeamResult, stringResource -> {
            if (stringResource == null) return;
            switch (stringResource.getStatus()){
                case SUCCESS:
                    String teamId = stringResource.getData();
                    updateOwnerTeam(teamId);
                    createTeamViewState.setValue(CreateTeamViewState.success());
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

    public void updateOwnerTeam(String teamId){
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<Boolean>> updateUserTeamResult = updateAdminTeamUseCase.execute(currentUserId, teamId);

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
            String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
            TeamModel team = new TeamModel(null, teamName, teamDescription, new ArrayList<>(Arrays.asList(currentUserId)));
            createTeam(team);
        }
    }

    private boolean isValidTeamName(String teamName){
        return teamName != null && !teamName.isEmpty();
    }

    private boolean isValidTeamDescription(String teamDescription){
        return teamDescription != null && !teamDescription.isEmpty();
    }
}