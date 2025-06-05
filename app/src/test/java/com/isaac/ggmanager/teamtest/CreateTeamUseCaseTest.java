package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.CreateTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class CreateTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private CreateTeamUseCase createTeamUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        createTeamUseCase = new CreateTeamUseCase(teamRepository);
    }

    @Test
    public void execute_shouldCreateTeamSuccessfully() {
        // Arrange
        TeamModel team = new TeamModel();
        team.setTeamName("Test Team");
        team.setTeamDescription("Test Team Description");

        String expectedTeamId = "team123";
        MutableLiveData<Resource<String>> expectedLiveData = new MutableLiveData<>();
        expectedLiveData.setValue(Resource.success(expectedTeamId));

        when(teamRepository.createTeam(team)).thenReturn(expectedLiveData);

        // Act
        LiveData<Resource<String>> result = createTeamUseCase.execute(team);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(expectedTeamId, result.getValue().getData());
    }
}
