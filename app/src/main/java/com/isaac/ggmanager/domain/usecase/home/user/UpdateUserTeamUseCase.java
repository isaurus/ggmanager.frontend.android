package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para actualizar el equipo asociado a un usuario, asignándole el rol de "Member".
 * Esta operación permite cambiar el equipo al que pertenece un usuario con un rol de miembro estándar.
 */
public class UpdateUserTeamUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de usuarios.
     *
     * @param userRepository Repositorio responsable de la gestión de usuarios.
     */
    @Inject
    public UpdateUserTeamUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la actualización del equipo y rol de un usuario determinado.
     *
     * @param userId Identificador único del usuario.
     * @param teamId Identificador único del equipo.
     * @return LiveData que contiene un Resource con un Boolean que indica si la operación fue exitosa.
     */
    public LiveData<Resource<Boolean>> execute(String userId, String teamId){
        return userRepository.updateUserTeam(userId, teamId, "Member");
    }
}
