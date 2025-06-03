package com.isaac.ggmanager.domain.model;

import java.util.Date;

public class TaskModel {
    private String id;
    private String taskTitle;
    private String taskDescription;
    private Date taskDeadLine;
    private String priority;
    private boolean isComplete;

    private String teamId;
    private String memberId;

    public TaskModel(){}

    public TaskModel(String id, String taskTitle, String taskDescription, Date taskDeadLine, String priority, boolean isComplete, String teamId, String memberId) {
        this.id = id;
        this.taskTitle = taskTitle;
        this.taskDescription = taskDescription;
        this.taskDeadLine = taskDeadLine;
        this.priority = priority;
        this.isComplete = isComplete;
        this.teamId = teamId;
        this.memberId = memberId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaskTitle() {
        return taskTitle;
    }

    public void setTaskTitle(String taskTitle) {
        this.taskTitle = taskTitle;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Date getTaskDeadLine() {
        return taskDeadLine;
    }

    public void setTaskDeadLine(Date taskDeadLine) {
        this.taskDeadLine = taskDeadLine;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }
}
