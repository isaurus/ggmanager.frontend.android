package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para obtener el usuario actual por su ID.
 *
 * Este caso de uso permite obtener la información detallada de un usuario específico a partir de su ID.
 */
public class GetCurrentUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param userRepository Repositorio que maneja las operaciones de usuario.
     */
    @Inject
    public GetCurrentUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la consulta para obtener un usuario por su ID.
     *
     * @param userId ID del usuario a obtener.
     * @return Un {@link LiveData} que contiene un {@link Resource} con el {@link UserModel} correspondiente.
     */
    public LiveData<Resource<UserModel>> execute(String userId){
        return userRepository.getById(userId);
    }
}
