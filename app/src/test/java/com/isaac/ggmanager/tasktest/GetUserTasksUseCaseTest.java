package com.isaac.ggmanager.tasktest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;
import com.isaac.ggmanager.domain.usecase.home.team.task.GetUserTasksUseCase;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GetUserTasksUseCaseTest {

    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    @Mock
    private TaskRepository taskRepository;

    private GetUserTasksUseCase getUserTasksUseCase;

    @Before
    public void setup() {
        MockitoAnnotations.openMocks(this);
        getUserTasksUseCase = new GetUserTasksUseCase(taskRepository);
    }

    @Test
    public void execute_shouldReturnTaskList() {
        // Arrange
        List<String> taskIds = Arrays.asList("task1", "task2");

        List<TaskModel> taskModels = Arrays.asList(
                new TaskModel(
                        "task1",
                        "Title 1",
                        "Description 1",
                        new Date(1696262400000L), // ejemplo fecha deadline (1 de octubre 2023)
                        "Alta",
                        false,
                        "team1",
                        "member1"
                ),
                new TaskModel(
                        "task2",
                        "Title 2",
                        "Description 2",
                        new Date(1696348800000L), // ejemplo fecha deadline (2 de octubre 2023)
                        "Media",
                        true,
                        "team2",
                        "member2"
                )
        );

        MutableLiveData<Resource<List<TaskModel>>> liveData = new MutableLiveData<>();
        liveData.setValue(Resource.success(taskModels));

        when(taskRepository.getUserTasks(taskIds)).thenReturn(liveData);

        // Act
        LiveData<Resource<List<TaskModel>>> result = getUserTasksUseCase.execute(taskIds);

        // Assert
        assertEquals(Resource.Status.SUCCESS, result.getValue().getStatus());
        assertEquals(2, result.getValue().getData().size());
        assertEquals("Title 1", result.getValue().getData().get(0).getTaskTitle());
        assertEquals("Description 1", result.getValue().getData().get(0).getTaskDescription());
        assertEquals("Alta", result.getValue().getData().get(0).getPriority());
        assertEquals(false, result.getValue().getData().get(0).isCompleted());
        assertEquals("team1", result.getValue().getData().get(0).getTeamId());
        assertEquals("member1", result.getValue().getData().get(0).getMemberId());
    }
}
