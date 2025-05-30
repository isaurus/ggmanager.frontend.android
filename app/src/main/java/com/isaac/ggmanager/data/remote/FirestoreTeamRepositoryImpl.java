package com.isaac.ggmanager.data.remote;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.isaac.ggmanager.core.Resource;
import com.isaac.ggmanager.domain.model.TeamModel;
import com.isaac.ggmanager.domain.repository.FirebaseAuthRepository;
import com.isaac.ggmanager.domain.repository.FirestoreTeamRepository;
import com.isaac.ggmanager.domain.repository.FirestoreUserRepository;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class FirestoreTeamRepositoryImpl implements FirestoreTeamRepository {

    private final FirebaseFirestore firestore;
    private final FirebaseAuthRepository firebaseAuthRepository;

    @Inject
    public FirestoreTeamRepositoryImpl(FirebaseFirestore firestore,
                                       FirebaseAuthRepository firebaseAuthRepository){
        this.firestore = firestore;
        this.firebaseAuthRepository = firebaseAuthRepository;
    }

    @Override
    public LiveData<Resource<String>> createTeam(TeamModel teamModel) {
        MutableLiveData<Resource<String>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        String userUid = firebaseAuthRepository.getAuthenticatedUser().getUid();
        String teamId = firestore.collection("teams").document().getId();

        teamModel.setId(teamId);
        teamModel.setAdminUid(userUid);
        teamModel.setMembers(new ArrayList<>(Arrays.asList(userUid)));

        firestore.collection("teams")
                .document(teamId)
                .set(teamModel)
                .addOnSuccessListener(unused -> result.setValue(Resource.success(teamId)))
                .addOnFailureListener(e -> result.setValue(Resource.error("Error al crear equipo: " + e.getMessage())));

        return result;
    }

    @Override
    public LiveData<Resource<Boolean>> inviteUserToTeam(String teamId, String email) {
        MutableLiveData<Resource<Boolean>> result = new MutableLiveData<>();
        result.setValue(Resource.loading());

        firestore.collection("users")
                .whereEqualTo("email", email)
                .limit(1)
                .get()
                .addOnSuccessListener(userQuery -> {
                    if (!userQuery.isEmpty()) {
                        DocumentSnapshot userDoc = userQuery.getDocuments().get(0);
                        String userId = userDoc.getId();

                        firestore.collection("teams")
                                .document(teamId)
                                .update("members", FieldValue.arrayUnion(userId))
                                .addOnSuccessListener(aVoid -> {

                                    firestore.collection("users")
                                            .document(userId)
                                            .update("teamId", teamId)
                                            .addOnSuccessListener(v -> result.setValue(Resource.success(true)))
                                            .addOnFailureListener(e -> result.setValue(Resource.error("No se pudo asignar el equipo al usuario")));
                                                result.setValue(Resource.error("No se pudo asignar el equipo al usuario"));
                                })
                                .addOnFailureListener(e -> result.setValue(Resource.error("No se pudo añadir al usuario al equipo")));
                    } else {
                        result.setValue(Resource.error("El usuario no existe"));
                    }
                })
                .addOnFailureListener(e -> result.setValue(Resource.error("Error al buscar el usuario")));

        return result;
    }
}
