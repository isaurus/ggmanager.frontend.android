package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

public class GetTeamByIdUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public GetTeamByIdUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<TeamModel>> execute(String teamId){
        return teamRepository.getById(teamId);
    }
}
