package com.example.project.model.ServiceModel;

public class LocalChange {
    private int taskId;
    private int operation;

    public LocalChange(int taskId, int operation) {
        this.taskId = taskId;
        this.operation = operation;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getOperation() {
        return operation;
    }

    public void setOperation(int operation) {
        this.operation = operation;
    }
}
