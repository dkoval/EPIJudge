package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

public class PrimitiveMultiply {

    @EpiTest(testfile = "primitive_multiply.tsv")
    public static long multiply(long x, long y) {
        // x * y = x * y0 + x * y1 * 2 + x * y2 * 2 ^ 2 + ... + x * yn-1 * 2 ^ (n - 1),
        // where yi denotes i-th bit of y
        long sum = 0;
        while (y != 0) {
            if ((y & 1) != 0) {
                sum = add(sum, x);
            }
            y >>>= 1;
            x <<= 1; // x * 2^i = x << i
        }
        return sum;
    }

    private static long add(long a, long b) {
        long sum = 0, copyA = a, copyB = b, carry = 0, mask = 1;
        while (copyA != 0 || copyB != 0) {
            long ak = a & mask;
            long bk = b & mask;
            sum |= (ak ^ bk ^ carry);
            carry = ((ak & bk) | (ak & carry) | (bk & carry)) << 1;
            mask <<= 1;
            copyA >>>= 1;
            copyB >>>= 1;
        }
        return sum | carry;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
