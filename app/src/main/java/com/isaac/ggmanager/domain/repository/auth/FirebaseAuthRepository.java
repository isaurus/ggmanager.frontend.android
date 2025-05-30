package com.isaac.ggmanager.domain.repository.auth;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;
import com.isaac.ggmanager.core.Resource;


/**
 * Abstracción de la capa de Dominio inyectada en las implementaciones de los repoitorios. Permiten
 * a la capa de Presentación utilizar funciones de la capa de Datos sin necesidad de conocerla.
 */
public interface FirebaseAuthRepository {

    /**
     * Registro/Login del usuario con Google.
     *
     * @param idToken El token recibido de Google para el proceso de autenticación en Firebase.
     * @return true o false según si la operación es o no exitosa encapsulado en un Resource.
     */
    LiveData<Resource<Boolean>> loginWithGoogle(String idToken);

    /**
     * Obtiene el usuario autenticado (FirebaseUser) mapeado a un UserModel.
     *
     * @return El usuario autenticado mapeado a UserModel.
     */
    FirebaseUser getAuthenticatedUser();

    /**
     * Comprueba si el usuario ha iniciado sesión previamente en la aplicación para lanzar las
     * funcionalidades principales y saltarse la fase de login.
     *
     * @return true o false según si el usuario está o no logeado.
     */
    boolean isUserAuthenticated();

    /**
     * Cierra la sesión del usuario activo para devolverle a la fase de login/registro.
     */
    void signOut();
}
