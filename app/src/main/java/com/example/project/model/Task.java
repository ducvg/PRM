package com.example.project.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Task implements Serializable {
    private int taskId;
    private int userId;
    private String title;
    private String description;
    private Date dueDate;
    private Date createdAt;
    private Date updatedAt;
    private int status;
    private int categoryId;
    private Category category;
    private User user;
    private Set<Attachment> attachments = new HashSet<>();
    private Set<Subtask> subtasks = new HashSet<>();

}
