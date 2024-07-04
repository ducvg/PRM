package com.example.project.model;

import java.util.Date;

public class Attachment {
    private int attachmentId;
    private int taskId;
    private String filePath;
    private Date uploadedAt;
    private Task task;
}
