package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.EpiTestComparator;
import epi.test_framework.GenericTestHandler;
import epi.test_framework.LexicographicalListComparator;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.BiPredicate;

public class NQueens {

    @EpiTest(testfile = "n_queens.tsv")
    public static List<List<Integer>> nQueens(int n) {
        List<List<Integer>> result = new LinkedList<>();
        solveNQueens(n, 0, new ArrayList<>(n), result);
        return result;
    }

    private static void solveNQueens(int n, int row, List<Integer> colPlacement, List<List<Integer>> result) {
        if (row == n) {
            // we got to the bottom of the recursion tree - all queens are legally placed
            // important - we make a copy of colPlacement list here since it is being reused between recursive calls
            result.add(new ArrayList<>(colPlacement));
        } else {
            for (int col = 0; col < n; col++) {
                colPlacement.add(col);
                if (isValid(colPlacement)) {
                    solveNQueens(n, row + 1, colPlacement, result);
                }
                // backtracking
                colPlacement.remove(colPlacement.size() - 1);
            }
        }
    }

    private static boolean isValid(List<Integer> colPlacement) {
        // make sure newly placed queen doesn't conflict with any queens placed before
        int row = colPlacement.size() - 1;
        for (int i = 0; i < row; i++) {
            int diff = Math.abs(colPlacement.get(i) - colPlacement.get(row));
            // check vertical and diagonal conflicts
            if (diff == 0 || diff == row - i) {
                return false;
            }
        }
        return true;
    }

    @EpiTestComparator
    public static BiPredicate<List<List<Integer>>, List<List<Integer>>> comp = (expected, result) -> {
        if (result == null) {
            return false;
        }
        expected.sort(new LexicographicalListComparator<>());
        result.sort(new LexicographicalListComparator<>());
        return expected.equals(result);
    };

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
