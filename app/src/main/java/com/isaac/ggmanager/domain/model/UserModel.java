package com.isaac.ggmanager.domain.model;

import java.util.Date;

import javax.annotation.Nullable;

/**
 * Modelo de dominio para un usuario.
 */
public class UserModel {
    private String firebaseUid;
    private String avatar;
    private String name;
    private String email;
    private Date birthdate;
    private String country;
    @Nullable private String teamId;  // El usuario puede pertenecer a un grupo, del cual recoge su ID. Si es null, no pertenece a ningún grupo.

    /**
     * Constructor para deserialización automática con Firestore.
     */
    public UserModel(){}

    /**
     * Constructor para persistir el usuario en Firestore cuando se registra o se logea por primera
     * vez en la aplicación. Establece algunas propiedades por defecto para generar consistencia con
     * Firestore Database.
     *
     * @param firebaseUid El UID del usuario recién creado en Firebase Auth.
     * @param email El correo que ha utilizado el usuario para registrarse/logearse.
     */
    public UserModel(String firebaseUid, String email){
        this.firebaseUid = firebaseUid;
        this.email = email;
        this.avatar = "ic_avatar_avocado";
        this.name = "Por definir";
        this.birthdate = null;
        this.country = "Por definir";
    }

    /**
     * Constructor para completar/editar los detalles del usuario desde su perfil.
     *
     * @param avatar
     * @param name
     * @param birthdate
     * @param country
     */
    public UserModel(String avatar, String name, Date birthdate, String country, String teamId) {
        this.avatar = avatar;
        this.name = name;
        this.birthdate = birthdate;
        this.country = country;
        this.teamId = teamId;
    }

    public String getFirebaseUid() {
        return firebaseUid;
    }

    public void setFirebaseUid(String firebaseUid) {
        this.firebaseUid = firebaseUid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}