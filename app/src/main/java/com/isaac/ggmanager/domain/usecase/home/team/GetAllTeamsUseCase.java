package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Caso de uso para obtener todos los equipos disponibles en la base de datos.
 *
 * Este caso de uso se utiliza para mostrar el listado de todos los equipos existentes,
 * por ejemplo, en una sección de exploración o búsqueda de equipos.
 */
public class GetAllTeamsUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio que gestiona las operaciones relacionadas con equipos.
     */
    @Inject
    public GetAllTeamsUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la operación para recuperar todos los equipos.
     *
     * @return Un {@link LiveData} que contiene un {@link Resource} con una lista de {@link TeamModel}.
     */
    public LiveData<Resource<List<TeamModel>>> execute(){
        return teamRepository.getAll();
    }
}
