package com.isaac.ggmanager.ui.home.user;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;

/**
 * Clase que representa el estado de la vista (ViewState) para el perfil del usuario.
 * <p>
 * Contiene la información relacionada con el estado de la operación (cargando, éxito o error)
 * y el modelo de usuario que se muestra en la UI.
 * </p>
 */
public class UserProfileViewState {

    private final Resource<?> resource;
    private final UserModel user;

    /**
     * Constructor principal que inicializa el estado con un recurso y un usuario.
     *
     * @param resource recurso que contiene el estado (cargando, éxito o error) y datos o mensaje.
     * @param user modelo de usuario asociado al estado (puede ser null en carga o error).
     */
    public UserProfileViewState(Resource<?> resource, UserModel user){
        this.resource = resource;
        this.user = user;
    }

    /**
     * Crea un estado de éxito con el usuario proporcionado.
     *
     * @param user usuario obtenido correctamente.
     * @return instancia de UserProfileViewState con estado de éxito.
     */
    public static UserProfileViewState success(UserModel user){
        return new UserProfileViewState(Resource.success(user), user);
    }

    /**
     * Crea un estado de carga.
     *
     * @return instancia de UserProfileViewState con estado de carga.
     */
    public static UserProfileViewState loading(){
        return new UserProfileViewState(Resource.loading(), null);
    }

    /**
     * Crea un estado de error con el mensaje proporcionado.
     *
     * @param message mensaje de error.
     * @return instancia de UserProfileViewState con estado de error.
     */
    public static UserProfileViewState error(String message){
        return new UserProfileViewState(Resource.error(message), null);
    }

    /**
     * Obtiene el modelo de usuario asociado a este estado.
     *
     * @return usuario, o null si no hay datos disponibles.
     */
    public UserModel getUser() {
        return user;
    }

    /**
     * Obtiene el estado del recurso (cargando, éxito o error).
     *
     * @return estado actual del recurso.
     */
    public Resource.Status getStatus(){
        return resource.getStatus();
    }

    /**
     * Obtiene los datos del recurso, tipado genéricamente.
     *
     * @param <T> tipo de dato esperado.
     * @return datos asociados al recurso.
     */
    public <T> T getData() {
        return (T) resource.getData();
    }

    /**
     * Obtiene el mensaje del recurso, útil principalmente en caso de error.
     *
     * @return mensaje descriptivo.
     */
    public String getMessage(){
        return resource.getMessage();
    }
}
