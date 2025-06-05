package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para obtener un usuario mediante su email.
 */
public class GetUserByEmailUseCase {

    private final UserRepository userRepository;

    @Inject
    public GetUserByEmailUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la consulta para obtener un usuario por su email.
     *
     * @param email Email del usuario a buscar.
     * @return LiveData que contiene un Resource con el UserModel encontrado o error.
     */
    public LiveData<Resource<UserModel>> execute(String email){
        return userRepository.getUserByEmail(email);
    }
}
