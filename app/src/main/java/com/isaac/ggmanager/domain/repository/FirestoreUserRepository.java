package com.isaac.ggmanager.domain.repository;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;


/**
 * Abstracción de la capa de Dominio inyectada en las implementaciones de los repositorios. Permiten
 * a la capa de Presentación utilizar funciones de la capa de Datos sin necesidad de conocerla.
 */
public interface FirestoreUserRepository {

    /**
     * Obtiene el usuario persistido en Firestore Database.
     *
     * @return UserModel para pintar sus propiedades en la UI.
     */
    LiveData<Resource<UserModel>> getCurrentUser();

    /**
     * Comprueba si el usuario pertenece a algún equipo.
     *
     * @return true en caso afirmativo, false en caso contrario.
     */
    LiveData<Resource<Boolean>> hasTeam();

    /**
     * Persiste al usuario en Firestore Database. Su propósito es tanto para persistir un nuevo usuario
     * si aún no existe, como para actualizarlo en caso de que ya esté persistido.
     *
     * @return LiveData observable por la Activity.
     */
    LiveData<Resource<Boolean>> saveUserProfile(UserModel userModel);

    LiveData<Resource<Boolean>> assignTeamToUser(UserModel userModel, String teamId);
}
