package com.isaac.ggmanager.domain.model;

import java.util.Date;

/**
 * Modelo de dominio que representa una tarea dentro de un equipo.
 * Contiene información sobre el título, descripción, fecha límite, prioridad,
 * estado de completitud, y referencias al equipo y miembro asignado.
 *
 * <p>Esta clase se utiliza para gestionar las tareas dentro de la aplicación,
 * facilitando su creación, modificación y seguimiento.</p>
 */
public class TaskModel {

    /**
     * Identificador único de la tarea.
     */
    private String id;

    /**
     * Título o nombre de la tarea.
     */
    private String taskTitle;

    /**
     * Descripción detallada de la tarea.
     */
    private String taskDescription;

    /**
     * Fecha límite para completar la tarea.
     */
    private Date taskDeadLine;

    /**
     * Prioridad de la tarea (por ejemplo: alta, media, baja).
     */
    private String priority;

    /**
     * Indica si la tarea ha sido completada o no.
     */
    private boolean isCompleted;

    /**
     * Identificador del equipo al que pertenece la tarea.
     */
    private String teamId;

    /**
     * Identificador del miembro asignado a la tarea.
     */
    private String memberId;

    /**
     * Constructor vacío necesario para ciertos frameworks y serialización.
     */
    public TaskModel() {}

    /**
     * Constructor completo para crear una tarea con todos sus atributos.
     *
     * @param id Identificador único de la tarea.
     * @param taskTitle Título o nombre de la tarea.
     * @param taskDescription Descripción detallada de la tarea.
     * @param taskDeadLine Fecha límite para completar la tarea.
     * @param priority Prioridad de la tarea.
     * @param isCompleted Estado de completitud de la tarea.
     * @param teamId Identificador del equipo al que pertenece la tarea.
     * @param memberId Identificador del miembro asignado a la tarea.
     */
    public TaskModel(String id, String taskTitle, String taskDescription, Date taskDeadLine, String priority, boolean isCompleted, String teamId, String memberId) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDeadLine = taskDeadLine;
        this.priority = priority;
        this.isCompleted = isCompleted;
        this.teamId = teamId;
        this.memberId = memberId;
    }

    /**
     * Obtiene el identificador único de la tarea.
     *
     * @return ID de la tarea.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único de la tarea.
     *
     * @param id ID de la tarea.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el título o nombre de la tarea.
     *
     * @return Título de la tarea.
     */
    public String getTaskTitle() {
        return taskTitle;
    }

    /**
     * Establece el título o nombre de la tarea.
     *
     * @param taskTitle Título de la tarea.
     */
    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    /**
     * Obtiene la descripción detallada de la tarea.
     *
     * @return Descripción de la tarea.
     */
    public String getTaskDescription() {
        return taskDescription;
    }

    /**
     * Establece la descripción detallada de la tarea.
     *
     * @param taskDescription Descripción de la tarea.
     */
    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    /**
     * Obtiene la fecha límite para completar la tarea.
     *
     * @return Fecha límite de la tarea.
     */
    public Date getTaskDeadLine() {
        return taskDeadLine;
    }

    /**
     * Establece la fecha límite para completar la tarea.
     *
     * @param taskDeadLine Fecha límite de la tarea.
     */
    public void setTaskDeadLine(Date taskDeadLine) {
        this.taskDeadLine = taskDeadLine;
    }

    /**
     * Obtiene la prioridad de la tarea.
     *
     * @return Prioridad de la tarea.
     */
    public String getPriority() {
        return priority;
    }

    /**
     * Establece la prioridad de la tarea.
     *
     * @param priority Prioridad de la tarea.
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * Indica si la tarea ha sido completada.
     *
     * @return {@code true} si la tarea está completada, {@code false} en caso contrario.
     */
    public boolean isCompleted() {
        return isCompleted;
    }

    /**
     * Establece el estado de completitud de la tarea.
     *
     * @param completed Estado de completitud.
     */
    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    /**
     * Obtiene el identificador del equipo asociado a la tarea.
     *
     * @return ID del equipo.
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * Establece el identificador del equipo asociado a la tarea.
     *
     * @param teamId ID del equipo.
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     * Obtiene el identificador del miembro asignado a la tarea.
     *
     * @return ID del miembro.
     */
    public String getMemberId() {
        return memberId;
    }

    /**
     * Establece el identificador del miembro asignado a la tarea.
     *
     * @param memberId ID del miembro.
     */
    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
