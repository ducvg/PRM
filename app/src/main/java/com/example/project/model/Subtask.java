package com.example.project.model;

import java.util.Date;

public class Subtask {
    private int subtaskId;
    private int taskId;
    private String title;
    private String description;
    private String status;
    private Date createdAt;
    private Date updatedAt;
    private Task task;

    public Subtask(int subtaskId, int taskId, String title, String description, String status, Date createdAt, Date updatedAt) {
        this.subtaskId = subtaskId;
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
