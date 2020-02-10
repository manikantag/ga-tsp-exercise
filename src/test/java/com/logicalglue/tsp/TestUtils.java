package com.logicalglue.tsp;

import java.util.ArrayList;
import java.util.List;

/**
 * Test utils.
 *
 * @author Manikanta G
 */
public class TestUtils {

    /**
     * Creates new {@link Tour} of given number of cities.
     *
     * @param numOfCities Number of cities in the tour
     * @return New {@link Tour} instance
     */
    public static Tour createNewTour(int numOfCities) {
        return createNewTour("City_",
                             numOfCities);
    }

    public static Tour createNewTour(String cityNamePrefix,
                                     int numOfCities) {
        List<City> cities = new ArrayList<>();

        for (int i = 0; i < numOfCities; i++) {
            cities.add(new City(cityNamePrefix + i));
        }

        return new Tour(cities);
    }

}
