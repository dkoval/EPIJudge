package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class SwapBits {

    @EpiTest(testfile = "swap_bits.tsv")
    public static long swapBits(long x, int i, int j) {
        // Extract the i-th and j-th bits, and see if they differ
        if (((x >>> i) & 1) == ((x >>> j) & 1)) {
            return x;
        }
        // Swap t-th and j-th bits by flipping their values.
        // Since x ^ 1 = 0 when x = 1 and x ^ 1 = 1 when x = 0, perform the flip with XOR.
        long bitMask = (1L << i) | (1L << j);
        return x ^ bitMask;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
