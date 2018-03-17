package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class ClosestIntSameWeight {

    @EpiTest(testfile = "closest_int_same_weight.tsv")
    public static long closestIntSameBitCount(long x) {
        for (int i = 0; i < 63; i++) {
            // find two consecutive bit that differ
            if (((x >> i) & 1) != ((x >> (i + 1)) & 1)) {
                // and then swap them
                long mask = (1L << i) | (1L << (i + 1));
                return x ^ mask;
            }
        }
        throw new IllegalArgumentException("All bits are either 0 or 1. Input: " + x);
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {
                }.getClass().getEnclosingClass(), args);
    }
}
