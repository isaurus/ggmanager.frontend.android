package com.isaac.ggmanager.ui.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel encargado de gestionar la lógica y datos relacionados con
 * el perfil del usuario en la pantalla de perfil.
 * <p>
 * Se encarga de obtener el usuario autenticado y recuperar su perfil
 * desde el repositorio usando casos de uso específicos.
 * Proporciona un LiveData con el estado actual del perfil para ser observado
 * por la vista.
 * </p>
 */
@HiltViewModel
public class UserProfileViewModel extends ViewModel {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    /**
     * MediatorLiveData que emite el estado del perfil del usuario,
     * incluyendo datos, estados de carga y error.
     */
    public final MediatorLiveData<UserProfileViewState> userProfileViewState = new MediatorLiveData<>();

    /**
     * Constructor inyectado con casos de uso para obtener el usuario autenticado
     * y obtener el perfil del usuario actual.
     *
     * @param getCurrentUserUseCase Caso de uso para obtener el usuario por ID.
     * @param getAuthenticatedUserUseCase Caso de uso para obtener el usuario autenticado.
     */
    @Inject
    public UserProfileViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                                GetAuthenticatedUserUseCase getAuthenticatedUserUseCase){
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    /**
     * Devuelve un LiveData observable con el estado del perfil del usuario.
     *
     * @return LiveData con UserProfileViewState que representa el estado de la UI.
     */
    public LiveData<UserProfileViewState> getUserProfileViewState() {
        return userProfileViewState;
    }

    /**
     * Obtiene el perfil del usuario actualmente autenticado.
     * <p>
     * Este método primero recupera el UID del usuario autenticado, luego
     * consulta el perfil del usuario con ese UID. Actualiza el estado
     * observable para reflejar carga, éxito o error.
     * </p>
     */
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
