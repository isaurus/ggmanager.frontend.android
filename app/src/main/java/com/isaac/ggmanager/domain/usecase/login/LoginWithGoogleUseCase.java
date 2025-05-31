package com.isaac.ggmanager.domain.usecase.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

public class LoginWithGoogleUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public LoginWithGoogleUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    public LiveData<Resource<Boolean>> execute(String tokenId) {
        MediatorLiveData<Resource<Boolean>> result = new MediatorLiveData<>();

        LiveData<Resource<Boolean>> source = firebaseAuthRepository.loginWithGoogle(tokenId);

        result.addSource(source, resource -> {
            if (resource == null) {
                result.setValue(Resource.error("Recurso nulo"));
                result.removeSource(source);
                return;
            }

            switch (resource.getStatus()) {
                case SUCCESS:
                    String userId = firebaseAuthRepository.getAuthenticatedUser().getUid();

                    result.setValue(Resource.success(true));
                    break;
                case ERROR:
                    result.setValue(Resource.error(resource.getMessage()));
                    break;
                case LOADING:
                    result.setValue(Resource.loading());
                    break;
            }

            result.removeSource(source);
        });

        return result;
    }
}
