package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.core.utils.UserPreferencesUtils;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class GetCurrentUserUseCase {

    private final UserRepository userRepository;
    private final UserPreferencesUtils userPreferencesUtils;

    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository, UserPreferencesUtils userPreferencesUtils){
        this.userRepository = userRepository;
        this.userPreferencesUtils = userPreferencesUtils;
    }

    public LiveData<Resource<UserModel>> execute(){

        String userId = userPreferencesUtils.getUserId();

        return userRepository.getById(userId);
    }

}
