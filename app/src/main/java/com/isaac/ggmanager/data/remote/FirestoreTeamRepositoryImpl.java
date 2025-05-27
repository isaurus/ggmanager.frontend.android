package com.isaac.ggmanager.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.FirestoreTeamRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirestoreTeamRepositoryImpl implements FirestoreTeamRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public FirestoreTeamRepositoryImpl(FirebaseFirestore firestore){
        this.firestore = firestore;
    }

    @Override
    public LiveData<Resource<Boolean>> createTeam(TeamModel teamModel) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String teamId = firestore.collection("teams").document().getId();
        teamModel.setId(teamId);

        firestore.collection("teams")
                .document(teamId)
                .set(teamModel)
                .addOnSuccessListener(unused -> result.setValue(Resource.success(true)))
                .addOnFailureListener(e -> result.setValue(Resource.error("Error al crear equipo: " + e.getMessage())));

        return result;
    }

    @Override
    public LiveData<Resource<Boolean>> inviteUserToTeam(String teamId, String userId) {
        return null;
    }
}
