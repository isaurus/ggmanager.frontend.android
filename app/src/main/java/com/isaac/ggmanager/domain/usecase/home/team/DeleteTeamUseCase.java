package com.isaac.ggmanager.domain.usecase.home.team;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import javax.inject.Inject;

/**
 * Caso de uso para eliminar un equipo de la base de datos.
 *
 * Este caso de uso se utiliza cuando un administrador decide eliminar un equipo desde la interfaz
 * de gestión de equipos. Elimina el documento correspondiente al equipo en Firestore.
 */
public class DeleteTeamUseCase {

    private final TeamRepository teamRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param teamRepository Repositorio que maneja las operaciones relacionadas con equipos.
     */
    @Inject
    public DeleteTeamUseCase(TeamRepository teamRepository){
        this.teamRepository = teamRepository;
    }

    /**
     * Ejecuta la eliminación del equipo con el ID proporcionado.
     *
     * @param teamId ID único del equipo a eliminar.
     * @return Un {@link LiveData} que contiene un {@link Resource} indicando éxito o error.
     */
    public LiveData<Resource<Boolean>> execute(String teamId){
        return teamRepository.deleteById(teamId);
    }
}
