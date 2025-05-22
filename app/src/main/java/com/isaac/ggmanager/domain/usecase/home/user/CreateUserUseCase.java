package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;

import javax.inject.Inject;

/**
 * Use case para crear un usuario. Se utiliza la primera vez que el usuario se logea con Google o se
 * registra manualmente.
 */
public class CreateUserUseCase {

    private final FirestoreUserRepository firestoreUserRepository;

    @Inject
    public CreateUserUseCase(FirestoreUserRepository firestoreUserRepository) {
        this.firestoreUserRepository = firestoreUserRepository;
    }

    public LiveData<Resource<Boolean>> execute(UserModel userModel){
        return firestoreUserRepository.createUser(userModel);
    }
}
