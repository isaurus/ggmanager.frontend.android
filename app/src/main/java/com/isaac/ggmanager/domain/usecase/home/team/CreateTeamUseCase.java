package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

public class CreateTeamUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public CreateTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<String>> execute(TeamModel teamModel){
        return teamRepository.createTeam(teamModel);
    }
}
