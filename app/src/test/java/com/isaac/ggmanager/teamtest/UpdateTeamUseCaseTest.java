package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.UpdateTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UpdateTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private UpdateTeamUseCase updateTeamUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        updateTeamUseCase = new UpdateTeamUseCase(teamRepository);
    }

    @Test
    public void execute_shouldReturnTrueOnSuccess() {
        // Arrange
        TeamModel teamModel = new TeamModel();
        // Aquí puedes setear atributos necesarios de teamModel si quieres

        MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(true));

        when(teamRepository.update(teamModel)).thenReturn(liveData);

        // Act
        LiveData<Resource<Boolean>> result = updateTeamUseCase.execute(teamModel);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}
