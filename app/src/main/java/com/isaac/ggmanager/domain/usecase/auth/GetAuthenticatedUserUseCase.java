package com.isaac.ggmanager.domain.usecase.auth;


import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;

import javax.inject.Inject;

public class GetAuthenticatedUserUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public GetAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public UserModel execute(){
        return firebaseAuthRepository.getAuthenticatedUser();
    }
}
