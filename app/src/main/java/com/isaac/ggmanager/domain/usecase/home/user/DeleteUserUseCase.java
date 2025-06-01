package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class DeleteUserUseCase {

    private final UserRepository userRepository;

    @Inject
    public DeleteUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Boolean>> execute(String userId){
        return userRepository.delete(userId);
    }
}
