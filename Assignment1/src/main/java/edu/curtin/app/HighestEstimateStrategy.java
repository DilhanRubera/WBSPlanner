package edu.curtin.app;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

// This class implements the ConfigureOption interface and calculates the highest estimate
public class HighestEstimateStrategy implements ConfigureOption {
    private static final Logger logger = Logger.getLogger(HighestEstimateStrategy.class.getName());

    @Override
    public int calculateEstimate(int[] estimates) {
        logger.log(Level.INFO, "Calculating estimate using Highest Estimate Strategy");
        System.out.println("Highest Estimate will be chosen as the effort");
        Arrays.sort(estimates);
        int estimate = estimates[estimates.length - 1];
        return estimate;
    }

}
