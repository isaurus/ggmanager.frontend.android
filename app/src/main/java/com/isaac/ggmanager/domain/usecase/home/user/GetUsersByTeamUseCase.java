package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Caso de uso para obtener la lista de usuarios que pertenecen a un equipo específico.
 * Devuelve un LiveData que emite un Resource con una lista de modelos de usuario,
 * permitiendo observar cambios en tiempo real o manejar estados de carga y error.
 */
public class GetUsersByTeamUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de usuarios.
     *
     * @param userRepository Repositorio encargado de la gestión de usuarios.
     */
    @Inject
    public GetUsersByTeamUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la consulta para obtener todos los usuarios pertenecientes a un equipo.
     *
     * @param teamId Identificador único del equipo.
     * @return LiveData que contiene un Resource con la lista de usuarios o un error.
     */
    public LiveData<Resource<List<UserModel>>> execute(String teamId){
        return userRepository.getUsersByTeam(teamId);
    }
}
