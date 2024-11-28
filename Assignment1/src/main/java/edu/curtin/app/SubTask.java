package edu.curtin.app;

// SubTask class which implements Task interface
// Leaf class of the Composite pattern
public class SubTask implements Task {
    private String parentId;
    private String taskId;
    private String description;
    private int effort;

    // Constructor for tasks with no parent
    public SubTask(String taskId, String description, int effort) {
        this.taskId = taskId;
        this.description = description;
        this.effort = effort;
    }

    // Constructor for tasks with parent
    public SubTask(String parentId, String taskId, String description, int effort) {
        this.parentId = parentId;
        this.taskId = taskId;
        this.description = description;
        this.effort = effort;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getParentTaskId() {
        return parentId;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public Integer getEffort() {
        return effort;
    }

    @Override
    public void setEffort(int effort) {
        this.effort = effort;
    }

}
