package com.example.project.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.Date;

public class Subtask {
    @SerializedName("SubtaskId")
    @Expose
    private int subtaskId;
    @SerializedName("TaskId")
    @Expose
    private int taskId;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("Description")
    @Expose
    private String description;
    @SerializedName("Status")
    @Expose
    private LocalDateTime status;
    @SerializedName("CreatedAt")
    @Expose
    private LocalDateTime createdAt;
    @SerializedName("UpdatedAt")
    @Expose
    private LocalDateTime updatedAt;

    public Subtask(int subtaskId, int taskId, String title, String description, LocalDateTime status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.subtaskId = subtaskId;
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getSubtaskId() {
        return subtaskId;
    }

    public void setSubtaskId(int subtaskId) {
        this.subtaskId = subtaskId;
    }

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

    public LocalDateTime getStatus() {
        return status;
    }

    public void setStatus(LocalDateTime status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
