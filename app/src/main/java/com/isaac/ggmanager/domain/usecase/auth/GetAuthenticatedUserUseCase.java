package com.isaac.ggmanager.domain.usecase.auth;


import com.google.firebase.auth.FirebaseUser;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Use case para obtener el usuario autenticado de Firebase y mapearlo posteriormente a modelo de
 * dominio. Necesario para obtener el firebaseUid del usuario de Firebase Auth.
 */
public class GetAuthenticatedUserUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public GetAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public FirebaseUser execute(){
        return firebaseAuthRepository.getAuthenticatedUser();
    }
}
