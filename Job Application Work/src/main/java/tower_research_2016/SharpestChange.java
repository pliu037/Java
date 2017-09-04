package tower_research_2016;

import java.util.ArrayList;

public class SharpestChange {

    static double roundToClosestHundredth(double f) {
        return Math.round(f*100) / 100.0;
    }

    static void findSharpestTimes(double[] prices) {
        double max = roundToClosestHundredth(Math.abs(prices[1] - prices[0]));
        ArrayList<Integer> maxTimes = new ArrayList<>();
        maxTimes.add(0);

        for (int i = 1; i < prices.length - 1; i ++) {
            double check = roundToClosestHundredth(Math.abs(prices[i + 1] - prices[i - 1])/2);
            if (check == max) {
                maxTimes.add(i);
            } else if (check > max) {
                max = check;
                maxTimes = new ArrayList<>();
                maxTimes.add(i);
            }
        }

        double check = roundToClosestHundredth(Math.abs(prices[prices.length - 1] - prices[prices.length - 2]));
        if (check == max) {
            maxTimes.add(prices.length - 1);
        } else if (check > max) {
            max = check;
            maxTimes = new ArrayList<>();
            maxTimes.add(prices.length - 1);
        }

        for (Integer time : maxTimes) {
            System.out.println(time);
        }
        System.out.println(max);
    }

    public static void main(String[] args) {
        double[] test = new double[] {0, 1, 2, 3, 4, 5.1, 6, 2, 3, 4};
        findSharpestTimes(test);
    }
}
