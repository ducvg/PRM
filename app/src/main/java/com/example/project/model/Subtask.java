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
}
