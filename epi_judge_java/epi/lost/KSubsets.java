package epi.lost;

/**
 * 16.5 Generate all subsets of size k.
 */
public class KSubsets {

    static void printCombinations(int[] arr, int k) {
        int[] combination = new int[k];
        doPrintCombinations(arr, 0, combination, 0, k);
    }

    private static void doPrintCombinations(int[] arr, int offset, int[] subset, int idx, int k) {
        if (idx == k) {
            for (int j = 0; j < k; j++) {
                System.out.print(subset[j] + " ");
            }
            System.out.println();
            return;
        }

        if (offset >= arr.length) {
            return;
        }

        // current index is included, move on to the next position
        subset[idx] = arr[offset];
        doPrintCombinations(arr, offset + 1, subset, idx + 1, k);


        // current index is excluded, note that
        // offset + 1 is passed, but idx is not changed
        doPrintCombinations(arr, offset + 1, subset, idx, k);
    }

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        int k = 3;
        printCombinations(arr, k);
    }
}
