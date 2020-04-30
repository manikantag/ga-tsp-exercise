package com.logicalglue.tsp;

public class GA {

    private double mutationRate;
    private int tournamentSize;
    private double elitismRate;

    private Tour bestEverTour = null;
    private int bestEverDistance = Integer.MAX_VALUE;

    public GA(int tournamentSize,
              double elitismRate,
              double mutationRate) {
        this.tournamentSize = tournamentSize;
        this.elitismRate = elitismRate;
        this.mutationRate = mutationRate;
    }

    public void setBestEver(Tour bestEverTour) {
        if (bestEverTour == null) return;

        this.bestEverTour = bestEverTour;
        this.bestEverDistance = bestEverTour.getDistance();
    }

    public int getBestEverDistance() {
        return bestEverDistance;
    }

    /**
     * Evolves a population over one generation
     */
    public Population evolvePopulation(Population population) {
        Population newPopulation = new Population(population.populationSize());

        // Crossover population
        // Loop over the new population's size and create individuals from
        // current population
        for (int i = 0; i < newPopulation.populationSize(); i++) {
            // Select parents
            Tour parent1 = selectFittestInTournament(population, tournamentSize);

            if (Utils.randomProbability() < elitismRate) {
                // Promote elitism - send few current generation
                // members as it is to next generation
                newPopulation.saveTour(i, parent1);
            } else {
                Tour parent2 = selectFittestInTournament(population, tournamentSize);

                // Crossover parents
                // Get start and end sub tour positions for parent1's tour
                int startPos = (int) (Math.random() * parent1.tourSize());
                int endPos = (int) (Math.random() * parent1.tourSize());

                Tour child = crossover(parent1,
                                       parent2,
                                       startPos,
                                       endPos);

                // Add child to new population
                newPopulation.saveTour(i, child);
            }
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = 0; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }

        return newPopulation;
    }


    /**
     * @param population     Current generation population
     * @param tournamentSize Tournament size
     * @return Tournament's fittest member
     * @see <a href="https://en.wikipedia.org/wiki/Tournament_selection">
     * Tournament selection</a>
     */
    public Tour selectFittestInTournament(Population population,
                                          int tournamentSize) {
        Population tournamentEntries = new Population(tournamentSize);

        int populationSize = population.populationSize();

        for (int i = 0; i < tournamentSize; i++) {
            int newEntryPosition = Utils.randomInt(populationSize);
            tournamentEntries.saveTour(i, population.getTour(newEntryPosition));
        }

        return tournamentEntries.getFittest();
    }


/*    public void promoteElitistToNextGeneration(Population newPopulation) {
        Tour newGenerationFittest = newPopulation.getFittest();

        if (newGenerationFittest.getDistance() < bestEverDistance) {
            setBestEver(newGenerationFittest);
        } else {
            newPopulation.saveTour(0, bestEverTour);
        }
    }


    // Evolves a population over one generation
    public Population evolvePopulation_2(Population pop) {
        Population newPopulation = new Population(pop.populationSize(), false);

        double sumOfAllFitness = 0;

        for (int i = 0; i < pop.populationSize(); i++) {
            sumOfAllFitness += pop.getTour(i)
                                  .getFitness();
        }

        // Crossover population
        // Loop over the new population's size and create individuals from
        // Current population
        for (int i = 0; i < newPopulation.populationSize(); i++) {
            // Select parents
            // Few times select this generation's fittest as one parent,
            // and other parent by fitness probability
            Tour parent1 = null;
            Tour parent2 = null;

            if (Math.random() < 0.1) {
                parent1 = pop.getFittest();
            } else {
                parent1 = selectParentByFitnessProbability(pop, sumOfAllFitness);
            }

            if (Math.random() < 0.1) {
                parent2 = pop.getFittest();
            } else {
                parent2 = selectParentByFitnessProbability(pop, sumOfAllFitness);
            }

            // Crossover parents
            Tour child = crossover(parent1, parent2);
            // Add child to new population
            newPopulation.saveTour(i, child);
        }

        // Mutate the new population a bit to add some new genetic material
        for (int i = 0; i < newPopulation.populationSize(); i++) {
            mutate(newPopulation.getTour(i));
        }

        promoteElitistToNextGeneration(newPopulation);

        return newPopulation;
    }


    *//**
     * Select a potential parent based on fitness (higher the fitness,
     * higher the probability of picking it)
     *
     * @param population      {@link Population} instance
     * @param sumOfAllFitness Sum of population fitness
     * @return Member from the given population selected by it's fitness probability
     * @see <a href="https://en.wikipedia.org/wiki/Fitness_proportionate_selection">
     * Fitness proportionate selection</a>
     *//*
    public Tour selectParentByFitnessProbability(Population population,
                                                 double sumOfAllFitness) {
        double probability = Utils.randomProbability(sumOfAllFitness);

        for (int i = 0; i < population.populationSize(); i++) {
            Tour currentTour = population.getTour(i);

            if (probability < currentTour.getFitness()) {
                return currentTour;
            }

            probability -= currentTour.getFitness();
        }

        return selectParentByFitnessProbability(population, sumOfAllFitness);
    }*/


    /**
     * Applies crossover to a set of parents and creates offspring
     */
    public Tour crossover(Tour parent1,
                          Tour parent2,
                          int startPos,
                          int endPos) {
        // Create new child tour
        Tour child = new Tour(parent1.tourSize());

        // Loop and add the sub tour from parent1 to our child
        for (int i = 0; i < child.tourSize(); i++) {
            // If our start position is less than the end position
            if (i > startPos && i < endPos) {
                child.setCity(i, parent1.getCity(i));
            } // If our start position is larger
            else if (startPos > endPos) {
                if (!(i < startPos && i > endPos)) {
                    child.setCity(i, parent1.getCity(i));
                }
            }
        }

        // Loop through parent2's city tour
        for (int i = 0; i < parent2.tourSize(); i++) {
            // If child doesn't have the city add it
            if (!child.containsCity(parent2.getCity(i))) {
                // Loop to find a spare position in the child's tour
                for (int ii = 0; ii < child.tourSize(); ii++) {
                    // Spare position found, add city
                    if (child.getCity(ii) == null) {
                        child.setCity(ii, parent2.getCity(i));
                        break;
                    }
                }
            }
        }
        return child;
    }

    private void mutate(Tour tour) {
        // Mutate a tour using swap mutation
        Mutation.doSwapMutation(tour.getCities(), mutationRate);

        // Displace subset of the tour to a random position
        Mutation.doDisplacementMutation(tour.getCities(), mutationRate);

        // Reverse the subset of cities in the tour
        Mutation.doInversionMutation(tour.getCities(), mutationRate);
    }

}
