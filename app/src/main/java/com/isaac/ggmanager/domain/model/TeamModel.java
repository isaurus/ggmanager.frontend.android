package com.isaac.ggmanager.domain.model;

import java.util.List;

public class TeamModel {
    private String id;
    private String teamName;
    private String teamDescription;
    private List<String> members;
    private List<String> teamTasksId;

    public TeamModel(){}

    public TeamModel(String id, String teamName, String teamDescription, List<String> members) {
        this.id = id;
        this.teamName = teamName;
        this.teamDescription = teamDescription;
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

    public List<String> getMembers() {
        return members;
    }

    public void setMembers(List<String> members) {
        this.members = members;
    }

    public List<String> getTeamTasksId() {
        return teamTasksId;
    }

    public void setTeamTasksId(List<String> teamTasksId) {
        this.teamTasksId = teamTasksId;
    }
}
