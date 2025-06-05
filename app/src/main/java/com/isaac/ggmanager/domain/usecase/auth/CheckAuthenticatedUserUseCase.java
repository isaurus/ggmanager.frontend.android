package com.isaac.ggmanager.domain.usecase.auth;

import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Caso de uso para verificar si un usuario ya está autenticado en Firebase.
 *
 * Esta verificación se utiliza para determinar si, al abrir la aplicación,
 * el usuario debe ser dirigido al LoginActivity (si no está autenticado)
 * o directamente al HomeActivity (si ya está autenticado).
 */
public class CheckAuthenticatedUserUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    /**
     * Constructor que inyecta el repositorio de autenticación Firebase.
     *
     * @param firebaseAuthRepository Repositorio encargado de la gestión de autenticación.
     */
    @Inject
    public CheckAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    /**
     * Ejecuta la comprobación del estado de autenticación del usuario actual.
     *
     * @return true si el usuario está autenticado, false en caso contrario.
     */
    public boolean execute(){
        return firebaseAuthRepository.isUserAuthenticated();
    }
}
