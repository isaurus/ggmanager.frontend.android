package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

public class RemoveUserFromTeamUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public RemoveUserFromTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId, String userId){
        return teamRepository.removeUserFromTeam(teamId, userId);
    }
}
