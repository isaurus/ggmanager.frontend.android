package com.isaac.ggmanager.domain.repository.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.base.FirestoreRepository;

public interface TeamRepository extends FirestoreRepository<TeamModel> {

    /**
     * Añade un nuevo usuario el equipo.
     *
     * @param teamId Identificador del equipo al que añadir un nuevo usuario.
     * @param userEmail Email del usuario a añadir.
     * @return LiveData con Resource del éxito o fracaso de la operación.
     */
    LiveData<Resource<Boolean>> addUserToTeam(String teamId, String userEmail);

    /**
     * Elimina un usuario del equipo.
     *
     * @param teamId Identificador del equipo del que eliminar un usuario.
     * @param userId Identificador del usuario que eliminar del equipo.
     * @return LiveData con Resource del éxito o fracaso de la operación.
     */
    LiveData<Resource<Boolean>> removeUserFromTeam(String teamId, String userId);

    /**
     * Crea un equipo añadiendo en la primera posición de la lista de miembros el ID del
     * usuario creador. Sobreescribe el metodo genérico de create.
     *
     * @param team Equipo que crear.
     * @return LiveData con Resource del éxito o fracaso de la operación.
     */
    LiveData<Resource<String>> createTeam(TeamModel team);
}
