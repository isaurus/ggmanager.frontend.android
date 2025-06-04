package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.RemoveUserFromTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class RemoveUserFromTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private RemoveUserFromTeamUseCase removeUserFromTeamUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        removeUserFromTeamUseCase = new RemoveUserFromTeamUseCase(teamRepository);
    }

    @Test
    public void execute_shouldReturnTrueOnSuccess() {
        // Arrange
        String teamId = "team123";
        String userId = "user456";

        MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(true));

        when(teamRepository.removeUserFromTeam(teamId, userId)).thenReturn(liveData);

        // Act
        LiveData<Resource<Boolean>> result = removeUserFromTeamUseCase.execute(teamId, userId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}