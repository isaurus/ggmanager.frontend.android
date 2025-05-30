package com.isaac.ggmanager.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;


import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Implementación de FirestoreUserRepository para inyectar las dependencias necesarias y desarrollar
 * el cuerpo de los métodos declarados.
 */
@Singleton
public class FirestoreUserRepositoryImpl implements FirestoreUserRepository {

    private final FirebaseFirestore firestore;
    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public FirestoreUserRepositoryImpl(FirebaseFirestore firestore, FirebaseAuthRepository firebaseAuthRepository) {
        this.firestore = firestore;
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    /**
     * Persiste al usuario en Firestore Database con el propósito tanto de crearlo por primera vez,
     * como actualizarlo en caso de que haya sido previamente persistido, gracias al 'SetOptions.merge()'.
     *
     * @param userModel El modelo de usuario recibido por parámetro.
     * @return LiveData con Resource observable por la Activity.
     */
    @Override
    public LiveData<Resource<Boolean>> saveUserProfile(UserModel userModel){
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String firebaseUid = firebaseAuthRepository.getAuthenticatedUser().getUid();
        String email = firebaseAuthRepository.getAuthenticatedUser().getEmail();

        userModel.setFirebaseUid(firebaseUid);
        userModel.setEmail(email);

        firestore.collection("users")
                .document(firebaseUid)
                .set(userModel, SetOptions.merge())
                .addOnSuccessListener(unused -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error("Error al guardar usuario: " + e.getMessage())));

        return result;
    }

    /**
     * Consulta el usuario en Firestore Database para devolver el usuario.
     *
     * @return El usuario como modelo de dominio.
     */
    @Override
    public LiveData<Resource<UserModel>> getCurrentUser() {
        MutableLiveData<Resource<UserModel>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String userUid = firebaseAuthRepository.getAuthenticatedUser().getUid();

        firestore.collection("users")
                .document(userUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        UserModel user = documentSnapshot.toObject(UserModel.class);
                        result.setValue(Resource.success(user));
                    } else if (!documentSnapshot.exists()) {
                        result.setValue(Resource.success(null));
                    }
                })
                .addOnFailureListener(e ->
                        result.setValue(Resource.error("Error al obtener usuario: " + e.getMessage()))
                );

        return result;
    }

    /**
     * Comprueba si el usuario pertenece a algún equipo para actualizar la UI.
     *
     * @return ¿?¿?¿?
     */
    @Override
    public LiveData<Resource<Boolean>> hasTeam() {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String userUid = firebaseAuthRepository.getAuthenticatedUser().getUid();

        firestore.collection("users")
                .document(userUid)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    String teamId = documentSnapshot.getString("teamId");
                    result.setValue(Resource.success(teamId != null && !teamId.isEmpty()));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error("Error al consultar el equipo al que pertenece el usuario: " + e.getMessage())));

        return result;
    }


    @Override
    public LiveData<Resource<UserModel>> getUserByEmail(String email){
        MutableLiveData<Resource<UserModel>> result = new MutableLiveData<>();

        result.postValue(Resource.loading());

        firestore.collection("users")
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .addOnSuccessListener(query -> {
                    if (!query.isEmpty()){
                        DocumentSnapshot doc = query.getDocuments().get(0);
                        UserModel user = doc.toObject(UserModel.class);
                        if (user != null){
                            user.setFirebaseUid(user.getFirebaseUid());
                            result.postValue(Resource.success(user));
                        } else {
                            result.postValue(Resource.error("No se pudo convertir el documento a usuario."));
                        }
                    } else {
                        result.postValue(Resource.error("No existe un usuario con ese correo."));
                    }
                })
                .addOnFailureListener(e -> result.postValue(Resource.error("Error al obtener usuario: " + e.getMessage())));
        return result;
    }
}
