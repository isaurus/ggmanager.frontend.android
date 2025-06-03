package com.isaac.ggmanager.domain.usecase.home.team.task;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.List;

import javax.inject.Inject;

public class GetUserTasksUseCase {

    private final TaskRepository taskRepository;

    @Inject
    public GetUserTasksUseCase(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public LiveData<Resource<List<TaskModel>>> execute(List<String> tasksListId){
        return taskRepository.getUserTasks(tasksListId);
    }
}
