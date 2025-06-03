package com.isaac.ggmanager.ui.home.team.task;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.home.team.task.DeleteTaskByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.task.GetUserTasksUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TaskViewModel extends ViewModel {

    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final DeleteTaskByIdUseCase deleteTaskByIdUseCase;
    private final GetUserTasksUseCase getUserTasksUseCase;

    private final MediatorLiveData<TaskViewState> taskViewState = new MediatorLiveData<>();

    private List<String> tasksAssigned;

    @Inject
    public TaskViewModel(GetCurrentUserUseCase getCurrentUserUseCase,
                         DeleteTaskByIdUseCase deleteTaskByIdUseCase,
                         GetUserTasksUseCase getUserTasksUseCase){
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.deleteTaskByIdUseCase = deleteTaskByIdUseCase;
        this.getUserTasksUseCase = getUserTasksUseCase;
    }

    public LiveData<TaskViewState> getTaskViewState() { return taskViewState; }

    public void getCurrentUser(){
        LiveData<Resource<UserModel>> getCurrentUserResult = getCurrentUserUseCase.execute();
        taskViewState.setValue(TaskViewState.loading());

        taskViewState.addSource(getCurrentUserResult, userModelResource -> {
            if (userModelResource == null) return;

            switch (userModelResource.getStatus()){
                case SUCCESS:
                    tasksAssigned = userModelResource.getData().getTeamTasksId();
                    getTasksAssignedToUser(tasksAssigned);
                    taskViewState.removeSource(getCurrentUserResult);
                    break;
                case ERROR:
                    taskViewState.setValue(TaskViewState.error(userModelResource.getMessage()));
                    taskViewState.removeSource(getCurrentUserResult);
                    break;
            }
        });
    }

    private void getTasksAssignedToUser(List<String> tasksAssigned){
        LiveData<Resource<List<TaskModel>>> getTasksAssignedToUserResult = getUserTasksUseCase.execute(tasksAssigned);

        taskViewState.addSource(getTasksAssignedToUserResult, taskModelResource -> {
            if (taskModelResource == null) return;
            switch (taskModelResource.getStatus()){
                case SUCCESS:
                    List<TaskModel> userTasksList = taskModelResource.getData();
                    Log.i("IYO", "La lista en TaskViewModel: " + userTasksList.toString());
                    taskViewState.setValue(TaskViewState.success(userTasksList));
                    taskViewState.removeSource(getTasksAssignedToUserResult);
                    break;
                case ERROR:
                    taskViewState.setValue(TaskViewState.error(taskModelResource.getMessage()));
                    taskViewState.removeSource(getTasksAssignedToUserResult);
            }
        });
    }

    public void markTaskAsCompleted(TaskModel task){
        String taskId = task.getId();
        LiveData<Resource<Boolean>> deleteTaskResult = deleteTaskByIdUseCase.execute(taskId);
        taskViewState.setValue(TaskViewState.loading());

        taskViewState.addSource(deleteTaskResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    getCurrentUser();
                    taskViewState.removeSource(deleteTaskResult);
                    break;
                case ERROR:
                    taskViewState.setValue(TaskViewState.error(booleanResource.getMessage()));
                    taskViewState.removeSource(deleteTaskResult);
                    break;
            }
        });
    }

    public void loadTasksOnStart(){
        LiveData<Resource<List<TaskModel>>> getTasksAssignedToUserResult = getUserTasksUseCase.execute(tasksAssigned);
        taskViewState.setValue(TaskViewState.loading());

        taskViewState.addSource(getTasksAssignedToUserResult, listResource -> {
            if (listResource == null) return;
            switch (listResource.getStatus()){
                case SUCCESS:
                    taskViewState.setValue(TaskViewState.success(listResource.getData()));
                    taskViewState.removeSource(getTasksAssignedToUserResult);
                    break;
                case ERROR:
                    taskViewState.setValue(TaskViewState.error(listResource.getMessage()));
                    taskViewState.removeSource(getTasksAssignedToUserResult);
                    break;
            }
        });
    }

}
