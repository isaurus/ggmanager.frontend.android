package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para eliminar un usuario de la base de datos.
 *
 * Este caso de uso se encarga de eliminar la información de un usuario por su ID.
 * Puede utilizarse, por ejemplo, en procesos de eliminación de cuenta o mantenimiento administrativo.
 */
public class DeleteUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param userRepository Repositorio encargado de las operaciones relacionadas con usuarios.
     */
    @Inject
    public DeleteUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la eliminación del usuario correspondiente al ID proporcionado.
     *
     * @param userId El identificador único del usuario a eliminar.
     * @return Un {@link LiveData} que contiene un {@link Resource} con un valor booleano
     * que indica si la eliminación fue exitosa.
     */
    public LiveData<Resource<Boolean>> execute(String userId){
        return userRepository.deleteById(userId);
    }
}
