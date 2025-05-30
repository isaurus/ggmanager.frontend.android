package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.FirestoreTeamRepository;

import javax.inject.Inject;

public class InviteUserToTeamUseCase {

    private final FirestoreTeamRepository firestoreTeamRepository;

    @Inject
    public InviteUserToTeamUseCase(FirestoreTeamRepository firestoreTeamRepository){
        this.firestoreTeamRepository = firestoreTeamRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId, String email){
        return firestoreTeamRepository.inviteUserToTeam(teamId, email);
    }
}
