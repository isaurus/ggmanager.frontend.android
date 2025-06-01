package com.isaac.ggmanager.data.repository;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.UserModel;
import com.isaac.ggmanager.domain.repository.user.UserRepository;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class UserRepositoryImpl extends FirestoreRepositoryImpl<UserModel> implements UserRepository {

    @Inject
    public UserRepositoryImpl(FirebaseFirestore firestore) {
        super(firestore);
    }

    @Override
    protected CollectionReference getCollection() {
        return firestore.collection("users");
    }

    @Override
    protected Class<UserModel> getModelClass() {
        return UserModel.class;
    }

    @Override
    protected String getDocumentId(UserModel model) {
        return model.getFirebaseUid();
    }

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

    @Override
    public LiveData<Resource<Boolean>> create(UserModel model, String uid) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(uid)
                .set(model)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }

    @Override
    public LiveData<Resource<Boolean>> updateUserTeam(String userId, String teamId) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(userId)
                .update("teamId", teamId)
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> {
                    result.setValue(Resource.error(e.getMessage()));
                    Log.e("PRUEBA", "Error Firestore en updateUserTeam." + e.getMessage());
                });

        return result;
    }

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
