package com.example.project.model;

import java.util.Date;

public class Attachment {
    private int attachmentId;
    private int taskId;
    private String filePath;
    private Date uploadedAt;
    private Task task;

    public int getAttachmentId() {
        return attachmentId;
    }

    public void setAttachmentId(int attachmentId) {
        this.attachmentId = attachmentId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Date getUploadedAt() {
        return uploadedAt;
    }

    public void setUploadedAt(Date uploadedAt) {
        this.uploadedAt = uploadedAt;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public Attachment(int attachmentId, int taskId, String filePath, Date uploadedAt, Task task) {
        this.attachmentId = attachmentId;
        this.taskId = taskId;
        this.filePath = filePath;
        this.uploadedAt = uploadedAt;
    }
}
