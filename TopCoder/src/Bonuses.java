//1-25 SRM 145 DIV 1 250

import java.util.ArrayList;

public class Bonuses {

    public int[] getDivision(int[] points) {
        int sum = 0;
        for (int i = 0; i < points.length; i ++) {
            sum += points[i];
        }

        int[] bonusPercentages = new int[points.length];
        int remainingPercentage = 100;
        for (int i = 0; i < points.length; i ++) {
            bonusPercentages[i] = 100*points[i]/sum;
            remainingPercentage -= bonusPercentages[i];
        }

        if (remainingPercentage == 0) {
            return bonusPercentages;
        }

        ArrayList<ArrayList<Integer>> descendingIndices = new ArrayList<>();
        int upper = Integer.MAX_VALUE;
        while (true) {
            int currentUpper = -1;
            ArrayList<Integer> currentMaxIndices = new ArrayList<>();
            for (int i = 0; i < points.length; i ++) {
                if (points[i] > currentUpper && points[i] < upper) {
                    currentMaxIndices = new ArrayList<>();
                    currentMaxIndices.add(i);
                    currentUpper = points[i];
                }
                else if (points[i] == currentUpper) {
                    currentMaxIndices.add(i);
                }
            }
            if (currentUpper == -1) {
                break;
            }
            upper = currentUpper;
            descendingIndices.add(currentMaxIndices);
        }

        for (int i = 0; i < descendingIndices.size(); i ++) {
            for (int j = 0; j < descendingIndices.get(i).size(); j ++) {
                bonusPercentages[descendingIndices.get(i).get(j)] += 1;
                remainingPercentage -= 1;
                if (remainingPercentage <= 0) {
                    return bonusPercentages;
                }
            }
        }

        return bonusPercentages;
    }

    public static void main (String[] args) {
        Bonuses test = new Bonuses();
        int[] check = {1,2,3,4,5};
        int[] answer = test.getDivision(check);
        for (int i = 0; i < answer.length; i ++) {
            System.out.print(answer[i] + " ");
        }
    }
}
