package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.FirestoreTeamRepository;

import javax.inject.Inject;

public class CreateTeamUseCase {

    private final FirestoreTeamRepository firestoreTeamRepository;

    @Inject
    public CreateTeamUseCase(FirestoreTeamRepository firestoreTeamRepository){
        this.firestoreTeamRepository = firestoreTeamRepository;
    }

    public LiveData<Resource<String>> execute(TeamModel teamModel){
        return firestoreTeamRepository.createTeam(teamModel);
    }
}
