package com.isaac.ggmanager.teamtest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;
import com.isaac.ggmanager.domain.usecase.home.team.member.DeleteTeamUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeleteTeamUseCaseTest {

    @Rule
    public InstantTaskExecutorRule rule = new InstantTaskExecutorRule();

    @Mock
    private TeamRepository teamRepository;

    private DeleteTeamUseCase deleteTeamUseCase;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        deleteTeamUseCase = new DeleteTeamUseCase(teamRepository);
    }

    @Test
    public void execute_shouldReturnSuccessTrue_whenTeamDeleted() {
        // Arrange
        String teamId = "team123";
        MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(true));

        when(teamRepository.deleteById(teamId)).thenReturn(liveData);

        // Act
        LiveData<Resource<Boolean>> result = deleteTeamUseCase.execute(teamId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}
