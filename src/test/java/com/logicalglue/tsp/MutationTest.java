package com.logicalglue.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * {@link Mutation} tests.
 */
public class MutationTest {

    @Test
    public void testDoSwapMutation() {
        // Setup
        Tour tour = TestUtils.createNewTour("City_", 10);
//        System.out.println("Before: " + tour.getCities());

        List<City> backup = new ArrayList<>(tour.getCities());

        // SUT
        Mutation.doSwapMutation(tour.getCities(), 1);

        // Assert
//        System.out.println("Before: " + tour.getCities());
        Assert.assertEquals("Tour size after swap mutation should be same",
                            backup.size(),
                            tour.getCities()
                                .size());

        Assert.assertNotEquals("After swap mutation, cities order should change",
                               backup,
                               tour.getCities());

    }

    @Test
    public void testDoDisplacementMutation() {
        // Setup
        Tour tour = TestUtils.createNewTour("City_", 10);
//        System.out.println("Before: " + tour.getCities());

        List<City> backup = new ArrayList<>(tour.getCities());

        // SUT
        Mutation.doDisplacementMutation(tour.getCities(), 1);

        // Assert
//        System.out.println("Before: " + tour.getCities());
        Assert.assertEquals("Tour size after displacement mutation should be same",
                            backup.size(),
                            tour.getCities()
                                .size());

        Assert.assertFalse("After displacement mutation, cities order should change",
                           backup.equals(tour.getCities()));
    }

    @Test
    public void testDisplaceElements() {
        // #1
        // Setup
        List<Integer> values1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected1 = List.of(0, 1, 2, 5, 6, 7, 8, 3, 4, 9);

        // SUT
        Mutation.displaceElements(values1, 5, 8, 3);

        // Assert
        Assert.assertEquals("Inserting before the displacement array should be possible",
                            expected1,
                            values1);

        // #2
        // Setup
        List<Integer> values2 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected2 = List.of(0, 1, 9, 2, 3, 4, 5, 6, 7, 8);

        // SUT
        Mutation.displaceElements(values2, 2, 8, 3);

        // Assert
        Assert.assertEquals("Inserting between the start & end position should be possible",
                            expected2,
                            values2);

        // #3
        // Setup
        List<Integer> values3 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected3 = List.of(0, 1, 5, 6, 7, 2, 3, 4, 8, 9);

        // SUT
        Mutation.displaceElements(values3, 2, 4, 5);

        // Assert
        Assert.assertEquals("Inserting after the displacement array should be possible",
                            expected3,
                            values3);
    }


    @Test
    public void testReverseElements() {
        // #1
        // Setup
        List<Integer> values1 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected1 = List.of(0, 1, 2, 3, 4, 8, 7, 6, 5, 9);

        // SUT
        Mutation.reverseElements(values1, 5, 8);

        // Assert
        Assert.assertEquals("Elements should be reversed",
                            expected1,
                            values1);

        // #2
        // Setup
        List<Integer> values2 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected2 = List.of(0, 1, 2, 3, 4, 5, 9, 8, 7, 6);

        // SUT
        Mutation.reverseElements(values2, 6, 9);

        // Assert
        Assert.assertEquals("Elements should be reversed",
                            expected2,
                            values2);

        // #3
        // Setup
        List<Integer> values3 = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
        List<Integer> expected3 = List.of(9, 8, 7, 6, 5, 4, 3, 2, 1, 0);

        // SUT
        Mutation.reverseElements(values3, 0, 9);

        // Assert
        Assert.assertEquals("Elements should be reversed",
                            expected3,
                            values3);
    }

}
