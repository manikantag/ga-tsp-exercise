package com.logicalglue.tsp;

import java.util.Random;

public class TSPApplication {

    private final static int MAX_COORDINATE = 200;
    private final static int NUM_CITIES = 100;
    private final static int POPULATION_SIZE = 500;

    private final static int TOURNAMENT_SIZE = 20;
    private final static double ELITISM_RATE = 0.05;
    private final static double MUTATION_RATE = 0.005;
    private final static int NUMBER_GENERATIONS = 10000;


    public static void main(String[] args) {
        TSPApplication app = new TSPApplication();

        // Generate new cities
        app.generateCities(NUM_CITIES);

        // Find the best possible shortest path
        Tour bestTour = app.findShortestPath(NUM_CITIES,
                                             POPULATION_SIZE,
                                             NUMBER_GENERATIONS,
                                             TOURNAMENT_SIZE,
                                             ELITISM_RATE,
                                             MUTATION_RATE);

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + bestTour.getDistance());
        System.out.println("Solution:\n" + bestTour);
    }


    public void generateCities(int numOfCities) {
        Random r = new Random(1);

        for (int i = 1; i <= numOfCities; i++) {
            int x = r.nextInt(MAX_COORDINATE);
            int y = r.nextInt(MAX_COORDINATE);
            TourManager.addCity(new City(i + "", x, y));
//            System.out.println("City["+i+"] with coordinates " + x + ":" + y);
        }
    }


    public Tour findShortestPath(int numOfCities,
                                 int populationSize,
                                 int numOfGenerations,
                                 int tournamentSize,
                                 double elitismRate,
                                 double mutationRate) {
        // Initialize population
        Population pop = new Population(populationSize, numOfCities);
        int initialDistance = pop.getFittest()
                                 .getDistance();
        System.out.println("Initial distance: " + initialDistance);

        GA geneticAlgorithm = new GA(tournamentSize,
                                     elitismRate,
                                     mutationRate);

        for (int i = 1; i <= numOfGenerations; i++) {
            pop = geneticAlgorithm.evolvePopulation(pop);

            System.out.println("Generation " + i
                                       + ": shortest distance within population = "
                                       + pop.getFittest()
                                            .getDistance()
                                       + ".  aggregate distance = "
                                       + pop.getTotalDistance());
        }

        // Print final results
        System.out.println("Initial distance: " + initialDistance);
        System.out.println("Best ever tour: " + geneticAlgorithm.getBestEverDistance());

        return pop.getFittest();
    }

}
