package com.logicalglue.tsp;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Utils {
    private static final Random random = new Random();

    /**
     * Returns a random number from {@code 0} (inclusive) to {@code max} (exclusive) number.
     *
     * @param max Upper bound (exclusive) for the random number
     * @return Next random number
     */
    public static int randomInt(int max) {
        return random.nextInt(max);
    }

    /**
     * Returns a random number from {@code min} (inclusive) to {@code max} (exclusive) number.
     *
     * @param min Lower bound (inclusive) for the random number
     * @param max Upper bound (exclusive) for the random number
     * @return Next random number
     */
    public static int randomInt(int min, int max) {
        return min + random.nextInt(max - min);
    }

    /**
     * Returns a random {@code double} between {@code 0.0} (inclusive) and {@code 1.0} (exclusive).
     *
     * @return Next random number
     */
    public static double randomProbability() {
        return random.nextDouble();
    }

    /**
     * Returns a random {@code double} between {@code 0.0} (inclusive) and {@code max} (exclusive).
     *
     * <p>Implementation is multi-thread safe</p>
     *
     * @param max Upper bound for random value
     * @return Next random number
     */
    public static double randomProbability(double max) {
        return ThreadLocalRandom.current()
                                .nextDouble(0,
                                            max);
    }

}
