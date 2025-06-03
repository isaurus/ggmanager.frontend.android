package com.isaac.ggmanager.data.repository.task;

import androidx.lifecycle.LiveData;

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
        return null;
    }
}
