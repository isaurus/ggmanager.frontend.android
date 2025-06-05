package com.isaac.ggmanager.ui.home.team.member;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;

import java.util.List;

/**
 * Clase que representa el estado de la vista para la gestión de miembros de un equipo.
 * Contiene información sobre el estado de carga, validación del email y la lista de miembros.
 */
public class MemberViewState {

    /**
     * Enum que representa el estado de validación del email.
     */
    public enum ValidationState {
        /** Estado inicial sin validación en proceso */
        IDLE,
        /** Estado durante la validación del email */
        VALIDATING
    }

    private final Resource<?> resource;
    private final ValidationState validationState;
    private final boolean isEmailValid;
    private final List<UserModel> memberList;

    /**
     * Constructor privado para crear un estado de vista con todos sus atributos.
     *
     * @param resource        Recurso que representa el estado de la operación (cargando, éxito, error).
     * @param validationState Estado de validación del email.
     * @param isEmailValid    Indica si el email es válido o no.
     * @param memberList      Lista de usuarios miembros del equipo (puede ser nula).
     */
    public MemberViewState(Resource<?> resource,
                           ValidationState validationState,
                           boolean isEmailValid,
                           List<UserModel> memberList) {
        this.resource = resource;
        this.validationState = validationState;
        this.isEmailValid = isEmailValid;
        this.memberList = memberList;
    }

    /**
     * Crea un estado que indica que la vista está cargando datos.
     *
     * @return Estado de vista en carga.
     */
    public static MemberViewState loading() {
        return new MemberViewState(Resource.loading(), ValidationState.IDLE, true, null);
    }

    /**
     * Crea un estado que indica que la operación fue exitosa y contiene la lista de miembros.
     *
     * @param members Lista de usuarios miembros del equipo.
     * @return Estado de vista con éxito y lista de miembros.
     */
    public static MemberViewState success(List<UserModel> members) {
        return new MemberViewState(Resource.success(members), ValidationState.IDLE, true, members);
    }

    /**
     * Crea un estado que indica que hubo un error en la operación.
     *
     * @param message Mensaje de error.
     * @return Estado de vista con error.
     */
    public static MemberViewState error(String message) {
        return new MemberViewState(Resource.error(message), ValidationState.IDLE, false, null);
    }

    /**
     * Crea un estado que indica que se está validando un email y si éste es válido o no.
     *
     * @param isEmailValid Indica si el email es válido.
     * @return Estado de vista durante la validación del email.
     */
    public static MemberViewState validating(boolean isEmailValid) {
        return new MemberViewState(Resource.loading(), ValidationState.VALIDATING, isEmailValid, null);
    }

    /**
     * Obtiene el estado actual de la validación del email.
     *
     * @return Estado de validación.
     */
    public ValidationState getValidationState() {
        return validationState;
    }

    /**
     * Obtiene el estado de la operación (cargando, éxito, error).
     *
     * @return Estado de la operación.
     */
    public Resource.Status getStatus() {
        return resource != null ? resource.getStatus() : null;
    }

    /**
     * Obtiene el mensaje asociado a la operación, ya sea de error o información.
     *
     * @return Mensaje de la operación o "Error desconocido" si no hay mensaje.
     */
    public String getMessage() {
        return resource != null ? resource.getMessage() : "Error desconocido";
    }

    /**
     * Indica si el email es válido.
     *
     * @return true si el email es válido, false en caso contrario.
     */
    public boolean isEmailValid() {
        return isEmailValid;
    }

    /**
     * Obtiene la lista de usuarios miembros del equipo.
     *
     * @return Lista de miembros o null si no existe.
     */
    public List<UserModel> getMemberList() {
        return memberList;
    }

    /**
     * Indica si la lista de miembros está vacía o es nula.
     *
     * @return true si la lista está vacía o es null, false en caso contrario.
     */
    public boolean isEmpty() {
        return memberList == null || memberList.isEmpty();
    }
}
