package com.isaac.ggmanager.domain.usecase.login;

import androidx.lifecycle.LiveData;

import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;

/**
 * Caso de uso para iniciar sesión con Google usando un token de ID.
 * Se encarga de solicitar el inicio de sesión a través del repositorio de autenticación Firebase.
 */
public class LoginWithGoogleUseCase {

    private final FirebaseAuthRepository firebaseAuthRepository;

    /**
     * Constructor con inyección de dependencias para el repositorio de autenticación Firebase.
     *
     * @param firebaseAuthRepository Repositorio encargado de la autenticación con Firebase.
     */
    @Inject
    public LoginWithGoogleUseCase(FirebaseAuthRepository firebaseAuthRepository) {
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    /**
     * Ejecuta el inicio de sesión con Google utilizando el token de ID proporcionado.
     *
     * @param tokenId Token de ID generado por Google para autenticar al usuario.
     * @return LiveData que contiene el estado de la operación envuelto en un Resource con un Boolean indicando éxito o fallo.
     */
    public LiveData<Resource<Boolean>> execute(String tokenId) {
        return firebaseAuthRepository.loginWithGoogle(tokenId);
    }
}
