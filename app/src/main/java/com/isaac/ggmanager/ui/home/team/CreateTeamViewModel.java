package com.isaac.ggmanager.ui.home.team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class CreateTeamViewModel extends ViewModel {


    private final CreateTeamUseCase createTeamUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final UpdateUserTeamUseCase updateUserTeamUseCase;



    private final MutableLiveData<CreateTeamViewState> createTeamViewState = new MutableLiveData<>();

    @Inject
    public CreateTeamViewModel(CreateTeamUseCase createTeamUseCase,
                               GetCurrentUserUseCase getCurrentUserUseCase,
                               UpdateUserTeamUseCase updateUserTeamUseCase){
        this.createTeamUseCase = createTeamUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.updateUserTeamUseCase = updateUserTeamUseCase;
    }

    public LiveData<CreateTeamViewState> getCreateTeamViewState() { return createTeamViewState; }

    /*
    public void createTeam(String teamName, String teamDescription){
        TeamModel teamModel = new TeamModel(null, teamName, teamDescription, null, null);

        createTeamUseCase.execute(teamModel).observeForever(resource -> {
            switch (resource.getStatus()){
                case SUCCESS:
                    String teamId = resource.getData();

                    getCurrentUserUseCase.execute().observeForever(userResource -> {
                        if (userResource.getStatus() == Resource.Status.SUCCESS){
                            UserModel currentUser = userResource.getData();
                            currentUser.setTeamId(teamId);

                            saveUserProfileUseCase.execute(currentUser).observeForever(updatedResource -> {
                                if (updatedResource.getStatus() == Resource.Status.SUCCESS) createTeamViewState.setValue(CreateTeamViewState.success());
                            });
                        }
                    });

                    break;
                case LOADING:
                    createTeamViewState.setValue(CreateTeamViewState.loading());
                    break;
                case ERROR:
                    createTeamViewState.setValue(CreateTeamViewState.error(resource.getMessage()));
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

    private boolean isValidTeamName(String teamName){
        return teamName != null && !teamName.isEmpty();
    }

    private boolean isValidTeamDescription(String teamDescription){
        return teamDescription != null && !teamDescription.isEmpty();
    }

     */
}
