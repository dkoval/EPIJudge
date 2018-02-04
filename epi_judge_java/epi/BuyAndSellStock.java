package epi;

import epi.test_framework.EpiTest;
import epi.test_framework.GenericTestHandler;

import java.util.List;

public class BuyAndSellStock {

    @EpiTest(testfile = "buy_and_sell_stock.tsv")
    public static double computeMaxProfit(List<Double> prices) {
        double maxProfit = 0.0;
        double minPriceSoFar = Double.MAX_VALUE;
        for (Double price : prices) {
            minPriceSoFar = Math.min(minPriceSoFar, price);
            maxProfit = Math.max(maxProfit, price - minPriceSoFar);
        }
        return maxProfit;
    }

    public static void main(String[] args) {
        GenericTestHandler.executeTestsByAnnotation(
                new Object() {}.getClass().getEnclosingClass(), args);
    }
}
