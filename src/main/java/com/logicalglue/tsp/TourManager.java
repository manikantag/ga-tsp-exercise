package com.logicalglue.tsp;

import java.util.ArrayList;
import java.util.List;

public class TourManager {

    // Holds our cities
    private static List<City> destinationCities = new ArrayList<>();

    // Adds a destination city
    public static void addCity(City city) {
        destinationCities.add(city);
    }

    // Get a city
    public static City getCity(int index) {
        return (City) destinationCities.get(index);
    }

    // Get the number of destination cities
    public static int numberOfCities() {
        return destinationCities.size();
    }

    public static List<City> getDestinationCities() {
        return destinationCities;
    }

    public static void setDestinationCities(List<City> destinationCities) {
        TourManager.destinationCities = destinationCities;
    }
}
