package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class UpdateUserTeamUseCase {

    private final UserRepository userRepository;
    private final FirebaseAuthRepository authRepository;

    @Inject
    public UpdateUserTeamUseCase(UserRepository userRepository,
                                 FirebaseAuthRepository authRepository){
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId){
        String userId = authRepository.getAuthenticatedUser().getUid();

        return userRepository.updateUserTeam(userId, teamId);
    }
}
