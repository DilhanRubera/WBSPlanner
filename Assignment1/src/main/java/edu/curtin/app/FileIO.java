package edu.curtin.app;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

//Suprressed warning for variable returnParent which is used when its returned.
//Suppressed warning for closing BufferedReader, FileReader and BufferedWriter
@SuppressWarnings({ "PMD.UnusedAssignment", "PMD.CloseResource" })

public class FileIO {

    private static final Logger logger = Logger.getLogger(FileIO.class.getName());

    public static Map<String, Task> loadFile(Map<String, Task> tasks, String filePath) {
        // String filePath = "E:\\Curtin\\YEAR 3\\SEM 1\\OOSE\\Assignment 1\\test.txt";

        try {
            FileReader fr = new FileReader(filePath);
            BufferedReader br = new BufferedReader(fr);
            String line;

            logger.log(Level.INFO, "Reading file and inserting tasks into map");

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";");

                // Adding tasks that have no effort
                if (parts.length == 3) {

                    // Adding tasks with no parent tasks.
                    if (parts[0].isEmpty()) {
                        String taskId = parts[1].trim();
                        String description = parts[2].trim();
                        CompositeTask task = new CompositeTask(taskId, description);
                        tasks.put(taskId, task);
                    } else {

                        // Adding subtasks of parent composite tasks.
                        CompositeTask parentTask = findParentTask(tasks, parts[0].trim());
                        parentTask.addParentTask(parts[0].trim(), parts[1].trim(), parts[2].trim());
                    }
                }
                if (parts.length == 4) {

                    // Adding tasks with effort but no subtask
                    if (parts[0].isEmpty()) {
                        SubTask leafTask = new SubTask(parts[1].trim(), parts[2].trim(),
                                Integer.parseInt(parts[3].trim()));
                        tasks.put(parts[1], leafTask);
                    } else {

                        // Adding subtasks that have effort of parent composite tasks
                        CompositeTask parentTask = findParentTask(tasks, parts[0].trim());
                        parentTask.addSubTask(parts[0].trim(), parts[1].trim(), parts[2].trim(),
                                Integer.parseInt(parts[3].trim()));
                    }
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error while reading file" + e.getMessage());
            logger.log(Level.SEVERE, () -> "Error while reading file" + e.getMessage());
        }

        // Displaying the tasks in the map
        displayMapRecursive(tasks, 0);

        // Calculating the total effort of all tasks
        int effort = calculateTotalEffortRecursive(tasks, 0);
        System.out.println("\nTotal effort: " + effort);

        // Calculating the number of unknown tasks
        int unknownTasks = noOfUnknownTasksRecursive(tasks, 0);
        System.out.println("Number of unknown tasks: " + unknownTasks + "\n");

        return tasks;

    }

    // Recurisve algorithm to find the parent task
    private static CompositeTask findParentTask(Map<String, Task> tasks, String parentTaskId) {
        CompositeTask returnParent = null;

        for (Task task : tasks.values()) {
            if (task instanceof CompositeTask) {
                if (task.getTaskId().equals(parentTaskId)) {
                    return returnParent = (CompositeTask) task;
                }
                if (!((CompositeTask) task).isSubTasksEmpty()) {
                    Map<String, Task> subTasks = ((CompositeTask) task).getSubTasks();
                    returnParent = findParentTask(subTasks, parentTaskId);
                }
            }
        }
        return returnParent;
    }

    // Recursive algorithm to display the tasks in the map
    private static void displayMapRecursive(Map<String, Task> tasks, int level) {
        for (Task task : tasks.values()) {
            if (task instanceof SubTask) {
                SubTask subTask = (SubTask) task;
                if (subTask.getParentTaskId() == null) {
                    System.out
                            .println("  ".repeat(level) + subTask.getTaskId() + " " + subTask.getDescription() + " "
                                    + subTask.getEffort()
                                    + " ");
                } else {
                    System.out.println("  ".repeat(level) +
                            subTask.getParentTaskId() + " " + subTask.getTaskId() + " " + subTask.getDescription() + " "
                            + subTask.getEffort());
                }
            }
            if (task instanceof CompositeTask) {
                CompositeTask compTask = (CompositeTask) task;
                if (compTask.getParentTaskId() == null) {
                    System.out.println("  ".repeat(level) + compTask.getTaskId() + " " + compTask.getDescription());
                } else {
                    System.out
                            .println("  ".repeat(level) + compTask.getParentTaskId() + " " + compTask.getTaskId() + " "
                                    + compTask.getDescription());
                }
                if (!compTask.isSubTasksEmpty()) {

                    displayMapRecursive(compTask.getSubTasks(), level + 1);
                }
            }
        }
    }

    // Recursive algorithm to calculate the total effort of all tasks
    private static int calculateTotalEffortRecursive(Map<String, Task> tasks, int effort) {
        for (Task task : tasks.values()) {
            if (task instanceof SubTask) {
                SubTask subTask = (SubTask) task;
                effort += subTask.getEffort();
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

    // Recursive algorithm to calculate the number of unknown tasks
    private static int noOfUnknownTasksRecursive(Map<String, Task> tasks, int count) {
        for (Task task : tasks.values()) {
            if (task instanceof CompositeTask) {
                CompositeTask compTask = (CompositeTask) task;

                // if (!compTask.isSubTasksEmpty()) {
                // count = noOfUnknownTasksRecursive(compTask.getSubTasks(), count);
                // }
                if (compTask.haveSubTasks()) {
                    count = noOfUnknownTasksRecursive(compTask.getSubTasks(), count);
                } else {
                    count++;

                }
            }
        }
        return count;

    }

    // Method to save the tasks to a file
    public static void saveToFile(Map<String, Task> tasks) {
        List<String> lines = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        System.out.println("Save to file\nWhat is the name of the file you want to save to?");
        String fileName = sc.nextLine();

        try {
            logger.log(Level.INFO, () -> "Saving file to " + fileName);
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));
            lines = getLineListRecursive(tasks, lines);
            for (String line : lines) {

                bw.write(line);
                bw.newLine();
            }
            bw.close();
            System.out.println("Saving File to " + fileName);
        } catch (IOException e) {
            System.out.println("Error while saving file" + e.getMessage());
            logger.log(Level.SEVERE, () -> "Error while saving file" + e.getMessage());
        }
    }

    // Recursive algorithm to add all tasks to an arraylist
    private static List<String> getLineListRecursive(Map<String, Task> tasks, List<String> lines)
            throws IOException {
        for (Task task : tasks.values()) {
            if (task.getParentTaskId() == null) {

                lines.add("; " + task.getTaskId() + " ; " + task.getDescription()
                        + (task.getEffort() != null ? " ; " + task.getEffort() : ""));
            } else {

                lines.add(task.getParentTaskId() + "; " + task.getTaskId() + " ; " + task.getDescription()
                        + (task.getEffort() != null ? " ; " + task.getEffort() : ""));
            }
            if (task instanceof CompositeTask) {
                CompositeTask compTask = (CompositeTask) task;
                if (!compTask.isSubTasksEmpty()) {
                    getLineListRecursive(compTask.getSubTasks(), lines);
                }

            }
        }
        return lines;
    }

}
