package com.isaac.ggmanager.ui.home.team.member;

import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.AddUserToTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByEmailUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetUsersByTeamUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel encargado de gestionar la lógica y el estado de la UI para la gestión de miembros del equipo.
 * <p>
 * Esta clase se encarga de cargar la lista de miembros, validar emails, agregar usuarios al equipo y actualizar
 * la información relacionada con los miembros de un equipo específico.
 * </p>
 *
 * @author Isaac
 */
@HiltViewModel
public class MemberViewModel extends ViewModel {

    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetUserByEmailUseCase getUserByEmailUseCase;
    private final AddUserToTeamUseCase addUserToTeamUseCase;
    private final UpdateUserTeamUseCase updateUserTeamUseCase;
    private final GetUsersByTeamUseCase getUsersByTeamUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    private final MediatorLiveData<MemberViewState> memberViewState = new MediatorLiveData<>();

    private String teamId;
    private String userToAddId;

    /**
     * Constructor del ViewModel con inyección de dependencias.
     *
     * @param getCurrentUserUseCase      Caso de uso para obtener el usuario actual.
     * @param getUserByEmailUseCase      Caso de uso para obtener un usuario mediante su email.
     * @param addUserToTeamUseCase       Caso de uso para agregar un usuario a un equipo.
     * @param updateUserTeamUseCase      Caso de uso para actualizar el equipo de un usuario.
     * @param getUsersByTeamUseCase      Caso de uso para obtener la lista de usuarios de un equipo.
     * @param getAuthenticatedUserUseCase Caso de uso para obtener el usuario autenticado.
     */
    @Inject
    public MemberViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                           GetUserByEmailUseCase getUserByEmailUseCase,
                           AddUserToTeamUseCase addUserToTeamUseCase,
                           UpdateUserTeamUseCase updateUserTeamUseCase,
                           GetUsersByTeamUseCase getUsersByTeamUseCase,
                           GetAuthenticatedUserUseCase getAuthenticatedUserUseCase) {
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getUserByEmailUseCase = getUserByEmailUseCase;
        this.addUserToTeamUseCase = addUserToTeamUseCase;
        this.updateUserTeamUseCase = updateUserTeamUseCase;
        this.getUsersByTeamUseCase = getUsersByTeamUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    /**
     * Obtiene el LiveData que expone el estado de la vista de miembros.
     *
     * @return LiveData con el estado actual de los miembros.
     */
    public LiveData<MemberViewState> getMemberViewState() {
        return memberViewState;
    }

    /**
     * Carga la lista de miembros del equipo del usuario autenticado.
     * <p>
     * Primero obtiene el usuario autenticado, luego su equipo asociado, y finalmente carga los miembros de ese equipo.
     * </p>
     */
    public void loadMembers() {
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> getCurrentUserResult = getCurrentUserUseCase.execute(currentUserId);
        memberViewState.setValue(MemberViewState.loading());

        memberViewState.addSource(getCurrentUserResult, userModelResource -> {
            if (userModelResource == null) return;

            switch (userModelResource.getStatus()) {
                case SUCCESS:
                    teamId = userModelResource.getData().getTeamId();
                    getTeamMembers();
                    memberViewState.removeSource(getCurrentUserResult);
                    break;
                case LOADING:
                    memberViewState.setValue(MemberViewState.loading());
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(userModelResource.getMessage()));
                    break;
            }
        });
    }

    /**
     * Busca un usuario por su correo electrónico.
     *
     * @param email Correo electrónico del usuario a buscar.
     */
    public void getUserByEmail(String email) {
        LiveData<Resource<UserModel>> getUserResult = getUserByEmailUseCase.execute(email);
        memberViewState.setValue(MemberViewState.loading());

        memberViewState.addSource(getUserResult, userModelResource -> {
            if (userModelResource == null) return;
            switch (userModelResource.getStatus()) {
                case SUCCESS:
                    userToAddId = userModelResource.getData().getFirebaseUid();
                    addUserToTeam(userToAddId);
                    memberViewState.removeSource(getUserResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(userModelResource.getMessage()));
                    memberViewState.removeSource(getUserResult);
                    break;
            }
        });
    }

    /**
     * Agrega un usuario identificado por su ID a un equipo.
     *
     * @param userToAddId ID del usuario a agregar.
     */
    public void addUserToTeam(String userToAddId) {
        LiveData<Resource<Boolean>> addUserToTeamResult = addUserToTeamUseCase.execute(teamId, userToAddId);
        memberViewState.addSource(addUserToTeamResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()) {
                case SUCCESS:
                    addTeamToUser();
                    memberViewState.removeSource(addUserToTeamResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(booleanResource.getMessage()));
                    break;
            }
        });
    }

    /**
     * Actualiza el equipo asignado a un usuario para reflejar que ahora forma parte del equipo.
     */
    public void addTeamToUser() {
        LiveData<Resource<Boolean>> addTeamToUserResult = updateUserTeamUseCase.execute(userToAddId, teamId);

        memberViewState.addSource(addTeamToUserResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()) {
                case SUCCESS:
                    updateMembers(teamId);
                    memberViewState.removeSource(addTeamToUserResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(booleanResource.getMessage()));
                    break;
            }
        });
    }

    /**
     * Actualiza la lista de miembros del equipo obteniendo la información actualizada desde la fuente de datos.
     *
     * @param teamId ID del equipo cuyos miembros se desean obtener.
     */
    private void updateMembers(String teamId) {
        LiveData<Resource<List<UserModel>>> getMembersResult = getUsersByTeamUseCase.execute(teamId);

        memberViewState.addSource(getMembersResult, listResource -> {
            if (listResource == null) return;
            switch (listResource.getStatus()) {
                case SUCCESS:
                    memberViewState.setValue(MemberViewState.success(listResource.getData()));
                    memberViewState.removeSource(getMembersResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(listResource.getMessage()));
                    memberViewState.removeSource(getMembersResult);
                    break;
            }
        });
    }

    /**
     * Obtiene la lista de miembros del equipo actual.
     */
    public void getTeamMembers() {
        LiveData<Resource<List<UserModel>>> getMembersResult = getUsersByTeamUseCase.execute(teamId);
        memberViewState.addSource(getMembersResult, listResource -> {
            if (listResource == null) return;
            switch (listResource.getStatus()) {
                case SUCCESS:
                    memberViewState.setValue(MemberViewState.success(listResource.getData()));
                    memberViewState.removeSource(getMembersResult);
                    break;
                case ERROR:
                    memberViewState.setValue(MemberViewState.error(listResource.getMessage()));
            }
        });
    }

    /**
     * Valida un correo electrónico y en caso de ser válido inicia la búsqueda y adición del usuario correspondiente.
     *
     * @param email Correo electrónico a validar.
     */
    public void validateEmail(String email) {
        boolean isEmailValid = isValidEmail(email);
        memberViewState.setValue(MemberViewState.validating(isEmailValid));

        if (isEmailValid) {
            memberViewState.setValue(MemberViewState.loading());
            getUserByEmail(email);
        }
    }

    /**
     * Comprueba si un correo electrónico tiene un formato válido.
     *
     * @param email Correo electrónico a validar.
     * @return true si el correo es válido, false en caso contrario.
     */
    private boolean isValidEmail(String email) {
        return !email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
