package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

public class AddUserToTeamUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public AddUserToTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId, String userId){
        return teamRepository.addUserToTeam(teamId, userId);
    }
}
