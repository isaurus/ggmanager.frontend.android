package com.isaac.ggmanager.domain.usecase.home;

import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Caso de uso para cerrar la sesión del usuario actual.
 * Este caso de uso delega la operación de cierre de sesión en el repositorio de autenticación.
 */
public class SignOutUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de autenticación Firebase.
     *
     * @param firebaseAuthRepository Repositorio encargado de la autenticación con Firebase.
     */
    @Inject
    public SignOutUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    /**
     * Ejecuta el cierre de sesión del usuario actualmente autenticado.
     */
    public void execute(){
        firebaseAuthRepository.signOut();
    }
}
