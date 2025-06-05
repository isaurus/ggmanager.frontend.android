package com.isaac.ggmanager.data.repository.task;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.data.repository.base.FirestoreRepositoryImpl;
import com.isaac.ggmanager.domain.model.TaskModel;
import com.isaac.ggmanager.domain.repository.task.TaskRepository;

import java.util.List;

/**
 * Implementación concreta del repositorio de tareas (TaskRepository) para acceder
 * a la colección "tasks" en Firebase Firestore.
 * <p>
 * Hereda de FirestoreRepositoryImpl para reutilizar las operaciones CRUD genéricas.
 * </p>
 */
public class TaskRepositoryImpl extends FirestoreRepositoryImpl<TaskModel> implements TaskRepository {

    /**
     * Constructor que recibe la instancia de FirebaseFirestore para acceder a la base de datos.
     *
     * @param firestore instancia de FirebaseFirestore
     */
    public TaskRepositoryImpl(FirebaseFirestore firestore) {
        super(firestore);
    }

    /**
     * Obtiene la referencia a la colección Firestore "tasks".
     *
     * @return CollectionReference para la colección "tasks"
     */
    @Override
    protected CollectionReference getCollection() {
        return firestore.collection("tasks");
    }

    /**
     * Devuelve la clase del modelo TaskModel para la deserialización de documentos.
     *
     * @return Clase TaskModel.class
     */
    @Override
    protected Class<TaskModel> getModelClass() {
        return TaskModel.class;
    }

    /**
     * Obtiene el ID único del documento Firestore a partir del modelo TaskModel.
     *
     * @param model instancia de TaskModel
     * @return ID del documento Firestore (campo "id" del modelo)
     */
    @Override
    protected String getDocumentId(TaskModel model) {
        return model.getId();
    }

    /**
     * Obtiene una lista de tareas cuyo ID esté contenido en la lista proporcionada.
     * <p>
     * Realiza una consulta Firestore con whereIn para obtener todas las tareas cuyo
     * campo "id" coincida con alguno de los IDs en tasksListId.
     * </p>
     * <p>
     * Si la lista de IDs es nula o vacía, devuelve inmediatamente una lista vacía.
     * </p>
     *
     * @param tasksListId Lista de IDs de tareas a obtener
     * @return LiveData<Resource<List<TaskModel>>> con la lista de tareas resultante,
     *         incluyendo estados de carga, éxito o error.
     */
    @Override
    public LiveData<Resource<List<TaskModel>>> getUserTasks(List<String> tasksListId) {
        MutableLiveData<Resource<List<TaskModel>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        if (tasksListId == null || tasksListId.isEmpty()) {
            // No hay IDs, se devuelve lista vacía con estado éxito
            result.setValue(Resource.success(List.of()));
            return result;
        }

        getCollection()
                .whereIn("id", tasksListId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<TaskModel> tasks = querySnapshot.toObjects(TaskModel.class);
                    result.setValue(Resource.success(tasks));
                    Log.i("FIRESTORE", "Tareas cargadas con éxito");
                })
                .addOnFailureListener(e -> {
                    result.setValue(Resource.error(e.getMessage()));
                    Log.e("FIRESTORE", "Error al obtener tareas por ID: " + e.getMessage());
                });

        return result;
    }
}
