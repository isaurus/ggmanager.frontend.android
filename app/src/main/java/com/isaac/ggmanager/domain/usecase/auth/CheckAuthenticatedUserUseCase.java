package com.isaac.ggmanager.domain.usecase.auth;


import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Use case para verificar si el usuario está ya autenticado. Devuelve true en caso afirmativo,
 * false en caso contrario. Se utiliza para que, tras abrir la aplicación, el usuario sea llevado
 * al LoginActivity o al HomeActivity
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
