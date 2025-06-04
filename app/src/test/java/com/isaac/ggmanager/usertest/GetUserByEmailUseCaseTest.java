package com.isaac.ggmanager.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.GetUserByEmailUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetUserByEmailUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserRepository userRepository;

    private GetUserByEmailUseCase getUserByEmailUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getUserByEmailUseCase = new GetUserByEmailUseCase(userRepository);
    }

    @Test
    public void execute_shouldReturnUserByEmail() {
        // Arrange
        String email = "test@example.com";
        UserModel expectedUser = new UserModel("uid123", email);

        MutableLiveData<Resource<UserModel>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(expectedUser));

        when(userRepository.getUserByEmail(email)).thenReturn(liveData);

        // Act
        LiveData<Resource<UserModel>> result = getUserByEmailUseCase.execute(email);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(expectedUser.getFirebaseUid(), result.getValue().getData().getFirebaseUid());
        assertEquals(expectedUser.getEmail(), result.getValue().getData().getEmail());
    }
}
