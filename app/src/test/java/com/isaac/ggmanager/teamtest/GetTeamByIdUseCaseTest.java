package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.GetTeamByIdUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class GetTeamByIdUseCaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private GetTeamByIdUseCase getTeamByIdUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        getTeamByIdUseCase = new GetTeamByIdUseCase(teamRepository);
    }

    @Test
    public void execute_shouldReturnTeamById() {
        // Arrange
        String teamId = "team123";
        TeamModel team = new TeamModel();
        team.setId(teamId);

        MutableLiveData<Resource<TeamModel>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(team));

        when(teamRepository.getById(teamId)).thenReturn(liveData);

        // Act
        LiveData<Resource<TeamModel>> result = getTeamByIdUseCase.execute(teamId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(teamId, result.getValue().getData().getId());
    }
}
