package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class UpdateUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public UpdateUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Boolean>> execute(UserModel userModel){
        return userRepository.update(userModel);
    }
}
