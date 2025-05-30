package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

public class IsUserHasTeamUseCase {

    private final UserRepository userRepository;

    @Inject
    public IsUserHasTeamUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public LiveData<Resource<Boolean>> execute(String userId){
        MediatorLiveData<Resource<Boolean>> result = new MediatorLiveData<>();

        LiveData<Resource<UserModel>> source = userRepository.getById(userId);

        result.addSource(source, resource -> {
            if (resource == null){
                result.setValue(Resource.error("Recurso nulo"));
                result.removeSource(source);
            }

            switch (resource.getStatus()){
                case SUCCESS:
                    UserModel user = resource.getData();
                    boolean hasTeam = user != null && user.getTeamId() != null && !user.getTeamId().isEmpty();
                    result.setValue(Resource.success(hasTeam));
                    break;
                case ERROR:
                    result.setValue(Resource.error(resource.getMessage()));
                    break;
                case LOADING:
                    result.setValue(Resource.loading());
                    break;
            }
        });

        return result;
    }
}
