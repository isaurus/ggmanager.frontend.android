package com.isaac.ggmanager.domain.usecase.home;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.FirestoreTeamRepository;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;

import javax.inject.Inject;

public class CheckUserHasTeamUseCase {

    private final FirestoreUserRepository firestoreUserRepository;

    @Inject
    public CheckUserHasTeamUseCase(FirestoreUserRepository firestoreUserRepository){
        this.firestoreUserRepository = firestoreUserRepository;
    }

    public LiveData<Resource<Boolean>> execute(){
        return firestoreUserRepository.hasTeam();
    }
}
