package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

/**
 * Caso de uso para crear un nuevo equipo en la aplicación.
 *
 * Este caso de uso se ejecuta cuando un usuario desea crear un equipo desde la sección de equipos.
 * Utiliza el repositorio de equipos para persistir los datos del equipo en Firestore.
 */
public class CreateTeamUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio que maneja las operaciones relacionadas con equipos.
     */
    @Inject
    public CreateTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la acción de crear un nuevo equipo con los datos proporcionados.
     *
     * @param teamModel Modelo de dominio con la información del equipo a crear.
     * @return Un {@link LiveData} que contiene un {@link Resource} con el ID del equipo creado o un error.
     */
    public LiveData<Resource<String>> execute(TeamModel teamModel){
        return teamRepository.createTeam(teamModel);
    }
}
