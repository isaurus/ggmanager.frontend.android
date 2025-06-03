package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import java.util.List;

import javax.inject.Inject;

public class GetAllTeamsUseCase {

    private final TeamRepository teamRepository;

    @Inject
    public GetAllTeamsUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    public LiveData<Resource<List<TeamModel>>> execute(){
        return teamRepository.getAll();
    }
}
