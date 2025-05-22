package com.isaac.ggmanager.domain.usecase.auth;

import androidx.lifecycle.LiveData;


import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;

import javax.inject.Inject;

public class LoginWithGoogleUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public LoginWithGoogleUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public LiveData<Resource<Boolean>> execute(String tokenId){
        return firebaseAuthRepository.loginWithGoogle(tokenId);
    }
}
