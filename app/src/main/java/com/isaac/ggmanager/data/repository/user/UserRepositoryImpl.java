package com.isaac.ggmanager.data.repository.user;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.data.repository.base.FirestoreRepositoryImpl;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Implementación del repositorio para la gestión de usuarios (UserRepository)
 * utilizando Firebase Firestore como fuente de datos.
 *
 * Hereda de FirestoreRepositoryImpl para aprovechar métodos CRUD genéricos.
 */
public class UserRepositoryImpl extends FirestoreRepositoryImpl<UserModel> implements UserRepository {

    /**
     * Constructor inyectado para obtener la instancia de FirebaseFirestore.
     *
     * @param firestore Instancia de FirebaseFirestore.
     */
    @Inject
    public UserRepositoryImpl(FirebaseFirestore firestore) {
        super(firestore);
    }

    /**
     * Obtiene la referencia a la colección "users" en Firestore.
     *
     * @return CollectionReference a la colección "users".
     */
    @Override
    protected CollectionReference getCollection() {
        return firestore.collection("users");
    }

    /**
     * Devuelve la clase modelo UserModel para la deserialización de documentos Firestore.
     *
     * @return Clase UserModel.class.
     */
    @Override
    protected Class<UserModel> getModelClass() {
        return UserModel.class;
    }

    /**
     * Obtiene el identificador único del documento Firestore a partir del modelo UserModel.
     *
     * @param model Instancia de UserModel.
     * @return ID del documento Firestore (UID de Firebase).
     */
    @Override
    protected String getDocumentId(UserModel model) {
        return model.getFirebaseUid();
    }

    /**
     * Obtiene un usuario por su email.
     *
     * Realiza una consulta filtrando por el campo "email" y limita la búsqueda a un resultado.
     *
     * @param email Correo electrónico del usuario a buscar.
     * @return LiveData con Resource<UserModel> que contiene el usuario encontrado,
     *         estado de carga o error en la consulta.
     */
    @Override
    public LiveData<Resource<UserModel>> getUserByEmail(String email) {
        MutableLiveData<Resource<UserModel>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (!snapshot.isEmpty()) {
                        UserModel user = snapshot.getDocuments().get(0).toObject(UserModel.class);
                        result.setValue(Resource.success(user));
                    } else {
                        result.setValue(Resource.error("Usuario no encontrado"));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Crea un nuevo usuario en la colección "users".
     *
     * El ID del documento será el UID de Firebase del usuario.
     *
     * @param user Instancia del usuario a crear.
     * @return LiveData con Resource<Boolean> indicando éxito (true), carga o error.
     */
    @Override
    public LiveData<Resource<Boolean>> create(UserModel user) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(user.getFirebaseUid())
                .set(user)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Actualiza el equipo y el rol del usuario dentro del equipo.
     *
     * Actualiza los campos "teamId" y "teamRole" del documento del usuario.
     *
     * @param userId   UID de Firebase del usuario a actualizar.
     * @param teamId   ID del equipo al que pertenece el usuario.
     * @param teamRole Rol del usuario dentro del equipo.
     * @return LiveData con Resource<Boolean> indicando estado de carga o error.
     */
    @Override
    public LiveData<Resource<Boolean>> updateUserTeam(String userId, String teamId, String teamRole) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(userId)
                .update(
                        "teamId", teamId,
                        "teamRole", teamRole)
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Obtiene la lista de usuarios que pertenecen a un equipo específico.
     *
     * Realiza una consulta filtrando por el campo "teamId".
     *
     * @param teamId ID del equipo.
     * @return LiveData con Resource<List<UserModel>> con los usuarios encontrados,
     *         estado de carga o error.
     */
    @Override
    public LiveData<Resource<List<UserModel>>> getUsersByTeam(String teamId) {
        MutableLiveData<Resource<List<UserModel>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .whereEqualTo("teamId", teamId)
                .get()
                .addOnSuccessListener(snapshot -> {
                    List<UserModel> users = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        UserModel user = doc.toObject(UserModel.class);
                        if (user != null) users.add(user);
                    }
                    result.setValue(Resource.success(users));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
}
