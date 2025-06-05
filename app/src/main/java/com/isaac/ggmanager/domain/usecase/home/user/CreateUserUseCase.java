package com.isaac.ggmanager.domain.usecase.home.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import javax.inject.Inject;

/**
 * Caso de uso para crear un nuevo usuario en la base de datos.
 *
 * Se utiliza tras el registro en Firebase Authentication, cuando se requiere
 * persistir información adicional del usuario como nombre, apellidos, país, etc.
 */
public class CreateUserUseCase {

    private final UserRepository userRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param userRepository Repositorio que gestiona las operaciones relacionadas con usuarios.
     */
    @Inject
    public CreateUserUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    /**
     * Ejecuta la creación de un nuevo usuario en Firestore u otra base de datos.
     *
     * @param user El modelo de usuario que contiene la información a persistir.
     * @return Un {@link LiveData} que contiene un {@link Resource} con un valor booleano
     * indicando si la operación fue exitosa.
     */
    public LiveData<Resource<Boolean>> execute(UserModel user){
        return userRepository.create(user);
    }
}
