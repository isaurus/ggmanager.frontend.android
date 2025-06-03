package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

public class UpdateTeamUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public UpdateTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<Boolean>> execute(TeamModel teamModel){
        return teamRepository.update(teamModel);
    }
}
