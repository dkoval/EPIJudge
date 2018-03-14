package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class ReverseBits {

    private static final int WORD_SIZE = 16;
    private static final int BIT_MASK = 0xFFFF;
    private static final int[] reverse = new int[BIT_MASK];

    static {
        for (int i = 0; i < BIT_MASK; ++i) {
            reverse[i] = doReverseBits(i);
        }
    }

    private static int doReverseBits(int x) {
        int rev = 0;
        for (int i = 0; i < WORD_SIZE; i++) {
            int ithBit = (x >>> i) & 1;
            if (ithBit == 1) {
                rev |= 1 << (WORD_SIZE - i - 1);
            }
        }
        return rev;
    }

    @EpiTest(testfile = "reverse_bits.tsv")
    public static long reverseBits(long x) {
        // x0 word becomes y3
        return (long) reverse[(int) (x & BIT_MASK)] << (3 * WORD_SIZE)
                // x1 word becomes y2
                | (long) reverse[(int) ((x >>> WORD_SIZE) & BIT_MASK)] << (2 * WORD_SIZE)
                // x2 word becomes y1
                | (long) reverse[(int) ((x >>> (2 * WORD_SIZE)) & BIT_MASK)] << WORD_SIZE
                // x3 word becomes y0
                | (long) reverse[(int) ((x >>> (3 * WORD_SIZE)) & BIT_MASK)];
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
