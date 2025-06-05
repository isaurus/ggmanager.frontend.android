package com.isaac.ggmanager.domain.model;

import java.util.Date;
import java.util.List;

/**
 * Modelo de dominio que representa un usuario dentro de la aplicación.
 * Contiene información básica y avanzada del usuario, así como su
 * relación con equipos y tareas dentro de la plataforma.
 */
public class UserModel {

    /**
     * Identificador único del usuario proporcionado por Firebase Authentication.
     */
    private String firebaseUid;

    /**
     * Clave o identificador del avatar seleccionado por el usuario.
     */
    private String avatar;

    /**
     * Nombre completo o apodo del usuario.
     */
    private String name;

    /**
     * Correo electrónico del usuario utilizado para autenticación.
     */
    private String email;

    /**
     * Fecha de nacimiento del usuario.
     */
    private Date birthdate;

    /**
     * País de residencia o procedencia del usuario.
     */
    private String country;

    /**
     * Identificador del equipo al que pertenece el usuario.
     */
    private String teamId;

    /**
     * Rol que desempeña el usuario dentro del equipo (por ejemplo, administrador o miembro).
     */
    private String teamRole;

    /**
     * Lista de identificadores de las tareas asignadas al usuario dentro de su equipo.
     */
    private List<String> teamTasksId;

    /**
     * Constructor vacío requerido para la deserialización automática por Firestore u otros frameworks.
     */
    public UserModel(){}

    /**
     * Constructor para crear un usuario básico al registrarse o iniciar sesión
     * por primera vez en la aplicación, con valores por defecto para garantizar
     * consistencia en la base de datos Firestore.
     *
     * @param firebaseUid El UID único asignado por Firebase Authentication.
     * @param email Correo electrónico con el que el usuario se autentica.
     */
    public UserModel(String firebaseUid, String email){
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.avatar = "ic_avatar_avocado";
        this.name = "Por definir";
        this.birthdate = null;
        this.country = "Por definir";
        this.teamRole = null;
        this.teamTasksId = null;
    }

    /**
     * Constructor completo para instanciar o actualizar todos los detalles del usuario,
     * incluyendo información de perfil y su vinculación con equipos y tareas.
     *
     * @param firebaseUid Identificador único de Firebase Authentication.
     * @param email Correo electrónico del usuario.
     * @param avatar Clave del avatar seleccionado.
     * @param name Nombre completo o apodo.
     * @param birthdate Fecha de nacimiento.
     * @param country País de residencia o procedencia.
     * @param teamId Identificador del equipo asociado.
     * @param teamRole Rol del usuario dentro del equipo.
     * @param teamTasksId Lista de IDs de tareas asignadas al usuario.
     */
    public UserModel(String firebaseUid, String email, String avatar, String name, Date birthdate, String country, String teamId, String teamRole, List<String> teamTasksId) {
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.avatar = avatar;
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
        this.teamId = teamId;
        this.teamRole = teamRole;
        this.teamTasksId = teamTasksId;
    }

    /**
     * Obtiene el UID único asignado por Firebase Authentication.
     *
     * @return El UID de Firebase.
     */
    public String getFirebaseUid() {
        return firebaseUid;
    }

    /**
     * Establece el UID único asignado por Firebase Authentication.
     *
     * @param firebaseUid El UID de Firebase.
     */
    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    /**
     * Obtiene la clave del avatar seleccionado por el usuario.
     *
     * @return La clave del avatar.
     */
    public String getAvatar() {
        return avatar;
    }

    /**
     * Establece la clave del avatar seleccionado por el usuario.
     *
     * @param avatar La clave del avatar.
     */
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    /**
     * Obtiene el nombre o apodo del usuario.
     *
     * @return Nombre del usuario.
     */
    public String getName() {
        return name;
    }

    /**
     * Establece el nombre o apodo del usuario.
     *
     * @param name Nombre del usuario.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Obtiene el correo electrónico del usuario.
     *
     * @return Correo electrónico.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Establece el correo electrónico del usuario.
     *
     * @param email Correo electrónico.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtiene la fecha de nacimiento del usuario.
     *
     * @return Fecha de nacimiento.
     */
    public Date getBirthdate() {
        return birthdate;
    }

    /**
     * Establece la fecha de nacimiento del usuario.
     *
     * @param birthdate Fecha de nacimiento.
     */
    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    /**
     * Obtiene el país de residencia o procedencia del usuario.
     *
     * @return País del usuario.
     */
    public String getCountry() {
        return country;
    }

    /**
     * Establece el país de residencia o procedencia del usuario.
     *
     * @param country País del usuario.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * Obtiene el identificador del equipo al que pertenece el usuario.
     *
     * @return ID del equipo.
     */
    public String getTeamId() {
        return teamId;
    }

    /**
     * Establece el identificador del equipo al que pertenece el usuario.
     *
     * @param teamId ID del equipo.
     */
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    /**
     * Obtiene el rol del usuario dentro del equipo (e.g., administrador o miembro).
     *
     * @return Rol del usuario en el equipo.
     */
    public String getTeamRole() {
        return teamRole;
    }

    /**
     * Establece el rol del usuario dentro del equipo.
     *
     * @param teamRole Rol en el equipo.
     */
    public void setTeamRole(String teamRole) {
        this.teamRole = teamRole;
    }

    /**
     * Obtiene la lista de identificadores de tareas asignadas al usuario.
     *
     * @return Lista de IDs de tareas.
     */
    public List<String> getTeamTasksId() {
        return teamTasksId;
    }

    /**
     * Establece la lista de identificadores de tareas asignadas al usuario.
     *
     * @param teamTasksId Lista de IDs de tareas.
     */
    public void setTeamTasksId(List<String> teamTasksId) {
        this.teamTasksId = teamTasksId;
    }
}
