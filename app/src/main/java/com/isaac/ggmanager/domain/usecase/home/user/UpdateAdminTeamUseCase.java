package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para actualizar el rol de un usuario a administrador ("Owner") de un equipo específico.
 * Permite asignar el rol de propietario de equipo a un usuario determinado.
 */
public class UpdateAdminTeamUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de usuarios.
     *
     * @param userRepository Repositorio encargado de la gestión de usuarios.
     */
    @Inject
    public UpdateAdminTeamUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la actualización del equipo administrador para un usuario específico.
     *
     * @param userId Identificador único del usuario.
     * @param teamId Identificador único del equipo.
     * @return LiveData que contiene un Resource con un Boolean indicando éxito o fallo de la operación.
     */
    public LiveData<Resource<Boolean>> execute(String userId, String teamId){
        return userRepository.updateUserTeam(userId, teamId, "Owner");
    }
}
