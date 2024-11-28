package edu.curtin.app;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class MainMenu extends Menu {

    // Instantiate objects
    private static EstimateEffort estimateEffort = new EstimateEffort();
    private ConfigureMenu configureMenu = new ConfigureMenu();

    // Logger
    private static final Logger logger = Logger.getLogger(MainMenu.class.getName());

    // Main menu options
    private String[] menuOptions = { "Estimate Effort", "Configure", "Display Work Breakdown Structure", "Save WBS",
            "Quit" };

    // Display main menu - Template Method of the Template pattern
    @Override
    public void displayMenu(String[] menuOptions) {
        super.displayMenu(menuOptions);

    }

    // Run main menu - Hook method of the Template pattern
    @Override
    protected void runMenu(Map<String, Task> tasks) {
        boolean running = true;
        Scanner sc = new Scanner(System.in);
        logger.log(Level.INFO, "Running main menu");
        try {
            while (running) {
                displayMenu(menuOptions);

                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        tasks = estimateEffort.determineEstimate(tasks, configureMenu.getValueOfN(),
                                configureMenu.getReconApproach());
                        break;
                    case 2:
                        configureMenu.runMenu(tasks);
                        break;
                    case 3:
                        displayWBS(tasks);
                        break;
                    case 4:
                        FileIO.saveToFile(tasks);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 5");
                        break;
                }

            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer");

        }
    }

    // Display WBS
    public void displayWBS(Map<String, Task> tasks) {
        logger.log(Level.INFO, "Displaying WBS");
        Uitilities.displayMapRecursive(tasks, 0);
        Uitilities.displayTotalEffort(tasks, 0);
        Uitilities.displayNoOfUnknownTasks(tasks, 0);

    }
}
