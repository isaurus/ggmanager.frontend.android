package com.isaac.ggmanager.ui.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.CheckAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel encargado de gestionar la lógica para la pantalla de lanzamiento (LaunchActivity).
 *
 * <p>Se encarga de verificar si el usuario está autenticado y obtener su perfil para determinar la navegación.</p>
 */
@HiltViewModel
public class LaunchViewModel extends ViewModel {

    private final CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;

    /**
     * LiveData que emite estados de la vista relacionados con el lanzamiento.
     */
    public final MediatorLiveData<LaunchViewState> launchViewState = new MediatorLiveData<>();

    /**
     * Constructor con inyección de dependencias para los casos de uso relacionados con usuario y autenticación.
     *
     * @param checkAuthenticatedUserUseCase Caso de uso para verificar si el usuario está autenticado
     * @param getAuthenticatedUserUseCase Caso de uso para obtener datos básicos del usuario autenticado
     * @param getCurrentUserUseCase Caso de uso para obtener el perfil completo del usuario
     */
    @Inject
    public LaunchViewModel(CheckAuthenticatedUserUseCase checkAuthenticatedUserUseCase,
                           GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                           GetCurrentUserUseCase getCurrentUserUseCase){
        this.checkAuthenticatedUserUseCase = checkAuthenticatedUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
    }

    /**
     * Proporciona el LiveData para observar el estado de la vista.
     *
     * @return LiveData con el estado actual del lanzamiento
     */
    public LiveData<LaunchViewState> getLaunchViewState() {
        return launchViewState;
    }

    /**
     * Obtiene el perfil completo del usuario autenticado para determinar si ya tiene perfil o no.
     * Actualiza el estado de la vista con el resultado.
     */
    public void fetchUserProfile() {
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute(currentUserId);

        launchViewState.addSource(userResult, userModelResource -> {
            if (userModelResource == null) return;
            switch (userModelResource.getStatus()) {
                case SUCCESS:
                    UserModel user = userModelResource.getData();
                    if (user != null) {
                        launchViewState.setValue(LaunchViewState.userHasProfile());
                    } else {
                        launchViewState.setValue(LaunchViewState.userHasNoProfile());
                    }
                    launchViewState.removeSource(userResult);
                    break;
                case ERROR:
                    launchViewState.setValue(LaunchViewState.error(userModelResource.getMessage()));
                    launchViewState.removeSource(userResult);
                    break;
            }
        });
    }

    /**
     * Comprueba si hay un usuario autenticado actualmente.
     *
     * @return true si hay usuario autenticado, false en caso contrario
     */
    public boolean isUserAuthenticated() {
        return checkAuthenticatedUserUseCase.execute();
    }
}
