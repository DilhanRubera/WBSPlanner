package edu.curtin.app;

import java.util.Map;

public class Uitilities {

    // Display the map of tasks recursively
    public static void displayMapRecursive(Map<String, Task> tasks, int level) {
        for (Task task : tasks.values()) {
            if (task.getParentTaskId() == null) {
                if (task.getEffort() == null) {
                    System.out.println("  ".repeat(level) + task.getTaskId() + " : " + task.getDescription());
                } else {
                    System.out.println("  ".repeat(level) + task.getTaskId() + " : " + task.getDescription() + " = "
                            + task.getEffort());
                }
            } else {
                if (task.getEffort() == null) {
                    System.out.println("  ".repeat(level) + task.getTaskId() + " : " + task.getDescription());
                } else {
                    System.out.println("  ".repeat(level) + task.getTaskId() + " : " + task.getDescription() + " = "
                            + task.getEffort());
                }
            }
            if (task instanceof CompositeTask) {
                CompositeTask compTask = (CompositeTask) task;
                if (!compTask.isSubTasksEmpty()) {
                    displayMapRecursive(compTask.getSubTasks(), level + 1);
                }
            }
        }
    }

    // Display the total effort of all tasks
    public static void displayTotalEffort(Map<String, Task> tasks, int effort) {
        int totalEffort = calculateTotalEffortRecursive(tasks, 0);
        System.out.println("Total effort: " + totalEffort);
    }

    // Display the number of unknown tasks
    public static void displayNoOfUnknownTasks(Map<String, Task> tasks, int effort) {
        int unknownTasks = calculateNoOfUnknownTasksRecursive(tasks, 0);
        System.out.println("Number of unknown tasks: " + unknownTasks);
    }

    // Calculate the total effort of all tasks recursively
    private static int calculateTotalEffortRecursive(Map<String, Task> tasks, int effort) {
        for (Task task : tasks.values()) {
            if (task.getEffort() != null) {
                effort += task.getEffort();
            }
            if (task instanceof CompositeTask) {
                CompositeTask compTask = (CompositeTask) task;
                if (!compTask.isSubTasksEmpty()) {
                    effort = calculateTotalEffortRecursive(compTask.getSubTasks(), effort);
                }
            }
        }
        return effort;
    }

    // Calculate the number of unknown tasks recursively
    private static int calculateNoOfUnknownTasksRecursive(Map<String, Task> tasks, int count) {
        for (Task task : tasks.values()) {
            if (task instanceof CompositeTask) {
                CompositeTask compTask = (CompositeTask) task;

                // if (!compTask.isSubTasksEmpty()) {
                if (compTask.haveSubTasks()) {
                    count = calculateNoOfUnknownTasksRecursive(compTask.getSubTasks(), count);
                } else {
                    if (compTask.getEffort() == null) {
                        count++;
                    }
                }
            }
        }

        return count;
    }
}
