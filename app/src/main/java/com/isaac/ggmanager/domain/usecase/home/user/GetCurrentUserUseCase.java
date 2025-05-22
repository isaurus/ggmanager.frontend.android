package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;

import javax.inject.Inject;

public class GetCurrentUserUseCase {

    private final FirestoreUserRepository firestoreUserRepository;

    @Inject
    public GetCurrentUserUseCase(FirestoreUserRepository firestoreUserRepository) {
        this.firestoreUserRepository = firestoreUserRepository;
    }

    public LiveData<Resource<UserModel>> execute(){
        return firestoreUserRepository.getCurrentUser();
    }
}
