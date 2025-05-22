package com.isaac.ggmanager.domain.usecase.auth;


import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Use case para verificar si el usuario está ya autenticado. Inyecta una dependencia de la implementación
 * del repositorio de FirebaseAuth.
 */
public class CheckAuthenticatedUserUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public CheckAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public boolean execute(){
        return firebaseAuthRepository.isUserAuthenticated();
    }
}
