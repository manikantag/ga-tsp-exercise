package com.logicalglue.tsp;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class SelectionPoolAlgo {

    public static void main(String[] args) {

        double[] fits = new double[] {0.1d, .81d, .31d, .4d, .55d, .68d, .71d, .67d, .94d};
//        fits = new double[] {41d, 31d, 24d, 55d, 28d, 71d, 67d, 94d};
//        fits = new double[] {1d, 2d, 3d, 4d, 5d, 6d, 7d, 8d};

        double total = 0;
        for (int i = 0; i < fits.length; i++) {
            total += fits[i];
        }
//        for (int i = 0; i < fits.length; i++) {
//            fits[i] /= total;
//        }

        System.out.println(Arrays.toString(fits) + " --> Sum=" + total);

        Map<Double, Integer> stats = new HashMap<>();

        for (int i = 0; i < 100; i++) {
//          double selected =   test(fits);
            double selected = test2(fits, total);
//            double selected = test3(fits, total);

            stats.putIfAbsent(selected, 0) ;
            stats.put(selected, stats.get(selected) + 1);
        }

        // Print stats
        stats = stats
                .entrySet()
                .stream()
                .sorted(comparingByKey())
                .collect(toMap(Map.Entry::getKey,
                               Map.Entry::getValue,
                               (e1, e2) -> e2,
                               LinkedHashMap::new));
        System.out.println(stats);

    }

    private static double test3(double[] fits, double sum) {
        double rand = Utils.randomProbability();

        for (int i = 0; i < fits.length; i++) {
            if (rand <= fits[i]) {
                System.out.println("Pick=" + fits[i] + ", Rand=" + rand);
                return fits[i];
            }
        }

//        System.out.println("Pick=-1" + ", Rand=" + rand);
        return test3(fits, sum);
    }


    // https://github.com/CodingTrain/Rainbow-Topics/issues/146#issuecomment-451792130

    private static double test2_1(double[] fits, double sum) {
        int rand2 = Utils.randomInt((int)Math.floor(sum)+1);
        double rand =  ThreadLocalRandom.current().nextDouble(0, sum);
        double runningSum = 1;

        for (int i = 0; i < fits.length; i++) {
            runningSum += fits[i];
            if (rand < runningSum) {
//                return fits[i];
                System.out.println("Fittest: " + fits[i] + ", rand=" + rand + ", sum=" + (int)sum);
                return fits[i];
            }
        }
        return -1;
    }

    // https://stackoverflow.com/a/1761646/340290
    private static double test2(double[] fits, double sum) {
        int rand2 = Utils.randomInt((int)Math.floor(sum)+1);
        double rand =  ThreadLocalRandom.current().nextDouble(0, sum);

        for (int i = 0; i < fits.length; i++) {
            if (rand < fits[i]) {
//                return fits[i];
                System.out.println("Fittest: " + fits[i] + ", rand=" + rand + ", sum=" + (int)sum);
                return fits[i];
            }
            rand -= fits[i];

        }
        return -1;
    }

    // https://github.com/CodingTrain/Rainbow-Topics/issues/146#issue-169703456
    private static double test(double[] fits) {
        // Start at 0
        int index = 0;

        // Pick a random number between 0 and 1
        double rand = Utils.randomProbability();
        double randActual = rand;

        // Keep subtracting probabilities until you get less than zero
        // Higher probabilities will be more likely to be fixed since they will
        // subtract a larger number towards zero
        while (rand > 0 && index < fits.length) {
            rand -= fits[index];
            // And move on to the next
            index ++;
        }

        // Go back one
        index--;
//        System.out.println("Fittest: " + population.getFittest().getFitness() + ", picked: " + population.getTour(index).getFitness());
        System.out.println("Fittest: " + fits[index] +", for rand=" + randActual);
        return fits[index];
    }

}
