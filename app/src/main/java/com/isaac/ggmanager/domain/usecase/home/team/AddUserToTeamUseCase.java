package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

/**
 * Caso de uso para añadir un usuario a un equipo existente.
 *
 * Este caso de uso se utiliza cuando un usuario desea unirse a un equipo o ser invitado a uno.
 * Utiliza el repositorio de equipo para realizar la operación y devuelve un resultado envuelto
 * en un {@link Resource} que indica el estado de la operación (éxito, error o carga).
 */
public class AddUserToTeamUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio que gestiona las operaciones relacionadas con equipos.
     */
    @Inject
    public AddUserToTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la acción de añadir un usuario a un equipo.
     *
     * @param teamId ID del equipo al que se desea añadir el usuario.
     * @param userToAddId ID del usuario que se quiere añadir al equipo.
     * @return Un {@link LiveData} que contiene un {@link Resource} con el resultado de la operación.
     */
    public LiveData<Resource<Boolean>> execute(String teamId, String userToAddId){
        return teamRepository.addUserToTeam(teamId, userToAddId);
    }
}
