package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.AddUserToTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class AddUserToTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private AddUserToTeamUseCase addUserToTeamUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        addUserToTeamUseCase = new AddUserToTeamUseCase(teamRepository);
    }

    @Test
    public void execute_shouldAddUserToTeamSuccessfully() {
        // Arrange
        String teamId = "team123";
        String userId = "user456";

        MutableLiveData<Resource<Boolean>> expectedLiveData = new MutableLiveData<>();
        expectedLiveData.setValue(Resource.success(true));

        when(teamRepository.addUserToTeam(teamId, userId)).thenReturn(expectedLiveData);

        // Act
        LiveData<Resource<Boolean>> result = addUserToTeamUseCase.execute(teamId, userId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}
