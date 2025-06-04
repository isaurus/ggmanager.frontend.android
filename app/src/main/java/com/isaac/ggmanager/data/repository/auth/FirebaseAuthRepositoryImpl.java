package com.isaac.ggmanager.data.repository.auth;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.auth.FirebaseAuthRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementación del repositorio de autenticación usando Firebase Authentication.
 * <p>
 * Proporciona métodos para iniciar sesión con Google, obtener el usuario autenticado,
 * comprobar el estado de autenticación y cerrar sesión.
 * </p>
 * Esta clase está anotada con @Singleton para asegurar una única instancia durante el ciclo de vida de la aplicación.
 */
@Singleton
public class FirebaseAuthRepositoryImpl implements FirebaseAuthRepository {

    private final FirebaseAuth firebaseAuth;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param firebaseAuth instancia de FirebaseAuth proporcionada por DI
     */
    @Inject
    public FirebaseAuthRepositoryImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

    /**
     * Inicia sesión con Firebase Authentication usando un token de Google.
     * <p>
     * Devuelve un LiveData que emite estados de carga, éxito o error
     * mediante la clase Resource<Boolean>.
     * </p>
     *
     * @param idToken token de autenticación de Google obtenido desde el cliente
     * @return LiveData<Resource<Boolean>> con el resultado de la operación de login
     */
    @Override
    public LiveData<Resource<Boolean>> loginWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Obtiene el usuario actualmente autenticado en Firebase.
     *
     * @return instancia de FirebaseUser si hay un usuario autenticado,
     *         o null si no hay sesión activa
     */
    @Override
    public FirebaseUser getAuthenticatedUser() {
        return firebaseAuth.getCurrentUser();
    }

    /**
     * Comprueba si existe un usuario autenticado actualmente.
     *
     * @return true si hay un usuario autenticado, false en caso contrario
     */
    @Override
    public boolean isUserAuthenticated() {
        return firebaseAuth.getCurrentUser() != null;
    }

    /**
     * Cierra la sesión del usuario actual en Firebase Authentication.
     */
    @Override
    public void signOut() {
        firebaseAuth.signOut();
    }
}
