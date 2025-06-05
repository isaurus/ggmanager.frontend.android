package com.isaac.ggmanager.domain.usecase.home.team.task;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Caso de uso para obtener las tareas asociadas a un usuario.
 *
 * Este caso de uso recibe una lista de identificadores de tareas (`tasksListId`)
 * y solicita al repositorio correspondiente que obtenga los objetos `TaskModel`
 * asociados a esos IDs. Es utilizado, por ejemplo, para mostrar las tareas
 * asignadas a un usuario en la interfaz de usuario.
 */
public class GetUserTasksUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param taskRepository Repositorio que proporciona el acceso a las tareas.
     */
    @Inject
    public GetUserTasksUseCase(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    /**
     * Ejecuta la obtención de tareas a partir de una lista de IDs de tareas.
     *
     * @param tasksListId Lista de identificadores de tareas del usuario.
     * @return Un LiveData que contiene un Resource con la lista de tareas o un error.
     */
    public LiveData<Resource<List<TaskModel>>> execute(List<String> tasksListId){
        return taskRepository.getUserTasks(tasksListId);
    }
}
