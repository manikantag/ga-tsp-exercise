package com.logicalglue.tsp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class TSPApplicationTest {

    private final static String CITIES_FILE_PATH = "d:/tours";

    public static void main(String[] args) {
        TSPApplication app = new TSPApplication();

        // Generate new cities or load previous generated cities
//        app.generateCities(100); // Generate cities
//        saveCities(CITIES_FILE_PATH); // Save generated cities for next run

        loadCities(CITIES_FILE_PATH); // Load cities from previously saved file

        // Find the best possible shortest path
        Tour bestTour = app.findShortestPath(100,
                                             500,
                                             10_000,
                                             20,
                                             0.05,
                                             0.005);

        // Print final results
        System.out.println("Finished");
        System.out.println("Final distance: " + bestTour.getDistance());
        System.out.println("Solution:\n" + bestTour);
    }

    private static String saveCities(String filePath) {
        try {
            FileOutputStream fos = new FileOutputStream(filePath);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(TourManager.getDestinationCities());
            oos.close();
            fos.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }


    // best yet: 1613
    @SuppressWarnings("unchecked")
    private static void loadCities(String file) {
        try {
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            TourManager.setDestinationCities((List<City>) ois.readObject());

            ois.close();
            fis.close();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (ClassNotFoundException c) {
            System.err.println("Class not found");
            c.printStackTrace();
        }
    }
}
