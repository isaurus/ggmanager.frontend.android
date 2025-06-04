package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.GetAllTeamsUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class GetAllTeamsUseCaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private GetAllTeamsUseCase getAllTeamsUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getAllTeamsUseCase = new GetAllTeamsUseCase(teamRepository);
    }

    @Test
    public void execute_shouldReturnListOfTeams() {
        // Arrange
        TeamModel team1 = new TeamModel();
        team1.setId("team1");
        TeamModel team2 = new TeamModel();
        team2.setId("team2");

        List<TeamModel> mockList = Arrays.asList(team1, team2);
        MutableLiveData<Resource<List<TeamModel>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(mockList));

        when(teamRepository.getAll()).thenReturn(liveData);

        // Act
        LiveData<Resource<List<TeamModel>>> result = getAllTeamsUseCase.execute();

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(2, result.getValue().getData().size());
        assertEquals("team1", result.getValue().getData().get(0).getId());
    }
}
