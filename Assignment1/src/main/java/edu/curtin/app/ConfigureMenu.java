package edu.curtin.app;

import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

public class ConfigureMenu extends Menu {

    // Menu options
    private String menuOptions[] = { "Change value of N", "Change reconciliation approach", "Exit" };

    // Default values
    private int valueOfN = 3;
    private int reconApproach = 3;

    // Logger
    private static final Logger logger = Logger.getLogger(ConfigureMenu.class.getName());

    // Display configure menu- Template Method of the Template pattern
    @Override
    public void displayMenu(String[] menuOptions) {
        super.displayMenu(menuOptions);
    }

    // Run configure menu - Hook method of the Template pattern
    @Override
    protected void runMenu(Map<String, Task> tasks) {

        logger.log(Level.INFO, "Running configure menu");
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        try {
            while (running) {
                displayMenu(menuOptions);

                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        setValueOfN(changeValueOfN(getValueOfN(), sc));
                        break;
                    case 2:
                        setReconApproach(changeReconciliationApproach(getReconApproach(), sc));
                        break;
                    case 3:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid input. Please enter a number between 1 and 3");
                        logger.log(Level.WARNING, "Invalid input. Please enter a number between 1 and 3");
                        break;

                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer between 1 and 3");
        }
    }

    // Method to change value of N
    private int changeValueOfN(int valueOfN, Scanner sc) {
        try {
            logger.log(Level.INFO, "Changing value of N");
            System.out.println("Enter the value of N. (Must be a POSITIVE INTEGER!) ");
            int newValueOfN = sc.nextInt();
            System.out.println("Value of N is now " + newValueOfN);
            valueOfN = newValueOfN;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a POSITIVE integer");
            logger.log(Level.WARNING, "Invalid input. Please enter a POSITIVE integer");
        }
        return valueOfN;
    }

    // Method to change reconciliation approach
    private int changeReconciliationApproach(int reconApproach, Scanner sc) {
        try {
            logger.log(Level.INFO, "Changing reconciliation approach");
            System.out.println("Choose new reconciliation approach");
            System.out.println(
                    "1. Take Highest Estimate\n2. Take Median Estimate\n3. Discuss and enter a single revised estimate");
            int newReconApproach = sc.nextInt();
            System.out.println("Reconciliation approach is now " + newReconApproach);
            reconApproach = newReconApproach;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter an integer between 1 and 3");
            logger.log(Level.WARNING, "Invalid input. Please enter an integer between 1 and 3");
        }
        return reconApproach;
    }

    public int getValueOfN() {
        return valueOfN;
    }

    public int getReconApproach() {
        return reconApproach;
    }

    public void setValueOfN(int valueOfN) {
        this.valueOfN = valueOfN;
    }

    public void setReconApproach(int reconApproach) {
        this.reconApproach = reconApproach;
    }

}
