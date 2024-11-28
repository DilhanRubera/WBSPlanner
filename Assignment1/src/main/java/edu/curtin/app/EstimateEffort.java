package edu.curtin.app;

import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.logging.Logger;
import java.util.logging.Level;

//Suppressed warning for variable returnTask which is used once its returned.
@SuppressWarnings({ "PMD.UnusedAssignment" })
public class EstimateEffort {

    // Map to store the ConfigureOption objects
    private Map<Integer, ConfigureOption> configureOptions = new HashMap<>();

    // Logger
    private static final Logger logger = Logger.getLogger(EstimateEffort.class.getName());

    // Method to determine estimate
    public Map<String, Task> determineEstimate(Map<String, Task> tasks, int valueOfN, int reconApproach) {
        initLoaders();
        int[] estimates = new int[valueOfN];
        System.out.println("Estimate Effort");
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the task id: ");
        String taskId = sc.nextLine();

        logger.log(Level.INFO, () -> "Finding task with id: " + taskId);
        Task rTask = findTaskRecursive(tasks, taskId, sc);

        if (rTask == null) {
            System.out.println("Task not found");
            logger.log(Level.INFO, "Task not found");

        } else {
            logger.log(Level.INFO, "Task found.");

            // Get the estimates from the users
            System.out.println("Enter estimate");

            try {
                for (int i = 0; i < valueOfN; i++) {
                    System.out.println("Enter estimate for subtask " + (i + 1) + ": ");
                    estimates[i] = sc.nextInt();
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter an integer");
            }

            for (int i = 0; i < valueOfN; i++) {
                System.out.println("Estimate for subtask by N " + (i + 1) + ": " + estimates[i]);
            }

            // Checking if the inputted estimates are different
            if (isDifferentEsitmates(estimates)) {

                // Get the ConfigureOption object based on the reconciliation approach
                ConfigureOption configureOption = configureOptions.get(reconApproach);

                // Calculate the new estimate using the reconciliation approach
                int newEstimate = configureOption.calculateEstimate(estimates);
                rTask.setEffort(newEstimate);
                System.out.println("Estimates are different. New estimate: " + newEstimate);

            } else {
                // If the estimates are the same, set the effort to the first estimate
                rTask.setEffort(estimates[0]);
                System.out.println("Estimates are the same. New estimate: " + estimates[0]);
            }

        }
        return tasks;
    }

    // Method to initialise loaders
    public void initLoaders() {
        logger.log(Level.INFO, "Initialising loaders");
        configureOptions.put(1, new HighestEstimateStrategy());
        configureOptions.put(2, new MedianEstimateStrategy());
        configureOptions.put(3, new RevisedEstimateStrategy());
    }

    // Method to find task recursively
    public Task findTaskRecursive(Map<String, Task> tasks, String taskId, Scanner sc) {
        Task returnTask = null;
        for (Task task : tasks.values()) {
            if (task.getTaskId().equals(taskId)) {
                if (task instanceof SubTask) {

                    return returnTask = task;
                }
                if (task instanceof CompositeTask) {
                    CompositeTask compTask = (CompositeTask) task;
                    // if (!compTask.isSubTasksEmpty()) {
                    if (compTask.haveSubTasks()) {
                        System.out.println("Sub tasks exist.\nEnter the task id: ");
                        String subTaskId = sc.nextLine();
                        return returnTask = findTaskRecursive(compTask.getSubTasks(), subTaskId, sc);
                    } else {
                        return returnTask = compTask;
                    }
                }
            }
            if (task instanceof CompositeTask) {
                CompositeTask compositeTask = (CompositeTask) task;
                if (!compositeTask.isSubTasksEmpty()) {
                    returnTask = findTaskRecursive(compositeTask.getSubTasks(), taskId, sc);
                    if (returnTask != null) {
                        return returnTask;
                    }
                }
            }

        }
        return returnTask;
    }

    // Method to check if the estimates are different
    public boolean isDifferentEsitmates(int[] estimates) {
        boolean isDifferent = false;
        Set<Integer> estimatesSet = new HashSet<>();
        // for (int i = 0; i < estimates.length; i++) {
        // estimatesSet.add(estimates[i]);
        // }
        for (int estimate : estimates) {
            estimatesSet.add(estimate);
        }
        if (estimatesSet.size() > 2) {
            isDifferent = true;
        }
        return isDifferent;
    }
}
