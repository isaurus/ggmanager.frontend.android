package com.isaac.ggmanager.domain.repository.user;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.base.FirestoreRepository;

import java.util.List;

public interface UserRepository extends FirestoreRepository<UserModel> {

    /**
     * Obtiene el usuario por su email.
     *
     * @param email Email del usuario.
     * @return LiveData con Resource del usuario o el error.
     */
    LiveData<Resource<UserModel>> getUserByEmail(String email);

    /**
     * Actualiza el equipo al que pertenece el usuario.
     *
     * @param userId Identificador del usuario a actualizar equipo.
     * @param teamId Identificador del equipo.
     * @return LiveData con Resource del éxito o fracaso de la operación.
     */
    LiveData<Resource<Boolean>> updateUserTeam(String userId, String teamId);

    /**
     * Crea un nuevo usuario asignándole el ID obtenido de Firebase Authentication.
     *
     * @param user Usuario a crear.
     * @param userId Identificador del usuario de Firebase Authentication.
     * @return LiveData con Resource del éxito o fracaso de la operación.
     */
    LiveData<Resource<Boolean>> create(UserModel user, String userId);

    /**
     * Obtiene la lista de usuarios pertenecientes a un equipo.
     *
     * @param teamId Identificador del equipo.
     * @return LiveData con Resource de los usuarios o error.
     */
    LiveData<Resource<List<UserModel>>> getUsersByTeam(String teamId);
}
