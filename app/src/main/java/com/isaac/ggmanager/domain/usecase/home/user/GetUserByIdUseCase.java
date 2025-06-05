package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para obtener un usuario a partir de su identificador único.
 * Proporciona un flujo reactivo (LiveData) que emite el recurso con el modelo de usuario
 * o el estado de error correspondiente.
 */
public class GetUserByIdUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de usuarios.
     *
     * @param userRepository Repositorio de usuarios para acceder a los datos.
     */
    @Inject
    public GetUserByIdUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la consulta para obtener un usuario por su ID.
     *
     * @param userId Identificador único del usuario.
     * @return LiveData que contiene un Resource con el UserModel o error si no se encuentra.
     */
    public LiveData<Resource<UserModel>> execute(String userId){
        return userRepository.getById(userId);
    }
}
