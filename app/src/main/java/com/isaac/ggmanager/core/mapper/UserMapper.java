package com.isaac.ggmanager.core.mapper;

import com.google.firebase.auth.FirebaseUser;
import com.isaac.ggmanager.domain.model.UserModel;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class UserMapper {

    // REALMENTE NO NECESITO ESTE, FIREBASE YA ME DA LA OPCIÓN CON documentSnapshot.toObject(UserModel.class)
    public static UserModel fromFirebaseUser(FirebaseUser firebaseUser) {
        if (firebaseUser == null) return null;

        return new UserModel(
                firebaseUser.getUid(),
                firebaseUser.getEmail()
        );
    }

    // En caso de que quieras mapear de Firestore document a UserModel
    public static UserModel fromMap(Map<String, Object> data) {
        UserModel user = new UserModel();
        // user.setFirebaseUid((String) data.get("firebaseUid"));
        user.setEmail((String) data.get("email"));
        user.setName((String) data.get("name"));
        user.setAvatar((String) data.get("profilePicURL"));
        user.setBirthdate((Date) data.get("birthDate"));
        user.setCountry((String) data.get("country"));

        return user;
    }


    /**
     * Mapeo de UserModel para persistencia en Firestore Database. No persiste ni firebaseUid
     * ni email porque es para actualizar un usuario, y no contempla esas dos propiedades.
     * @param user Usuario recibido para actualizar.
     * @return
     */
    public static Map<String, Object> toMap(UserModel user) {
        Map<String, Object> map = new HashMap<>();
        //map.put("firebaseUid", user.getFirebaseUid());
        //map.put("email", user.getEmail());
        map.put("avatar", user.getAvatar());
        map.put("name", user.getName());
        map.put("birthDate", user.getBirthdate());
        map.put("country", user.getCountry());

        return map;
    }

}
