package com.isaac.ggmanager.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.GetUsersByTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class GetUsersByTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private UserRepository userRepository;

    private GetUsersByTeamUseCase getUsersByTeamUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getUsersByTeamUseCase = new GetUsersByTeamUseCase(userRepository);
    }

    @Test
    public void execute_shouldReturnListOfUsers() {
        // Arrange
        String teamId = "team123";
        UserModel user1 = new UserModel();
        user1.setFirebaseUid("u1");
        user1.setName("Isaac");

        UserModel user2 = new UserModel();
        user2.setFirebaseUid("u2");
        user2.setName("Alex");

        List<UserModel> expectedUsers = Arrays.asList(user1, user2);

        MutableLiveData<Resource<List<UserModel>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(expectedUsers));

        when(userRepository.getUsersByTeam(teamId)).thenReturn(liveData);

        // Act
        LiveData<Resource<List<UserModel>>> result = getUsersByTeamUseCase.execute(teamId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(2, result.getValue().getData().size());
        assertEquals("Isaac", result.getValue().getData().get(0).getName());
    }
}
