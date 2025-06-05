package com.isaac.ggmanager.ui.home.team;

import com.isaac.ggmanager.core.Resource;

import javax.annotation.Nullable;

/**
 * Clase que representa el estado de la vista para la creación de un equipo.
 * <p>
 * Contiene el estado de validación del formulario, así como el estado
 * general del proceso (cargando, éxito, error).
 * </p>
 */
public class CreateTeamViewState {

    /**
     * Enum que representa el estado de validación del formulario.
     */
    public enum ValidationState {
        IDLE,       // No se está validando
        VALIDATING  // El formulario está siendo validado
    }

    private final Resource<?> resource;
    private final ValidationState validationState;
    private final boolean isTeamNameValid;
    private final boolean isTeamDescriptionValid;

    /**
     * Constructor general para el estado de la vista.
     *
     * @param resource               Estado general del recurso (éxito, error, carga)
     * @param validationState        Estado de validación del formulario
     * @param isTeamNameValid        Indicador si el nombre del equipo es válido
     * @param isTeamDescriptionValid Indicador si la descripción del equipo es válida
     */
    public CreateTeamViewState(Resource<?> resource,
                               ValidationState validationState,
                               boolean isTeamNameValid,
                               boolean isTeamDescriptionValid) {
        this.resource = resource;
        this.validationState = validationState;
        this.isTeamNameValid = isTeamNameValid;
        this.isTeamDescriptionValid = isTeamDescriptionValid;
    }

    /**
     * Estado que representa éxito en la creación del equipo.
     */
    public static CreateTeamViewState success() {
        return new CreateTeamViewState(
                Resource.success(null),
                ValidationState.IDLE,
                true,
                true);
    }

    /**
     * Estado que representa que la creación del equipo está en progreso.
     */
    public static CreateTeamViewState loading() {
        return new CreateTeamViewState(
                Resource.loading(),
                ValidationState.IDLE,
                false,
                false);
    }

    /**
     * Estado que representa un error en la creación del equipo.
     *
     * @param message Mensaje de error a mostrar.
     */
    public static CreateTeamViewState error(String message) {
        return new CreateTeamViewState(
                Resource.error(message),
                ValidationState.IDLE,
                false,
                false);
    }

    /**
     * Estado que representa que el formulario está siendo validado.
     *
     * @param isTeamNameValid        Validez del nombre del equipo.
     * @param isTeamDescriptionValid Validez de la descripción del equipo.
     */
    public static CreateTeamViewState validating(boolean isTeamNameValid,
                                                 boolean isTeamDescriptionValid) {
        return new CreateTeamViewState(
                Resource.loading(),
                ValidationState.VALIDATING,
                isTeamNameValid,
                isTeamDescriptionValid);
    }

    /**
     * Obtiene el estado general del recurso.
     *
     * @return Estado del recurso (SUCCESS, ERROR, LOADING).
     */
    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    /**
     * Obtiene el mensaje asociado al estado actual.
     *
     * @return Mensaje de error o vacío si no hay mensaje.
     */
    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    /**
     * Obtiene el estado de validación del formulario.
     *
     * @return Estado de validación.
     */
    public ValidationState getValidationState() {
        return validationState;
    }

    /**
     * Indica si el nombre del equipo es válido.
     *
     * @return true si válido, false si no.
     */
    public boolean isTeamNameValid() {
        return isTeamNameValid;
    }

    /**
     * Indica si la descripción del equipo es válida.
     *
     * @return true si válida, false si no.
     */
    public boolean isTeamDescriptionValid() {
        return isTeamDescriptionValid;
    }
}
