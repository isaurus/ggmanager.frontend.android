package com.isaac.ggmanager.ui.home.team.task;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;

import java.util.List;

public class TaskViewState {
    private final Resource<?> resource;
    private final List<TaskModel> taskList;

    public TaskViewState(Resource<?> resource, List<TaskModel> taskList){
        this.resource = resource;
        this.taskList = taskList;
    }

    public static TaskViewState loading(){
        return new TaskViewState(Resource.loading(), null);
    }

    public static TaskViewState success(List<TaskModel> taskList){
        return new TaskViewState(Resource.success(taskList), taskList);
    }

    public static TaskViewState error(String message){
        return new TaskViewState(Resource.error(message), null);
    }

    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    public List<TaskModel> getTaskList() {
        return taskList;
    }
}
