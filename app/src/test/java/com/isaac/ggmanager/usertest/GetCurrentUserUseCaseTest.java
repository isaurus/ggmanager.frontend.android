package com.isaac.ggmanager.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetCurrentUserUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserRepository userRepository;

    private GetCurrentUserUseCase getCurrentUserUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getCurrentUserUseCase = new GetCurrentUserUseCase(userRepository);
    }

    @Test
    public void execute_shouldReturnUserById() {
        // Arrange
        String userId = "uid123";
        UserModel expectedUser = new UserModel(userId, "test@example.com");

        MutableLiveData<Resource<UserModel>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(expectedUser));

        when(userRepository.getById(userId)).thenReturn(liveData);

        // Act
        LiveData<Resource<UserModel>> result = getCurrentUserUseCase.execute(userId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(expectedUser.getFirebaseUid(), result.getValue().getData().getFirebaseUid());
        assertEquals(expectedUser.getEmail(), result.getValue().getData().getEmail());
    }
}
