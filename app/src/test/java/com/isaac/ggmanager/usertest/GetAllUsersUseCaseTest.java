package com.isaac.ggmanager.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.GetAllUsersUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class GetAllUsersUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserRepository userRepository;

    private GetAllUsersUseCase getAllUsersUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getAllUsersUseCase = new GetAllUsersUseCase(userRepository);
    }

    @Test
    public void execute_shouldReturnSuccessWithUserList() {
        // Arrange
        List<UserModel> users = Arrays.asList(
                new UserModel("uid1", "email1@example.com"),
                new UserModel("uid2", "email2@example.com")
        );

        MutableLiveData<Resource<List<UserModel>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(users));

        when(userRepository.getAll()).thenReturn(liveData);

        // Act
        LiveData<Resource<List<UserModel>>> result = getAllUsersUseCase.execute();

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(2, result.getValue().getData().size());
        assertEquals("uid1", result.getValue().getData().get(0).getFirebaseUid());
    }
}
