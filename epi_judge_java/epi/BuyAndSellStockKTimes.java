package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class BuyAndSellStockKTimes {

    @EpiTest(testfile = "buy_and_sell_stock_k_times.tsv")
    public static double buyAndSellStockKTimes(List<Double> prices, int k) {
        // maxProfit[t][i] - maximum profit that can be made with at most t transactions up to day i
        int n = prices.size();
        double[][] maxProfit = new double[k + 1][n];

        for (int t = 1; t <= k; t++) {
            for (int i = 1; i < n; i++) {
                double maxProfitSoFar = 0.0;
                for (int j = 0; j < i; j++) {
                    maxProfitSoFar = Math.max(maxProfitSoFar, prices.get(i) - prices.get(j) + maxProfit[t - 1][j]);
                }
                maxProfit[t][i] = Math.max (maxProfit[t][i - 1], maxProfitSoFar);
            }
        }
        return maxProfit[k][n - 1];
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
