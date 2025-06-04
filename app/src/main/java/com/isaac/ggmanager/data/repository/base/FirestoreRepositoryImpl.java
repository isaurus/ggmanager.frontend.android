package com.isaac.ggmanager.data.repository.base;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.repository.base.FirestoreRepository;

import java.util.ArrayList;
import java.util.List;

public abstract class FirestoreRepositoryImpl<T> implements FirestoreRepository<T> {

    protected final FirebaseFirestore firestore;

    public FirestoreRepositoryImpl(FirebaseFirestore firestore){
        this.firestore = firestore;
    }

    protected abstract CollectionReference getCollection();
    protected abstract Class<T> getModelClass();
    protected abstract String getDocumentId(T model);


    @Override
    public LiveData<Resource<T>> getById(String id) {
        MutableLiveData<Resource<T>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(id)
                .get()
                .addOnSuccessListener(snapshot -> {
                    if (snapshot.exists()){
                        T model = snapshot.toObject(getModelClass());
                        result.setValue(Resource.success(model));
                    } else {
                        result.setValue(Resource.success(null));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    @Override
    public LiveData<Resource<List<T>>> getAll() {
        MutableLiveData<Resource<List<T>>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection().get()
                .addOnSuccessListener(snapshot -> {
                    List<T> items = new ArrayList<>();
                    for (var doc : snapshot.getDocuments()) {
                        T model = doc.toObject(getModelClass());
                        if (model != null) items.add(model);
                    }
                    result.setValue(Resource.success(items));
                })
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    /*
    @Override
    public LiveData<Resource<Boolean>> create(T model) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());


            getCollection()
                    .document()
                    .set(model)
                    .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                    .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

     */

    @Override
    public LiveData<Resource<Boolean>> update(T model) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String id = getDocumentId(model);
        if (id == null || id.isEmpty()) {
            result.setValue(Resource.error("ID inválido"));
            return result;
        }
        getCollection().document(id).set(model)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    @Override
    public LiveData<Resource<Boolean>> deleteById(String id) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection().document(id).delete()
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }
}
