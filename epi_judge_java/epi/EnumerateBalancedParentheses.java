package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

public class EnumerateBalancedParentheses {

    @EpiTest(testfile = "enumerate_balanced_parentheses.tsv")
    public static List<String> generateBalancedParentheses(int numPairs) {
        char[] str = new char[2 * numPairs];
        List<String> result = new LinkedList<>();
        doGenerateBalancedParentheses(numPairs, numPairs, str, 0, result);
        return result;
    }

    private static void doGenerateBalancedParentheses(int numLeftParens, int numRightParens,
                                                      char[] str, int i, List<String> result) {
        if (numLeftParens == 0 && numRightParens == 0) {
            result.add(String.valueOf(str));
            return;
        }
        // if there are left parens remaining, insert a left paren and recurse
        if (numLeftParens > 0) {
            str[i] = '(';
            doGenerateBalancedParentheses(numLeftParens - 1, numRightParens, str, i + 1, result);
        }
        // if there are more right parens remaining, insert a right paren and recurse
        if (numRightParens > numLeftParens) {
            str[i] = ')';
            doGenerateBalancedParentheses(numLeftParens, numRightParens - 1, str, i + 1, result);
        }
    }

    @EpiTestComparator
    public static BiPredicate<List<String>, List<String>> comp = (expected, result) -> {
        if (result == null) {
            return false;
        }
        Collections.sort(expected);
        Collections.sort(result);
        return expected.equals(result);
    };

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
