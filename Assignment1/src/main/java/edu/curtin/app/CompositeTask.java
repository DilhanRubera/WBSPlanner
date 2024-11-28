package edu.curtin.app;

import java.util.Map;
import java.util.TreeMap;

//Suppressed warning for variable subTasks which is not used until a task is added.
@SuppressWarnings("PMD.UnusedAssignment")

// CompositeTask class which implements Task interface
// Composite class of the Composite pattern
public class CompositeTask implements Task {
    private Map<String, Task> subTasks = new TreeMap<>();
    private String parentId;
    private String taskId;
    private String taskDescription;
    private Integer effort;

    // Constructor for tasks with no parent
    public CompositeTask(String taskId, String taskDescription) {
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.subTasks = new TreeMap<>();
    }

    // Constructor for tasks with parent
    public CompositeTask(String parentId, String taskId, String taskDescription) {
        this.parentId = parentId;
        this.taskId = taskId;
        this.taskDescription = taskDescription;
        this.subTasks = new TreeMap<>();
        this.effort = null;
    }

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public String getParentTaskId() {
        return parentId;
    }

    public Map<String, Task> getSubTasks() {
        return subTasks;
    }

    @Override
    public String getDescription() {
        return taskDescription;
    }

    @Override
    public Integer getEffort() {
        return effort;
    }

    @Override
    public void setEffort(int effort) {
        this.effort = effort;
    }

    public boolean isSubTasksEmpty() {
        boolean isEmpty = true;
        if (!subTasks.isEmpty()) {
            isEmpty = false;
        }
        return isEmpty;
    }

    public boolean haveSubTasks() {
        boolean haveSubTasks = true;
        if (subTasks.isEmpty()) {
            haveSubTasks = false;
        }
        return haveSubTasks;
    }

    // Add subtask to the parent task
    public void addSubTask(
            String parentId,
            String taskId,
            String taskDescription, int effort) {
        Task task = new SubTask(parentId, taskId, taskDescription, effort);
        subTasks.put(taskId, task);
    };

    // Add parent task to the parent task
    public void addParentTask(
            String parentId,
            String taskId,
            String taskDescription) {
        Task task = new CompositeTask(parentId, taskId, taskDescription);
        subTasks.put(taskId, task);
    };

}
