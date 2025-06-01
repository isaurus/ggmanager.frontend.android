package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class GetCurrentUserUseCase {

    private final UserRepository userRepository;
    private final FirebaseAuthRepository authRepository;

    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository,
                                 FirebaseAuthRepository authRepository){
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public LiveData<Resource<UserModel>> execute(){

        String userId = authRepository.getAuthenticatedUser().getUid();

        return userRepository.getById(userId);
    }

}
