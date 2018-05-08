package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.LexicographicalListComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

public class Permutations {

    @EpiTest(testfile = "permutations.tsv")
    public static List<List<Integer>> permutations(List<Integer> A) {
        List<List<Integer>> result = new LinkedList<>();
        generatePermutations(0, A, result);
        return result;
    }

    private static void generatePermutations(int i, List<Integer> a, List<List<Integer>> result) {
        if (i == a.size() - 1) {
            result.add(a);
            return;
        }
        for (int j = i; j < a.size(); j++) {
            Collections.swap(a, i, j);
            // generate all permutations for sublist [i + 1, a.size())
            generatePermutations(i + 1, a, result);
            Collections.swap(a, i, j);
        }
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
