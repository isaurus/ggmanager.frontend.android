package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Caso de uso para obtener la lista completa de usuarios.
 *
 * Este caso de uso recupera todos los usuarios disponibles en la base de datos o fuente de datos.
 */
public class GetAllUsersUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param userRepository Repositorio que maneja operaciones de usuario.
     */
    @Inject
    public GetAllUsersUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la consulta para obtener todos los usuarios.
     *
     * @return Un {@link LiveData} que contiene un {@link Resource} con una lista de {@link UserModel}.
     */
    public LiveData<Resource<List<UserModel>>> execute(){
        return userRepository.getAll();
    }
}
