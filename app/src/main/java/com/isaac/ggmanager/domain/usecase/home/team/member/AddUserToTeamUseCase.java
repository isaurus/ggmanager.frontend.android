package com.isaac.ggmanager.domain.usecase.home.team.member;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class AddUserToTeamUseCase {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Inject
    public AddUserToTeamUseCase(TeamRepository teamRepository,
                                UserRepository userRepository){
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Boolean>> execute(String teamId, String userEmail){
        MediatorLiveData<Resource<Boolean>> result = new MediatorLiveData<>();
        result.setValue(Resource.loading());

        LiveData<Resource<UserModel>> userResult = userRepository.getUserByEmail(userEmail);
        result.addSource(userResult, userModelResource -> {
            if (userModelResource == null) return;
            switch (userModelResource.getStatus()){
                case SUCCESS:
                    UserModel user = userModelResource.getData();
                    if(user != null){
                        String userId = user.getFirebaseUid();
                        teamRepository.addUserToTeam(teamId, userId);
                        result.setValue(Resource.success(true));
                    } else {
                        result.setValue(Resource.error("El usuario no existe"));
                    }
                    result.removeSource(userResult);
                    break;
                case ERROR:
                    result.setValue(Resource.error(userModelResource.getMessage()));
                    break;
            }
        });

        return result;
    }
}
