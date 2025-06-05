package com.isaac.ggmanager.ui.home.team.task;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TaskModel;

import java.util.List;

/**
 * Clase que representa el estado de la vista de tareas.
 * <p>
 * Contiene la información sobre el estado actual de la carga de tareas,
 * incluyendo el estado de la operación (cargando, éxito o error),
 * el mensaje de error si lo hay, y la lista de tareas.
 * </p>
 */
public class TaskViewState {

    private final Resource<?> resource;
    private final List<TaskModel> taskList;

    /**
     * Constructor privado que inicializa el estado con un recurso y una lista de tareas.
     *
     * @param resource Recurso que representa el estado de la operación.
     * @param taskList Lista de tareas en el estado actual (puede ser null).
     */
    public TaskViewState(Resource<?> resource, List<TaskModel> taskList){
        this.resource = resource;
        this.taskList = taskList;
    }

    /**
     * Crea un estado que indica que la carga está en progreso.
     *
     * @return TaskViewState en estado de carga.
     */
    public static TaskViewState loading(){
        return new TaskViewState(Resource.loading(), null);
    }

    /**
     * Crea un estado que indica que la carga fue exitosa con la lista de tareas resultante.
     *
     * @param taskList Lista de tareas obtenidas.
     * @return TaskViewState con estado de éxito.
     */
    public static TaskViewState success(List<TaskModel> taskList){
        return new TaskViewState(Resource.success(taskList), taskList);
    }

    /**
     * Crea un estado que indica que ocurrió un error durante la carga.
     *
     * @param message Mensaje descriptivo del error.
     * @return TaskViewState con estado de error.
     */
    public static TaskViewState error(String message){
        return new TaskViewState(Resource.error(message), null);
    }

    /**
     * Obtiene el estado actual de la operación (LOADING, SUCCESS, ERROR).
     *
     * @return Estado de la operación o null si no está definido.
     */
    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    /**
     * Obtiene el mensaje de error si existe, o un mensaje por defecto.
     *
     * @return Mensaje de error o "Error desconocido".
     */
    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    /**
     * Obtiene la lista de tareas asociadas al estado actual.
     *
     * @return Lista de tareas o null si no está disponible.
     */
    public List<TaskModel> getTaskList() {
        return taskList;
    }
}
