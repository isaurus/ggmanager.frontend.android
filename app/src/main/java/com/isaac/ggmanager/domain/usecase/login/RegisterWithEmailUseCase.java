package com.isaac.ggmanager.domain.usecase.login;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;

import javax.inject.Inject;

public class RegisterWithEmailUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public RegisterWithEmailUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public LiveData<Resource<Boolean>> execute(String email, String password){
        return firebaseAuthRepository.registerWithEmail(email, password);
    }
}
