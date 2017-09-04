package tower_research_2016;

import java.util.*;

public class Auction {

    HashMap<Integer, Integer> buyers = new HashMap<>();
    HashMap<Integer, Integer> sellers = new HashMap<>();
    int rangeMin = Integer.MAX_VALUE;
    int rangeMax = Integer.MIN_VALUE;
    int numBuyers = 0;

    void updateRange(int check) {
        if (check > rangeMax) {
            rangeMax = check;
        }
        if (check < rangeMin) {
            rangeMin = check;
        }
    }

    void addSeller(int min) {
        updateRange(min);

        if (sellers.containsKey(min)) {
            sellers.put(min, sellers.get(min) + 1);
        } else {
            sellers.put(min, 1);
        }
    }

    void addBuyer(int max) {
        updateRange(max);

        if (buyers.containsKey(max)) {
            buyers.put(max, buyers.get(max) + 1);
        } else {
            buyers.put(max, 1);
        }
        numBuyers ++;
    }

    void process() {
        int numSellers = 0;
        int tmpNumBuyers = numBuyers;
        int max = 0;
        int minDifference = 0;
        int price = 0;

        HashSet<Integer> marks = new HashSet<>();
        marks.addAll(buyers.keySet());
        marks.addAll(sellers.keySet());
        List<Integer> sortedMarks = new ArrayList<>(marks);
        Collections.sort(sortedMarks);

        for (Integer mark : sortedMarks) {
            if (sellers.containsKey(mark)) {
                numSellers += sellers.get(mark);
            }

            int overlap = Math.min(numSellers, tmpNumBuyers);
            if (overlap > max) {
                max = overlap;
                minDifference = Math.abs(numSellers - tmpNumBuyers);
                price = mark;
            } else if (max != 0 && overlap == max && Math.abs(numSellers - tmpNumBuyers) <= minDifference) {
                minDifference = Math.abs(numSellers - tmpNumBuyers);
                price = mark;
            } else if (overlap < max) {
                break;
            }

            if (buyers.containsKey(mark)) {
                tmpNumBuyers -= buyers.get(mark);
            }
        }

        System.out.println(max + " " + price);
    }

    public static void main(String[] args) {
        Auction auc = new Auction();
        auc.addBuyer(90);
        auc.addBuyer(90);
        auc.addBuyer(94);
        auc.addBuyer(94);
        auc.addBuyer(95);
        auc.addSeller(80);
        auc.addSeller(90);
        auc.addSeller(90);
        auc.addSeller(92);
        auc.addSeller(100);
        auc.process();
    }
}
