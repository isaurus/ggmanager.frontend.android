package com.isaac.ggmanager.data.remote;

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

@Singleton
public class FirebaseAuthRepositoryImpl implements FirebaseAuthRepository {

    private final FirebaseAuth firebaseAuth;

    @Inject
    public FirebaseAuthRepositoryImpl(FirebaseAuth firebaseAuth) {
        this.firebaseAuth = firebaseAuth;
    }

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
     * Implementación para obtener el usuario autenticado mapeado a UserModel.
     *
     * @return El FirebaseUser mapeado a UserModel.
     */
    @Override
    public FirebaseUser getAuthenticatedUser() {

        return firebaseAuth.getCurrentUser();
    }

    /**
     * Implementación para comprobar si el usuario ya está autenticado.
     *
     * @return true si está autenticado, false en caso contrario.
     */
    @Override
    public boolean isUserAuthenticated(){
        return firebaseAuth.getCurrentUser() != null;
    }

    @Override
    public void signOut(){
        firebaseAuth.signOut();
    }
}
