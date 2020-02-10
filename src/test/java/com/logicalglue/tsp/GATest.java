package com.logicalglue.tsp;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GATest {

    private GA geneticAlgorithm = null;

    @Before
    public void setup() {
        geneticAlgorithm = new GA(20, 0.05, 0.005);
    }


    @Test
    public void testCrossover() {
        // Setup
        Tour tour1 = TestUtils.createNewTour("Tour1_City_", 10);
        Tour tour2 = TestUtils.createNewTour("Tour2_City_", 10);

        // SUT
        int startPos = 5;
        int endPos = 9;
        Tour child = geneticAlgorithm.crossover(tour1, tour2, startPos, endPos);

        // Assert
        Assert.assertEquals("Child tour size should be same as parents after crossover",
                            tour1.tourSize(),
                            child.tourSize());

        String[] expectedOrder = new String[]{"Tour2_City_0", "Tour2_City_1", "Tour2_City_2",
                "Tour2_City_3", "Tour2_City_4", "Tour2_City_5", "Tour1_City_6", "Tour1_City_7",
                "Tour1_City_8", "Tour2_City_6"};

        for (int i = 0; i < child.tourSize(); i++) {
            Assert.assertEquals("City at " + i + " is incorrect ",
                                expectedOrder[i],
                                child.getCity(i)
                                     .getName());
        }
    }
}
