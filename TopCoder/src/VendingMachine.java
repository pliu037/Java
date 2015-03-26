//1-25 SRM 145 DIV 1 600

import java.util.ArrayList;
import java.util.StringTokenizer;

public class VendingMachine {

    /*
    The outer ArrayList represents columns whereas the inner ArrayList represents rows from top to
    bottom.
     */
    private ArrayList<ArrayList<Integer>> items;

    private ArrayList<Integer> columnSums;
    private int rotations;
    private int position;
    private int lastPurchase;

    /**
     * Sets the <columnNum>-th entry of columnSums as the sum of the prices in the <columnNum>-th
     * column.
     */
    private void sumColumn (int columnNum) {
        ArrayList<Integer> column = items.get(columnNum);
        int sum = 0;
        for (int i = 0; i < column.size(); i ++) {
            sum += column.get(i);
        }
        columnSums.set(columnNum, sum);
    }

    /**
     * Rotates to the given index (dest), going clockwise or counterclockwise to minimize the number
     * of rotation steps needed.
     */
    private void rotate (int dest) {
        int columns = items.size();
        int min = Math.min(Math.abs(dest - position), Math.abs(columns - position + dest));
        rotations += Math.min(min, Math.abs(position + columns - dest));
        position = dest;
    }

    /**
     * Finds and rotates to the column with the maximum sum (ties go to the lowest index).
     */
    private void goToMax () {
        int maxPosition = 0;
        int maxValue = 0;
        for (int i = 0; i < columnSums.size(); i ++) {
            if (columnSums.get(i) > maxValue) {
                maxValue = columnSums.get(i);
                maxPosition = i;
            }
        }
        rotate(maxPosition);
    }

    /**
     * Returns the number of rotation steps the vending machine makes given the items and purchases
     * specified in prices and purchases, respectively. Items in the vending machine are arranged in
     * rows and columns around a cylinder so the columns wrap-around.
     */
    public int motorUse (String[] prices, String[] purchases) {
        items = new ArrayList<>();
        columnSums = new ArrayList<>();
        rotations = 0;
        position = 0;
        lastPurchase = 0;

        /*
        Places the prices of items into their respective rows and columns. Each String represents a
        row of space-delimited prices.
         */
        StringTokenizer tokenizer = new StringTokenizer(prices[0], " ");
        while (tokenizer.hasMoreTokens()) {
            ArrayList<Integer> column = new ArrayList<>();
            Integer price = Integer.parseInt(tokenizer.nextToken());
            column.add(price);
            items.add(column);
            columnSums.add(price);
        }
        for (int i = 1; i < prices.length; i ++) {
            tokenizer = new StringTokenizer(prices[i], " ");
            for (int j = 0; j < items.size(); j ++) {
                ArrayList<Integer> column = items.get(j);
                Integer price = Integer.parseInt(tokenizer.nextToken());
                column.add(price);
                columnSums.set(j, columnSums.get(j) + price);
            }
        }

        goToMax();

        /*
        Simulates each purchase. Each String represents a purchase in the form <row>,<column>:<time>.
        A purchase requires the vending machine to rotate to the correct column. If 5 or more units
        of time have elapsed since the previous purchase, the vending machine rotates to the column
        with the maximum sum.
         */
        for (int i = 0; i < purchases.length; i ++) {
            tokenizer = new StringTokenizer(purchases[i], ",:");
            int row = Integer.parseInt(tokenizer.nextToken());
            int column = Integer.parseInt(tokenizer.nextToken());
            int time = Integer.parseInt(tokenizer.nextToken());
            ArrayList<Integer> check = items.get(column);

            /*
            There are no items with a price of 0, so if a position's price is 0, it must have been
            previously purchased and the method returns -1 for an invalid sequences of purchases.
             */
            if (check.get(row) == 0) {
                return -1;
            }

            if (time - lastPurchase >= 5) {
                goToMax();
            }
            rotate(column);
            lastPurchase = time;
            check.set(row, 0);
            sumColumn(column);
        }

        goToMax();

        return rotations;
    }

    public static void main (String[] args) {
        VendingMachine test = new VendingMachine();
        String[] prices = {"100 200 300", "600 500 400"};
        String[] purchases = {"0,0:0", "1,1:10", "1,2:20", "0,1:21", "1,0:22", "0,2:35"};
        System.out.println(test.motorUse(prices, purchases));
    }
}
