package edu.curtin.app;

// Task interface which is implemented by the CompositeTask and SubTask classes
// Node class of the Composite pattern
public interface Task {

    String getTaskId();

    String getParentTaskId();

    Integer getEffort();

    void setEffort(int effort);

    String getDescription();

}
