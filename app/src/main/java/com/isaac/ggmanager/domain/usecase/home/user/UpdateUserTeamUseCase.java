package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class UpdateUserTeamUseCase {

    private final UserRepository userRepository;

    @Inject
    public UpdateUserTeamUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Boolean>> execute(String userId, String teamId){
        return userRepository.updateUserTeam(userId, teamId);
    }
}
