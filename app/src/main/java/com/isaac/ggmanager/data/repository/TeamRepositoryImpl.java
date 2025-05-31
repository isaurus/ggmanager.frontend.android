package com.isaac.ggmanager.data.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.team.TeamRepository;

import java.util.ArrayList;
import java.util.Arrays;

public class TeamRepositoryImpl extends FirestoreRepositoryImpl<TeamModel> implements TeamRepository {

    public TeamRepositoryImpl(FirebaseFirestore firestore) {
        super(firestore);
    }

    @Override
    protected CollectionReference getCollection() {
        return firestore.collection("teams");
    }

    @Override
    protected Class<TeamModel> getModelClass() {
        return TeamModel.class;
    }

    @Override
    protected String getDocumentId(TeamModel model) {
        return model.getId();
    }

    @Override
    public LiveData<Resource<Boolean>> addUserToTeam(String teamId, String userId) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        getCollection()
                .document(teamId)
                .update("members", FieldValue.arrayUnion(userId))
                .addOnSuccessListener(aVoid -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error(e.getMessage())));

        return result;
    }


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
