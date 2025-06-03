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

public class TaskRepositoryImpl extends FirestoreRepositoryImpl<TaskModel> implements TaskRepository {

    public TaskRepositoryImpl(FirebaseFirestore firestore) {
        super(firestore);
    }

    @Override
    protected CollectionReference getCollection() {
        return firestore.collection("tasks");
    }

    @Override
    protected Class<TaskModel> getModelClass() {
        return TaskModel.class;
    }

    @Override
    protected String getDocumentId(TaskModel model) {
        return model.getId();
    }

    @Override
    public LiveData<Resource<List<TaskModel>>> getUserTasks(List<String> tasksListId) {
        MutableLiveData<Resource<List<TaskModel>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        if (tasksListId == null || tasksListId.isEmpty()) {
            result.setValue(Resource.success(List.of())); // o Collections.emptyList()
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
