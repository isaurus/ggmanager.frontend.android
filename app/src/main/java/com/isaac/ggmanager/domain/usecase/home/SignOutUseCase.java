package com.isaac.ggmanager.domain.usecase.home;

import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

public class SignOutUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject

    public SignOutUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public void execute(){
        firebaseAuthRepository.signOut();
    }
}
