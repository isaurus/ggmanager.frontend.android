package com.isaac.ggmanager.domain.model;

import java.util.List;

public class TeamModel {
    private String id;
    private String teamName;
    private String teamDescription;
    private String adminUid;
    private List<String> members;       // CREO QUE ES INNECESARIO

    public TeamModel(){}

    public TeamModel(String teamName, String teamDescription){
        this.id = id;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
    }

    public TeamModel(String id, String teamName, String teamDescription, String adminUid, List<String> members) {
        this.id = id;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
        this.adminUid = adminUid;
        this.members = members;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamDescription() {
        return teamDescription;
    }

    public void setTeamDescription(String teamDescription) {
        this.teamDescription = teamDescription;
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
}
