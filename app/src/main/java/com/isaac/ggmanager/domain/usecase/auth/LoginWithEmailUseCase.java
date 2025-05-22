package com.isaac.ggmanager.domain.usecase.auth;

import androidx.lifecycle.LiveData;


import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;

import javax.inject.Inject;

public class LoginWithEmailUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public LoginWithEmailUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public LiveData<Resource<Boolean>> execute(String email, String password){
        return firebaseAuthRepository.loginWithEmail(email, password);
    }
}
