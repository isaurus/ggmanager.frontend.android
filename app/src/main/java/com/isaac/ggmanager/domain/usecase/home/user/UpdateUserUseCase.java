package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para actualizar los datos de un usuario en el sistema.
 * Permite modificar la información del usuario proporcionada en el modelo de dominio.
 */
public class UpdateUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de usuarios.
     *
     * @param userRepository Repositorio encargado de las operaciones con usuarios.
     */
    @Inject
    public UpdateUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la actualización de la información del usuario.
     *
     * @param userModel Modelo con los datos actualizados del usuario.
     * @return LiveData que contiene un Resource con un Boolean que indica si la actualización fue exitosa.
     */
    public LiveData<Resource<Boolean>> execute(UserModel userModel){
        return userRepository.update(userModel);
    }
}
