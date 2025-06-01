package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final FirebaseAuthRepository authRepository;

    @Inject
    public CreateUserUseCase(UserRepository userRepository,
                             FirebaseAuthRepository authRepository){
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public LiveData<Resource<Boolean>> execute(UserModel userModel){
        String userId = authRepository.getAuthenticatedUser().getUid();
        String email = authRepository.getAuthenticatedUser().getEmail();

        userModel.setFirebaseUid(userId);
        userModel.setEmail(email);

        return userRepository.create(userModel, userId);
    }
}
