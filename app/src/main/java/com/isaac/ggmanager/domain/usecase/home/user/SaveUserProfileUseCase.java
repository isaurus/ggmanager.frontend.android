package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;

import javax.inject.Inject;

public class SaveUserProfileUseCase {

    private final FirestoreUserRepository firestoreUserRepository;

    @Inject
    public SaveUserProfileUseCase(FirestoreUserRepository firestoreUserRepository){
        this.firestoreUserRepository = firestoreUserRepository;
    }

    public LiveData<Resource<Boolean>> execute (UserModel userModel){
        return firestoreUserRepository.saveUserProfile(userModel);
    }
}
