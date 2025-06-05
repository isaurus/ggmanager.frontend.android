package com.isaac.ggmanager.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.SignOutUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel para la pantalla principal (Home) de la aplicación.
 * <p>
 * Gestiona la lógica relacionada con la obtención del usuario autenticado,
 * la comprobación del equipo asociado al usuario y la gestión del cierre de sesión.
 * </p>
 */
@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final SignOutUseCase signoutUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;

    private final MediatorLiveData<HomeViewState> homeViewState = new MediatorLiveData<>();

    /**
     * Constructor inyectado por Hilt con los casos de uso necesarios.
     *
     * @param signoutUseCase Caso de uso para cerrar sesión.
     * @param getCurrentUserUseCase Caso de uso para obtener el usuario actual.
     * @param getAuthenticatedUserUseCase Caso de uso para obtener el usuario autenticado.
     */
    @Inject
    public HomeViewModel(SignOutUseCase signoutUseCase,
                         GetCurrentUserUseCase getCurrentUserUseCase,
                         GetAuthenticatedUserUseCase getAuthenticatedUserUseCase) {
        this.signoutUseCase = signoutUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
    }

    /**
     * Obtiene el estado observable de la vista (HomeViewState).
     *
     * @return LiveData con el estado actual de la vista.
     */
    public LiveData<HomeViewState> getHomeViewState() {
        return homeViewState;
    }

    /**
     * Solicita al repositorio la información del equipo asociado al usuario autenticado.
     * Actualiza el estado de la vista según el resultado:
     * - Si el usuario tiene equipo, emite userHasTeam().
     * - Si el usuario no tiene equipo, emite userHasNoTeam().
     * - En caso de error, emite error().
     * - Mientras carga, emite loading().
     */
    public void getUserTeam() {
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> userResult = getCurrentUserUseCase.execute(currentUserId);
        homeViewState.setValue(HomeViewState.loading());

        homeViewState.addSource(userResult, resource -> {
            if (resource == null) return;
            switch (resource.getStatus()) {
                case SUCCESS:
                    UserModel user = resource.getData();
                    if (user.getTeamId() != null && !user.getTeamId().isEmpty()) {
                        homeViewState.setValue(HomeViewState.userHasTeam());
                    } else {
                        homeViewState.setValue(HomeViewState.userHasNoTeam());
                    }
                    homeViewState.removeSource(userResult);
                    break;
                case ERROR:
                    homeViewState.setValue(HomeViewState.error(resource.getMessage()));
                    homeViewState.removeSource(userResult);
                    break;
                case LOADING:
                    homeViewState.setValue(HomeViewState.loading());
                    break;
            }
        });
    }

    /**
     * Ejecuta el cierre de sesión del usuario.
     */
    public void signOut() {
        signoutUseCase.execute();
    }
}
