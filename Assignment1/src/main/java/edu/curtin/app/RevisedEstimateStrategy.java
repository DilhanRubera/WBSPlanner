package edu.curtin.app;

import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

// This class implements the ConfigureOption interface and calculates the revised estimate
public class RevisedEstimateStrategy implements ConfigureOption {
    private static final Logger logger = Logger.getLogger(RevisedEstimateStrategy.class.getName());

    @Override
    public int calculateEstimate(int[] estimates) {
        logger.log(Level.INFO, "Calculating estimate using Revised Estimate Strategy");
        Scanner scanner = new Scanner(System.in);
        System.out.println(
                "Discuss among yourselves and come up with a revised estimate.\nThese are the estimates you have provided: ");
        System.out.println("Enter your revised estimate: ");
        int revisedEstimate = scanner.nextInt();
        return revisedEstimate;

    }

}
