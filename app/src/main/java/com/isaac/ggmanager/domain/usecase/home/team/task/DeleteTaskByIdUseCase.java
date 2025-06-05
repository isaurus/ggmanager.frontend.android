package com.isaac.ggmanager.domain.usecase.home.team.task;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;

import javax.inject.Inject;

/**
 * Caso de uso para eliminar una tarea específica por su identificador.
 * Encapsula la lógica necesaria para borrar una tarea del repositorio de tareas.
 */
public class DeleteTaskByIdUseCase {

    private final TaskRepository taskRepository;

    /**
     * Constructor que inyecta la dependencia del repositorio de tareas.
     *
     * @param taskRepository Repositorio de tareas para realizar la eliminación.
     */
    @Inject
    public DeleteTaskByIdUseCase(TaskRepository taskRepository){
        this.taskRepository = taskRepository;
    }

    /**
     * Ejecuta la operación de eliminación de una tarea dado su ID.
     *
     * @param taskId Identificador de la tarea a eliminar.
     * @return LiveData que emite un Resource con el resultado de la operación
     *         (true si fue exitosa, false en caso contrario).
     */
    public LiveData<Resource<Boolean>> execute(String taskId){
        return taskRepository.deleteById(taskId);
    }
}
