package com.isaac.ggmanager.domain.repository;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;

public interface FirestoreTeamRepository {

    LiveData<Resource<String>> createTeam(TeamModel teamModel);
    LiveData<Resource<Boolean>> inviteUserToTeam(String teamId, String email);
}
