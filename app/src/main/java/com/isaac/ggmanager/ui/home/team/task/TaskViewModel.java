package com.isaac.ggmanager.ui.home.team.task;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.usecase.auth.GetAuthenticatedUserUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.task.DeleteTaskByIdUseCase;
import com.isaac.ggmanager.domain.usecase.home.team.task.GetUserTasksUseCase;
import com.isaac.ggmanager.domain.usecase.home.user.GetCurrentUserUseCase;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

/**
 * ViewModel encargado de gestionar la lógica y datos relacionados con las tareas del usuario.
 * <p>
 * Obtiene las tareas asignadas al usuario autenticado, actualiza el estado de las tareas,
 * y permite marcar tareas como completadas.
 * </p>
 */
@HiltViewModel
public class TaskViewModel extends ViewModel {

    private final GetAuthenticatedUserUseCase getAuthenticatedUserUseCase;
    private final GetCurrentUserUseCase getCurrentUserUseCase;
    private final DeleteTaskByIdUseCase deleteTaskByIdUseCase;
    private final GetUserTasksUseCase getUserTasksUseCase;

    private final MediatorLiveData<TaskViewState> taskViewState = new MediatorLiveData<>();

    private List<String> tasksAssigned;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param getAuthenticatedUserUseCase Caso de uso para obtener usuario autenticado.
     * @param getCurrentUserUseCase       Caso de uso para obtener datos del usuario actual.
     * @param deleteTaskByIdUseCase       Caso de uso para eliminar (completar) una tarea por su ID.
     * @param getUserTasksUseCase         Caso de uso para obtener tareas asignadas a un usuario.
     */
    @Inject
    public TaskViewModel(GetAuthenticatedUserUseCase getAuthenticatedUserUseCase,
                         GetCurrentUserUseCase getCurrentUserUseCase,
                         DeleteTaskByIdUseCase deleteTaskByIdUseCase,
                         GetUserTasksUseCase getUserTasksUseCase){
        this.getAuthenticatedUserUseCase = getAuthenticatedUserUseCase;
        this.getCurrentUserUseCase = getCurrentUserUseCase;
        this.deleteTaskByIdUseCase = deleteTaskByIdUseCase;
        this.getUserTasksUseCase = getUserTasksUseCase;
    }

    /**
     * Devuelve un LiveData observable con el estado actual de la vista de tareas.
     *
     * @return LiveData con TaskViewState.
     */
    public LiveData<TaskViewState> getTaskViewState() {
        return taskViewState;
    }

    /**
     * Obtiene el usuario actualmente autenticado y carga sus tareas asignadas.
     */
    public void getCurrentUser(){
        String currentUserId = getAuthenticatedUserUseCase.execute().getUid();
        LiveData<Resource<UserModel>> getCurrentUserResult = getCurrentUserUseCase.execute(currentUserId);
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

    /**
     * Obtiene las tareas asignadas a un usuario dado.
     *
     * @param tasksAssigned Lista de IDs de tareas asignadas.
     */
    private void getTasksAssignedToUser(List<String> tasksAssigned){
        LiveData<Resource<List<TaskModel>>> getTasksAssignedToUserResult = getUserTasksUseCase.execute(tasksAssigned);

        taskViewState.addSource(getTasksAssignedToUserResult, taskModelResource -> {
            if (taskModelResource == null) return;
            switch (taskModelResource.getStatus()){
                case SUCCESS:
                    List<TaskModel> userTasksList = taskModelResource.getData();
                    taskViewState.setValue(TaskViewState.success(userTasksList));
                    taskViewState.removeSource(getTasksAssignedToUserResult);
                    break;
                case ERROR:
                    taskViewState.setValue(TaskViewState.error(taskModelResource.getMessage()));
                    taskViewState.removeSource(getTasksAssignedToUserResult);
                    break;
            }
        });
    }

    /**
     * Marca una tarea como completada eliminándola de la lista de tareas asignadas.
     *
     * @param task Tarea a marcar como completada.
     */
    public void markTaskAsCompleted(TaskModel task){
        String taskId = task.getId();
        LiveData<Resource<Boolean>> deleteTaskResult = deleteTaskByIdUseCase.execute(taskId);
        taskViewState.setValue(TaskViewState.loading());

        taskViewState.addSource(deleteTaskResult, booleanResource -> {
            if (booleanResource == null) return;
            switch (booleanResource.getStatus()){
                case SUCCESS:
                    // Refresca la lista de tareas tras marcar como completada
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

}
