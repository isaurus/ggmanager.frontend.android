package com.isaac.ggmanager.tasktest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;
import com.isaac.ggmanager.domain.usecase.home.team.task.DeleteTaskByIdUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class DeleteTaskByIdUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TaskRepository taskRepository;

    private DeleteTaskByIdUseCase deleteTaskByIdUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        deleteTaskByIdUseCase = new DeleteTaskByIdUseCase(taskRepository);
    }

    @Test
    public void execute_shouldReturnTrueOnSuccess() {
        // Arrange
        String taskId = "123";

        MutableLiveData<Resource<Boolean>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(true));

        when(taskRepository.deleteById(taskId)).thenReturn(liveData);

        // Act
        LiveData<Resource<Boolean>> result = deleteTaskByIdUseCase.execute(taskId);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(true, result.getValue().getData());
    }
}
