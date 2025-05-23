package com.isaac.ggmanager.domain.model;

import java.security.Timestamp;
import java.util.List;

public class TeamModel {
    private String id;
    private String name;
    private String description;
    private String adminUid;
    private List<String> members;
    private Timestamp createdAt;

    public TeamModel(){}

    public TeamModel(String id, String name, String description, String adminUid, List<String> members, Timestamp createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.adminUid = adminUid;
        this.members = members;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAdminUid() {
        return adminUid;
    }

    public void setAdminUid(String adminUid) {
        this.adminUid = adminUid;
    }

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
}
