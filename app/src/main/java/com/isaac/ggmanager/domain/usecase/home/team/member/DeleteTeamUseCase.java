package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

public class DeleteTeamUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public DeleteTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId){
        return teamRepository.delete(teamId);
    }
}
