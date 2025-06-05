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

/**
 * ViewModel encargado de gestionar la lógica y estados relacionados con la creación de un equipo.
 * <p>
 * Valida el formulario, ejecuta el caso de uso para crear el equipo, y actualiza el equipo administrador
 * del usuario autenticado.
 * </p>
 */
@HiltViewModel
public class CreateTeamViewModel extends ViewModel {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final CreateTeamUseCase createTeamUseCase;
    private final UpdateAdminTeamUseCase updateAdminTeamUseCase;

    private final MediatorLiveData<CreateTeamViewState> createTeamViewState = new MediatorLiveData<>();

    @Inject
    public CreateTeamViewModel(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                               CreateTeamUseCase createTeamUseCase,
                               UpdateAdminTeamUseCase updateAdminTeamUseCase) {
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.createTeamUseCase = createTeamUseCase;
        this.updateAdminTeamUseCase = updateAdminTeamUseCase;
    }

    /**
     * Expone el estado actual de la creación de equipo como LiveData observable por la UI.
     *
     * @return LiveData con el estado de la creación del equipo.
     */
    public LiveData<CreateTeamViewState> getCreateTeamViewState() {
        return createTeamViewState;
    }

    /**
     * Ejecuta el caso de uso para crear un equipo y actualiza el estado en función del resultado.
     *
     * @param team Equipo a crear.
     */
    public void createTeam(TeamModel team) {
        createTeamViewState.setValue(CreateTeamViewState.loading());

        LiveData<Resource<String>> createTeamResult = createTeamUseCase.execute(team);

        createTeamViewState.addSource(createTeamResult, stringResource -> {
            if (stringResource == null) return;

            switch (stringResource.getStatus()) {
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

    /**
     * Actualiza el equipo administrador del usuario actual tras la creación de un equipo.
     *
     * @param teamId ID del equipo recién creado.
     */
    public void updateOwnerTeam(String teamId) {
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();

        LiveData<Resource<Boolean>> updateUserTeamResult = updateAdminTeamUseCase.execute(currentUserId, teamId);

        createTeamViewState.addSource(updateUserTeamResult, booleanResource -> {
            if (booleanResource == null) return;

            switch (booleanResource.getStatus()) {
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

    /**
     * Valida el formulario de creación de equipo y, si es válido, inicia el proceso de creación.
     *
     * @param teamName        Nombre del equipo.
     * @param teamDescription Descripción del equipo.
     */
    public void validateCreateTeamForm(String teamName, String teamDescription) {
        boolean isTeamNameValid = isValidTeamName(teamName);
        boolean isTeamDescriptionValid = isValidTeamDescription(teamDescription);

        createTeamViewState.setValue(CreateTeamViewState.validating(isTeamNameValid, isTeamDescriptionValid));

        if (isTeamNameValid && isTeamDescriptionValid) {
            createTeamViewState.setValue(CreateTeamViewState.loading());
            String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
            TeamModel team = new TeamModel(null, teamName, teamDescription, new ArrayList<>(Arrays.asList(currentUserId)));
            createTeam(team);
        }
    }

    /**
     * Comprueba si el nombre del equipo es válido (no nulo ni vacío).
     *
     * @param teamName Nombre del equipo.
     * @return true si válido, false en caso contrario.
     */
    private boolean isValidTeamName(String teamName) {
        return teamName != null && !teamName.isEmpty();
    }

    /**
     * Comprueba si la descripción del equipo es válida (no nula ni vacía).
     *
     * @param teamDescription Descripción del equipo.
     * @return true si válida, false en caso contrario.
     */
    private boolean isValidTeamDescription(String teamDescription) {
        return teamDescription != null && !teamDescription.isEmpty();
    }
}
