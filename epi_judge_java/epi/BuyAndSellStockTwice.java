package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.ArrayList;
import java.util.List;

public class BuyAndSellStockTwice {

    // O(N) time complexity, O(N) space complexity
    @EpiTest(testfile = "buy_and_sell_stock_twice.tsv")
    public static double buyAndSellStockTwice(List<Double> prices) {
        double profit = 0.0;
        // forward phase
        double minPriceSoFar = Double.MAX_VALUE;
        List<Double> firstBuyAndSellProfits = new ArrayList<>();
        for (Double price : prices) {
            minPriceSoFar = Math.min(minPriceSoFar, price);
            profit = Math.max(profit, price - minPriceSoFar);
            firstBuyAndSellProfits.add(profit);
        }
        // backward phase
        double maxPriceSoFar = Double.MIN_VALUE;
        for (int i = prices.size() - 1; i > 0; i--) {
            maxPriceSoFar = Math.max(maxPriceSoFar, prices.get(i));
            profit = Math.max(profit, maxPriceSoFar - prices.get(i) + firstBuyAndSellProfits.get(i - 1));
        }
        return profit;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
