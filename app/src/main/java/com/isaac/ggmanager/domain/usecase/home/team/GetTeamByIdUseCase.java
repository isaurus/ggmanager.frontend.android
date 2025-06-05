package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

/**
 * Caso de uso para obtener un equipo específico a partir de su ID.
 *
 * Este caso de uso se utiliza cuando se necesita acceder a los datos detallados de un equipo,
 * como al entrar en la pantalla de perfil de equipo o al realizar operaciones específicas sobre él.
 */
public class GetTeamByIdUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio que gestiona las operaciones relacionadas con equipos.
     */
    @Inject
    public GetTeamByIdUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la operación para obtener los datos de un equipo por su ID.
     *
     * @param teamId ID del equipo a consultar.
     * @return Un {@link LiveData} que contiene un {@link Resource} con el {@link TeamModel} correspondiente.
     */
    public LiveData<Resource<TeamModel>> execute(String teamId){
        return teamRepository.getById(teamId);
    }
}
