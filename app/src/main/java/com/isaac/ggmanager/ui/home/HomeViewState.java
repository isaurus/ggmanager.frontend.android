package com.isaac.ggmanager.ui.home;

import com.isaac.ggmanager.core.Resource;

/**
 * Representa el estado de la vista para la pantalla principal (Home).
 * <p>
 * Contiene información sobre el estado de la petición y si el usuario tiene un equipo asignado.
 * </p>
 */
public class HomeViewState {

    private final Resource<?> resource;
    private final boolean userHasTeam;

    /**
     * Constructor privado para crear un estado de la vista.
     *
     * @param resource    Recurso que representa el estado de la operación (éxito, carga, error).
     * @param userHasTeam Indica si el usuario tiene un equipo asignado.
     */
    public HomeViewState(Resource<?> resource, boolean userHasTeam) {
        this.resource = resource;
        this.userHasTeam = userHasTeam;
    }

    /**
     * Estado que indica que el usuario tiene un equipo asignado.
     *
     * @return Instancia de HomeViewState con estado de éxito y usuario con equipo.
     */
    public static HomeViewState userHasTeam() {
        return new HomeViewState(Resource.success(null), true);
    }

    /**
     * Estado que indica que el usuario no tiene equipo asignado.
     *
     * @return Instancia de HomeViewState con estado de éxito y usuario sin equipo.
     */
    public static HomeViewState userHasNoTeam() {
        return new HomeViewState(Resource.success(null), false);
    }

    /**
     * Estado de éxito genérico con datos opcionales y flag de equipo.
     *
     * @param data        Datos asociados al estado de éxito.
     * @param userHasTeam Indica si el usuario tiene equipo.
     * @param <T>         Tipo de los datos.
     * @return Instancia de HomeViewState con estado de éxito.
     */
    public static <T> HomeViewState success(T data, boolean userHasTeam) {
        return new HomeViewState(Resource.success(data), userHasTeam);
    }

    /**
     * Estado que indica que la operación está en curso (cargando).
     *
     * @return Instancia de HomeViewState con estado de carga.
     */
    public static HomeViewState loading() {
        return new HomeViewState(Resource.loading(), false);
    }

    /**
     * Estado que indica que la operación ha fallado con un mensaje de error.
     *
     * @param message Mensaje de error.
     * @return Instancia de HomeViewState con estado de error.
     */
    public static HomeViewState error(String message) {
        return new HomeViewState(Resource.error(message), false);
    }

    /**
     * Obtiene el recurso que representa el estado de la operación.
     *
     * @return Recurso con el estado actual.
     */
    public Resource<?> getResource() {
        return resource;
    }

    /**
     * Obtiene el estado actual (SUCCESS, ERROR, LOADING).
     *
     * @return Estado actual o null si no hay recurso.
     */
    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    /**
     * Obtiene el mensaje de error en caso de existir.
     *
     * @return Mensaje de error o "Error desconocido" si no hay recurso.
     */
    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    /**
     * Obtiene los datos asociados al recurso.
     *
     * @param <T> Tipo esperado de los datos.
     * @return Datos si existen, o null.
     */
    public <T> T getData() {
        return resource != null ? (T) resource.getData() : null;
    }

    /**
     * Indica si el usuario tiene un equipo asignado.
     *
     * @return true si el usuario tiene equipo, false en caso contrario.
     */
    public boolean isUserHasTeam() {
        return userHasTeam;
    }

    /**
     * Indica si el estado actual es de carga.
     *
     * @return true si está cargando, false en caso contrario.
     */
    public boolean isLoading() {
        return resource.getStatus() == Resource.Status.LOADING;
    }

    /**
     * Indica si el estado actual es de éxito.
     *
     * @return true si es éxito, false en caso contrario.
     */
    public boolean isSuccess() {
        return resource.getStatus() == Resource.Status.SUCCESS;
    }

    /**
     * Indica si el estado actual es de error.
     *
     * @return true si es error, false en caso contrario.
     */
    public boolean isError() {
        return resource.getStatus() == Resource.Status.ERROR;
    }
}
