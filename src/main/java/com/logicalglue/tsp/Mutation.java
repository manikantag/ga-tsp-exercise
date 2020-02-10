package com.logicalglue.tsp;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Different mutation algorithms implementation.
 *
 * @see <a href="https://www.ncbi.nlm.nih.gov/pmc/articles/PMC4990531/">
 * Performance impact of mutation operators of a subpopulation-based
 * genetic algorithm for multi-robot task allocation problems</a>
 * @see <a href="https://www.tutorialspoint.com/genetic_algorithms/genetic_algorithms_mutation.htm">
 * Mutations</a>
 */
public class Mutation {

    /**
     * Performs random number of swap mutations, at random positions, on the given list.
     *
     * @param members      List of members to be mutated by displacement
     * @param mutationRate Values from 0.0 (0% of times) to 1.0 (100% of times),
     *                     indicating how frequent this mutation should be applied
     * @param <T>          Values type
     */
    public static <T> void doSwapMutation(List<T> members, double mutationRate) {
        for (int pos1 = 0; pos1 < members.size(); pos1++) {
            // Apply mutation rate
            if (Utils.randomProbability() < mutationRate) {
                // Get a second random position in the list
                int pos2 = (int) (members.size() * Math.random());

                // Get the elements at target position in the list
                T ele1 = members.get(pos1);
                T ele2 = members.get(pos2);

                // Swap them around
                members.set(pos2, ele1);
                members.set(pos1, ele2);
            }
        }
    }


    /**
     * Performs random displacement mutations on the given list.
     *
     * @param members      List of members to be mutated by displacement
     * @param mutationRate Values from 0.0 (0% of times) to 1.0 (100% of times),
     *                     indicating how frequent this mutation should be applied
     * @param <T>          Values type
     */
    public static <T> void doDisplacementMutation(List<T> members,
                                                  double mutationRate) {
        int numOfMutations = Utils.randomInt(1, 5);
        int totalMembers = members.size();

        for (int i = 0; i < numOfMutations; i++) {
            // Apply mutation rate
            if (Utils.randomProbability() > mutationRate) {
                continue;
            }

            int startAt = Utils.randomInt(totalMembers);
            int endAt = Utils.randomInt(startAt, totalMembers);

            int sizeExcludingDisplacement = totalMembers - (endAt - startAt);
            int insertAt = Utils.randomInt(sizeExcludingDisplacement);

            displaceElements(members,
                             startAt,
                             endAt,
                             insertAt);
        }
    }


    /**
     * Moves the given range of elements to different position,
     * shifting the other existing elements accordingly.
     * <p>
     * Ex:<pre>
     * [0, 1, 2, 3, 4, 5, 6, 7, 8, 9] -> [0, 1, 2, 5, 6, 7, 8, 3, 4, 9]
     *           i     s        e                  s        e  i
     * </pre></p>
     *
     * @param values   List whose range of values to be displaced
     * @param startPos Starting position (inclusive) of the displacement range
     * @param endPos   Ending position (inclusive) of the displacement range
     * @param insertAt Position in the given list after removing the given range of elements
     * @param <T>      Values type
     */
    public static <T> void displaceElements(List<T> values,
                                            int startPos,
                                            int endPos,
                                            int insertAt) {
        if (startPos == insertAt) {
            return;
        }

        // Take out the displacement range
        List<T> toDisplaceSubList = values.subList(startPos,
                                                   endPos + 1);
        List<T> toDisplaceList = new ArrayList<>(toDisplaceSubList);

        toDisplaceSubList.clear();

        // Put the displacement range at new position
        values.addAll(insertAt,
                      toDisplaceList);
    }


    /**
     * Performs inversion mutations, at random points, on the given list.
     *
     * @param members      List of members to be mutated by displacement
     * @param mutationRate Values from 0.0 (0% of times) to 1.0 (100% of times),
     *                     indicating how frequent this mutation should be applied
     * @param <T>          Values type
     */
    public static <T> void doInversionMutation(List<T> members,
                                               double mutationRate) {
        if (Utils.randomProbability() > mutationRate) {
            return;
        }

        int totalMembers = members.size();
        int startAt = Utils.randomInt(totalMembers);
        int endAt = Utils.randomInt(startAt, totalMembers);

        reverseElements(members,
                        startAt,
                        endAt);
    }


    /**
     * Reverse the elements in given range.
     *
     * @param values   List whose range of values to be reversed
     * @param startPos Starting position (inclusive) of the displacement range
     * @param endPos   Ending position (inclusive) of the displacement range
     * @param <T>      Values type
     */
    public static <T> void reverseElements(List<T> values,
                                           int startPos,
                                           int endPos) {
        Collections.reverse(values.subList(startPos, endPos + 1));
    }

}
