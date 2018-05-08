package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.LexicographicalListComparator;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

public class PowerSet {

    @EpiTest(testfile = "power_set.tsv")
    public static List<List<Integer>> generatePowerSetRecursively(List<Integer> inputSet) {
        List<List<Integer>> allSubsets = new LinkedList<>();
        doGeneratePowerSetRecursively(inputSet, 0, new Integer[inputSet.size()], allSubsets);
        return allSubsets;
    }

    private static void doGeneratePowerSetRecursively(List<Integer> inputSet, int i,
                                                      Integer[] subset, List<List<Integer>> allSubsets) {
        if (i == inputSet.size()) {
            List<Integer> sanitizedSubset = Arrays.stream(subset)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            allSubsets.add(sanitizedSubset);
            return;
        }
        // generate all subsets that do not contain inputSet.get(i)
        subset[i] = null;
        doGeneratePowerSetRecursively(inputSet, i + 1, subset, allSubsets);
        // generate all subsets that contain inputSet.get(i)
        subset[i] = inputSet.get(i);
        doGeneratePowerSetRecursively(inputSet, i + 1, subset, allSubsets);
    }

    @EpiTest(testfile = "power_set.tsv")
    public static List<List<Integer>> generatePowerSetIteratively(List<Integer> inputSet) {
        List<List<Integer>> allSubsets = new LinkedList<>();
        int max = 1 << inputSet.size(); // 2^n
        for (int bitMask = 0; bitMask < max; bitMask++) {
            List<Integer> subset = bitMaskToSubset(bitMask, inputSet);
            allSubsets.add(subset);
        }
        return allSubsets;
    }

    private static List<Integer> bitMaskToSubset(int bitMask, List<Integer> inputSet) {
        List<Integer> subset = new LinkedList<>();
        int i = 0;
        while (bitMask > 0) {
            if ((bitMask & 1) == 1) {
                Integer elem = inputSet.get(i);
                subset.add(elem);
            }
            i++;
            bitMask >>= 1;
        }
        return subset;
    }

    @EpiTestComparator
    public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp = (expected, result) -> {
        if (result == null) {
            return false;
        }
        for (List<Integer> l : expected) {
            Collections.sort(l);
        }
        expected.sort(new LexicographicalListComparator<>());
        for (List<Integer> l : result) {
            Collections.sort(l);
        }
        result.sort(new LexicographicalListComparator<>());
        return expected.equals(result);
    };

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
