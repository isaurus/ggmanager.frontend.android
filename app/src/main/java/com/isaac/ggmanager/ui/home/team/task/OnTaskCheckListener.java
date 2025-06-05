package com.isaac.ggmanager.ui.home.team.task;

import com.isaac.ggmanager.domain.model.TaskModel;

/**
 * Interfaz para escuchar eventos de marcado o desmarcado de una tarea.
 * <p>
 * Esta interfaz debe ser implementada para manejar la acción cuando una tarea es
 * marcada o desmarcada en la interfaz de usuario.
 * </p>
 */
public interface OnTaskCheckListener {

    /**
     * Método llamado cuando se marca o desmarca una tarea.
     *
     * @param task      La tarea que fue marcada o desmarcada.
     * @param isChecked Valor booleano que indica si la tarea fue marcada (true) o desmarcada (false).
     */
    void onTaskChecked(TaskModel task, boolean isChecked);
}
