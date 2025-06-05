package com.isaac.ggmanager.domain.model;

import java.util.List;

/**
 * Modelo de dominio que representa un equipo dentro de la aplicación.
 * Contiene información sobre el nombre, descripción, miembros y tareas asociadas al equipo.
 *
 * <p>Esta clase facilita la gestión y representación de equipos, sus integrantes
 * y las tareas vinculadas a dichos equipos.</p>
 */
public class TeamModel {

    /**
     * Identificador único del equipo.
     */
    private String id;

    /**
     * Nombre del equipo.
     */
    private String teamName;

    /**
     * Descripción o información adicional sobre el equipo.
     */
    private String teamDescription;

    /**
     * Lista de identificadores de los miembros que pertenecen al equipo.
     */
    private List<String> members;

    /**
     * Lista de identificadores de las tareas asociadas al equipo.
     */
    private List<String> teamTasksId;

    /**
     * Constructor vacío necesario para frameworks y serialización.
     */
    public TeamModel() {}

    /**
     * Constructor para crear un equipo con sus atributos básicos.
     *
     * @param id Identificador único del equipo.
     * @param teamName Nombre del equipo.
     * @param teamDescription Descripción del equipo.
     * @param members Lista de IDs de los miembros del equipo.
     */
    public TeamModel(String id, String teamName, String teamDescription, List<String> members) {
        this.id = id;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.members = members;
    }

    /**
     * Obtiene el identificador único del equipo.
     *
     * @return ID del equipo.
     */
    public String getId() {
        return id;
    }

    /**
     * Establece el identificador único del equipo.
     *
     * @param id ID del equipo.
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del equipo.
     *
     * @return Nombre del equipo.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Establece el nombre del equipo.
     *
     * @param teamName Nombre del equipo.
     */
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Obtiene la descripción o información adicional del equipo.
     *
     * @return Descripción del equipo.
     */
    public String getTeamDescription() {
        return teamDescription;
    }

    /**
     * Establece la descripción o información adicional del equipo.
     *
     * @param teamDescription Descripción del equipo.
     */
    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
    }

    /**
     * Obtiene la lista de identificadores de los miembros que forman parte del equipo.
     *
     * @return Lista de IDs de miembros.
     */
    public List<String> getMembers() {
        return members;
    }

    /**
     * Establece la lista de identificadores de los miembros que forman parte del equipo.
     *
     * @param members Lista de IDs de miembros.
     */
    public void setMembers(List<String> members) {
        this.members = members;
    }

    /**
     * Obtiene la lista de identificadores de las tareas asociadas al equipo.
     *
     * @return Lista de IDs de tareas.
     */
    public List<String> getTeamTasksId() {
        return teamTasksId;
    }

    /**
     * Establece la lista de identificadores de las tareas asociadas al equipo.
     *
     * @param teamTasksId Lista de IDs de tareas.
     */
    public void setTeamTasksId(List<String> teamTasksId) {
        this.teamTasksId = teamTasksId;
    }
}
