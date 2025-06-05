package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

/**
 * Caso de uso para actualizar los datos de un equipo.
 *
 * Este caso de uso se emplea cuando un usuario administrador desea modificar
 * información del equipo, como el nombre, descripción, imagen, etc.
 */
public class UpdateTeamUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio que gestiona las operaciones relacionadas con equipos.
     */
    @Inject
    public UpdateTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la operación de actualización del equipo.
     *
     * @param teamModel El modelo del equipo con los datos actualizados.
     * @return Un {@link LiveData} que contiene un {@link Resource} con un valor booleano
     * indicando si la operación fue exitosa.
     */
    public LiveData<Resource<Boolean>> execute(TeamModel teamModel){
        return teamRepository.update(teamModel);
    }
}
