package com.isaac.ggmanager.domain.usecase.auth;

import com.google.firebase.auth.FirebaseUser;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Caso de uso para obtener el usuario actualmente autenticado en Firebase.
 *
 * Este caso de uso permite acceder al objeto FirebaseUser que representa al usuario
 * autenticado, para posteriormente mapearlo a un modelo de dominio propio si es necesario.
 * Es especialmente útil para obtener datos como el firebaseUid, que identifica de forma única
 * al usuario en Firebase Authentication.
 */
public class GetAuthenticatedUserUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    /**
     * Constructor que inyecta el repositorio de autenticación Firebase.
     *
     * @param firebaseAuthRepository Repositorio encargado de la gestión de autenticación.
     */
    @Inject
    public GetAuthenticatedUserUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    /**
     * Ejecuta la obtención del usuario autenticado en Firebase.
     *
     * @return El objeto FirebaseUser correspondiente al usuario autenticado,
     *         o null si no hay ningún usuario autenticado.
     */
    public FirebaseUser execute(){
        return firebaseAuthRepository.getAuthenticatedUser();
    }
}
