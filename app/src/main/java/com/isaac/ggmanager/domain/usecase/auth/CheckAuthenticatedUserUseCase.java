package com.isaac.ggmanager.domain.usecase.auth;


import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Use case para verificar si el usuario está ya autenticado. Devuelve true en caso afirmativo,
 * false en caso contrario.
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
