package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

public class CreateTeamUseCase {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;
    private final FirebaseAuthRepository authRepository;

    @Inject
    public CreateTeamUseCase(TeamRepository teamRepository,
                             UserRepository userRepository,
                             FirebaseAuthRepository authRepository){
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public LiveData<Resource<String>> execute(TeamModel teamModel){

        String userId = authRepository.getAuthenticatedUser().getUid();
        teamModel.setMembers(new ArrayList<>(Arrays.asList(userId)));

        return teamRepository.createTeam(teamModel);
    }
}
