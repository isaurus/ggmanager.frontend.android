package com.isaac.ggmanager.usertest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;
import com.isaac.ggmanager.domain.usecase.home.user.UpdateUserTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UpdateUserTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private UserRepository userRepository;

    private UpdateUserTeamUseCase updateUserTeamUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        updateUserTeamUseCase = new UpdateUserTeamUseCase(userRepository);
    }

    @Test
    public void execute_shouldUpdateUserTeamAsMember() {
        // Arrange
        String userId = "user123";
        String teamId = "team456";
        String expectedRole = "Member";

        MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(true));

        when(userRepository.updateUserTeam(userId, teamId, expectedRole)).thenReturn(liveData);

        // Act
        LiveData<Resource<Boolean>> result = updateUserTeamUseCase.execute(userId, teamId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}
