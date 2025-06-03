package com.isaac.ggmanager.domain.usecase.home.team.task;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;

import javax.inject.Inject;

public class DeleteTaskByIdUseCase {

    private final TaskRepository taskRepository;

    @Inject
    public DeleteTaskByIdUseCase(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    public LiveData<Resource<Boolean>> execute(String taskId){
        return taskRepository.deleteById(taskId);
    }
}
