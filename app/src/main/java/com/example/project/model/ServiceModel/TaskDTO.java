package com.example.project.model.ServiceModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class TaskDTO {
    @SerializedName("taskId")
    @Expose
    private int taskId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("DueDate")
    @Expose
    private Date dueDate;
    @SerializedName("Status")
    @Expose
    private int status;
    @SerializedName("CategoryId")
    @Expose
    private int categoryId;
    @SerializedName("Operation")
    @Expose
    private boolean operation;

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public boolean getOperation() {
        return operation;
    }

    public void setOperation(boolean operation) {
        this.operation = operation;
    }

    public TaskDTO() {
    }

    public TaskDTO(int taskId, String title, String description, Date dueDate, int status, int categoryId, boolean operation) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.categoryId = categoryId;
        this.operation = operation;
    }
}
