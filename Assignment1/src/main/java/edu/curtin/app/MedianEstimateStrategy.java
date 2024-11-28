package edu.curtin.app;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

// This class implements the ConfigureOption interface and calculates the median estimate
public class MedianEstimateStrategy implements ConfigureOption {
    private static final Logger logger = Logger.getLogger(MedianEstimateStrategy.class.getName());

    @Override
    public int calculateEstimate(int[] estimates) {
        logger.log(Level.INFO, "Calculating estimate using Median Estimate Strategy");
        System.out.println("Median Estimate will be chosen as the effort");
        int estimate;
        Arrays.sort(estimates);
        int i = estimates.length;
        if (i % 2 == 0) {
            int num1 = estimates[i / 2 - 1];
            int num2 = estimates[i / 2];
            estimate = (num1 + num2) / 2;

        } else {
            estimate = estimates[i / 2];
        }
        return estimate;

    }

}
