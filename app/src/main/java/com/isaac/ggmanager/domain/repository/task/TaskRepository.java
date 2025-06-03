package com.isaac.ggmanager.domain.repository.task;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.repository.base.FirestoreRepository;

import java.util.List;

public interface TaskRepository extends FirestoreRepository<TaskModel> {

    LiveData<Resource<List<TaskModel>>> getUserTasks(List<String> tasksListId);
}
