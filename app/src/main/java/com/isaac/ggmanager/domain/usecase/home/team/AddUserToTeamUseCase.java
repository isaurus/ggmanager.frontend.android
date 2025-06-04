package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class AddUserToTeamUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public AddUserToTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId, String userToAddId){
        return teamRepository.addUserToTeam(teamId, userToAddId);
    }
}
