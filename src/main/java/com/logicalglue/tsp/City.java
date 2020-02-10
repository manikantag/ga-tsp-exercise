package com.logicalglue.tsp;

import java.io.Serializable;

/**
 * City Entity
 */
public class City implements Serializable {

    private final String name;
    private final int x;
    private final int y;

    // Constructs a randomly placed city
    public City(String name) {
        this.name = name;
        this.x = (int) (Math.random() * 200);
        this.y = (int) (Math.random() * 200);
    }

    // Constructs a city at chosen x, y location
    public City(String name, int x, int y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    // Gets city's name
    public String getName() {
        return name;
    }

    // Gets city's x coordinate
    public int getX() {
        return this.x;
    }

    // Gets city's y coordinate
    public int getY() {
        return this.y;
    }

    // Gets the distance to given city
    public double distanceTo(City city) {
        int xDistance = Math.abs(getX() - city.getX());
        int yDistance = Math.abs(getY() - city.getY());
        double distance = Math.sqrt((xDistance * xDistance) + (yDistance * yDistance));

        return distance;
    }

    @Override
    public String toString() {
        return name + "[" + getX() + ":" + getY() + "]";
    }
}
