package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class GetUsersByTeamUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUsersByTeamUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LiveData<Resource<List<UserModel>>> execute(String teamId){
        return userRepository.getUsersByTeam(teamId);
    }
}
