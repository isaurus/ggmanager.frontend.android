package com.isaac.ggmanager.ui.auth;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

/**
 * Representa el estado de la vista de lanzamiento (LaunchActivity), incluyendo
 * información sobre la existencia del perfil de usuario y el estado de la operación.
 */
public class LaunchViewState {

    @Nullable
    private final Resource<?> resource;
    private final boolean userHasProfile;

    /**
     * Constructor privado para inicializar el estado con un recurso y un indicador
     * de si el usuario tiene perfil configurado o no.
     *
     * @param resource      recurso que contiene datos o estado de la operación
     * @param userHasProfile true si el usuario tiene perfil creado, false si no
     */
    public LaunchViewState(@Nullable Resource<?> resource, boolean userHasProfile){
        this.resource = resource;
        this.userHasProfile = userHasProfile;
    }

    /**
     * Crea un estado que indica que el usuario tiene perfil configurado.
     *
     * @return instancia de LaunchViewState indicando perfil existente
     */
    public static LaunchViewState userHasProfile(){
        return new LaunchViewState(Resource.success(null), true);
    }

    /**
     * Crea un estado que indica que el usuario no tiene perfil configurado.
     *
     * @return instancia de LaunchViewState indicando perfil no existente
     */
    public static LaunchViewState userHasNoProfile(){
        return new LaunchViewState(Resource.success(null), false);
    }

    /**
     * Crea un estado exitoso con datos asociados.
     *
     * @param <T>  tipo de los datos
     * @param data datos asociados al estado exitoso
     * @return instancia de LaunchViewState con éxito y datos
     */
    public static <T> LaunchViewState success(T data){
        return new LaunchViewState(Resource.success(data), true);
    }

    /**
     * Crea un estado que indica error con un mensaje asociado.
     *
     * @param message mensaje descriptivo del error
     * @return instancia de LaunchViewState con error
     */
    public static LaunchViewState error(String message){
        return new LaunchViewState(Resource.error(message), false);
    }

    /**
     * Obtiene los datos asociados al recurso contenido en este estado.
     *
     * @param <T> tipo esperado de los datos
     * @return datos contenidos en el recurso, puede ser null
     */
    @SuppressWarnings("unchecked")
    public <T> T getData() {
        return (T) resource.getData();
    }

    /**
     * Obtiene el mensaje asociado al recurso, generalmente en caso de error.
     *
     * @return mensaje descriptivo o null si no hay mensaje
     */
    public String getMessage() {
        return resource.getMessage();
    }

    /**
     * Obtiene el estado (Status) actual del recurso contenido.
     *
     * @return estado del recurso o null si el recurso es null
     */
    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    /**
     * Indica si el usuario tiene perfil configurado o no.
     *
     * @return true si el usuario tiene perfil, false en caso contrario
     */
    public boolean isUserHasProfile() {
        return userHasProfile;
    }
}
