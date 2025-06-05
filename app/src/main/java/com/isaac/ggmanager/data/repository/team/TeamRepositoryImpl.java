package com.isaac.ggmanager.data.repository.team;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.data.repository.base.FirestoreRepositoryImpl;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

/**
 * Implementación del repositorio para la gestión de equipos (TeamRepository)
 * que utiliza Firebase Firestore como fuente de datos.
 *
 * Hereda de FirestoreRepositoryImpl para reutilizar funcionalidades CRUD comunes.
 */
public class TeamRepositoryImpl extends FirestoreRepositoryImpl<TeamModel> implements TeamRepository {

    /**
     * Constructor que recibe la instancia de FirebaseFirestore para acceder a Firestore.
     *
     * @param firestore Instancia de FirebaseFirestore.
     */
    public TeamRepositoryImpl(FirebaseFirestore firestore) {
        super(firestore);
    }

    /**
     * Obtiene la referencia a la colección "teams" en Firestore.
     *
     * @return CollectionReference a la colección "teams".
     */
    @Override
    protected CollectionReference getCollection() {
        return firestore.collection("teams");
    }

    /**
     * Devuelve la clase modelo TeamModel para la deserialización de documentos Firestore.
     *
     * @return Clase TeamModel.class.
     */
    @Override
    protected Class<TeamModel> getModelClass() {
        return TeamModel.class;
    }

    /**
     * Obtiene el identificador único del documento Firestore a partir del modelo TeamModel.
     *
     * @param model Instancia de TeamModel.
     * @return ID del documento Firestore (campo "id" del modelo).
     */
    @Override
    protected String getDocumentId(TeamModel model) {
        return model.getId();
    }

    /**
     * Añade un usuario a la lista de miembros de un equipo específico.
     *
     * Usa el operador atómico arrayUnion para agregar el userId al campo "members"
     * sin duplicados.
     *
     * @param teamId ID del equipo al que se añadirá el usuario.
     * @param userId ID del usuario que se añadirá.
     * @return LiveData con Resource<Boolean> que indica el estado de la operación:
     *         cargando, éxito (true) o error.
     */
    @Override
    public LiveData<Resource<Boolean>> addUserToTeam(String teamId, String userId) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(teamId)
                .update("members", FieldValue.arrayUnion(userId))
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> {
                    result.setValue(Resource.error(e.getMessage()));
                    Log.e("PRUEBA", "Error Firestore en addUserToTeam: " + e.getMessage());
                });

        return result;
    }

    /**
     * Crea un nuevo equipo en Firestore generando un nuevo documento con ID único.
     *
     * El ID generado se asigna al modelo antes de persistirlo.
     *
     * @param team Instancia del equipo a crear.
     * @return LiveData con Resource<String> que contiene el ID del nuevo equipo si
     *         la operación fue exitosa, o un error si falló.
     */
    @Override
    public LiveData<Resource<String>> createTeam(TeamModel team) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        DocumentReference docRef = getCollection().document();
        String teamId = docRef.getId();

        team.setId(teamId);

        docRef.set(team)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(teamId)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /**
     * Elimina un usuario de la lista de miembros de un equipo específico.
     *
     * Usa el operador atómico arrayRemove para eliminar el userId del campo "members".
     *
     * @param teamId ID del equipo del cual se eliminará el usuario.
     * @param userId ID del usuario que se eliminará.
     * @return LiveData con Resource<Boolean> que indica el estado de la operación:
     *         cargando, éxito (true) o error.
     */
    @Override
    public LiveData<Resource<Boolean>> removeUserFromTeam(String teamId, String userId) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(teamId)
                .update("members", FieldValue.arrayRemove(userId))
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

}
