package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

/**
 * Caso de uso para eliminar a un usuario de un equipo.
 *
 * Este caso de uso se emplea cuando un administrador desea expulsar a un miembro de su equipo
 * o cuando un usuario decide abandonar un equipo.
 */
public class RemoveUserFromTeamUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio encargado de gestionar las operaciones de equipo.
     */
    @Inject
    public RemoveUserFromTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la operación para eliminar un usuario de un equipo.
     *
     * @param teamId ID del equipo del que se desea eliminar al usuario.
     * @param userId ID del usuario que se va a eliminar.
     * @return Un {@link LiveData} con un {@link Resource} que indica si la operación fue exitosa.
     */
    public LiveData<Resource<Boolean>> execute(String teamId, String userId){
        return teamRepository.removeUserFromTeam(teamId, userId);
    }
}
